package cn.xmo.service.cargo.impl;

import cn.xmo.dao.cargo.*;
import cn.xmo.domain.cargo.*;
import cn.xmo.service.cargo.ContractProductService;
import cn.xmo.service.cargo.ExportService;
import cn.xmo.vo.ExportProductResult;
import cn.xmo.vo.ExportResult;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 20:38 2019/8/5
 * @Modified By:
 */
@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ExtCproductDao extCproductDao;

    @Autowired
    private ExportDao exportDao;

    @Autowired
    private ExportProductDao exportProductDao;

    @Autowired
    private ExtEproductDao extEproductDao;

    @Override
    public PageInfo findAll(ExportExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Export> list = exportDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Override
    public void save(Export export) {
        //i.保存报运单
        //1.设置id
        export.setId(UUID.randomUUID().toString());
        //2.设置打断字段     （多个打段字段之间，使用“，”分隔）
        String contractIds [] = export.getContractIds().split(",");
        //3.设置订单号的集合 （ 多个订单号之间，使用“ ”分隔）
        ContractExample example = new ContractExample();
        ContractExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(Arrays.asList(contractIds));
        //4.查询所有购销合同
        List<Contract> contracts = contractDao.selectByExample(example);
        String contractNos = "";
        for (Contract contract : contracts) {
            contractNos += contract.getContractNo() + " ";
            contract.setState(2);
            contractDao.updateByPrimaryKeySelective(contract);
        }
        export.setCustomerContract(contractNos);
        //ii.保存报运单商品
        //1.根据购销合同查询所有的购销合同的货物
        ContractProductExample cpExample = new ContractProductExample();
        ContractProductExample.Criteria cpCriteria1 = cpExample.createCriteria();
        //设置in查询
        cpCriteria1.andContractIdIn(Arrays.asList(contractIds));
        List<ContractProduct> cps = contractProductDao.selectByExample(cpExample);
        //2.循环所有货物构造报运单商品

        //设置一个 从  购销合同货物id  -- 报运单货物id 的map集合
        Map<String,String> map = new HashMap<>();
        for (ContractProduct cp : cps) {
            ExportProduct ep = new ExportProduct();
            //将source中的同名属性的值 拷贝到 target中的同名属性上
            BeanUtils.copyProperties(cp,ep);
            //3.设置报运单商品的id和报运单id
            ep.setId(UUID.randomUUID().toString());
            ep.setExportId(export.getId());
            //4.保存每个报运单商品
            exportProductDao.insertSelective(ep);
            //购销合同货物id ==111   ，  报运单商品id==2222
            map.put(cp.getId(),ep.getId()) ;
        }
        //iii.保存报运单商品附件
        //1.根据购销合同查询所有的购销合同附件
        ExtCproductExample extcExample = new ExtCproductExample();
        ExtCproductExample.Criteria extcCriteria1 = extcExample.createCriteria();
        extcCriteria1.andContractIdIn(Arrays.asList(contractIds));
        List<ExtCproduct> extcs = extCproductDao.selectByExample(extcExample);
        //2.循环所有的购销合同附件生成报运单附件
        //购销合同货物id ==111   ，  报运单商品id==2222
        for (ExtCproduct extc : extcs) {
            ExtEproduct extE = new ExtEproduct();
            BeanUtils.copyProperties(extc,extE);
            //3.设置报运单附件的id和报运单货物id以及报运单id
            extE.setId(UUID.randomUUID().toString());
            extE.setExportId(export.getId());
            extE.setExportProductId(map.get(extc.getContractProductId()));
            //4.保存每个报运单的附件
            extEproductDao.insertSelective(extE);
        }

        //0草稿 ，1.已提交
        export.setState(0);
        export.setExtNum(extcs.size());
        export.setProNum(cps.size());
        exportDao.insertSelective(export);
    }

    @Override
    public void update(Export export) {
        //更新报运单
        exportDao.updateByPrimaryKeySelective(export);
        //更新每一个报运单的商品
        if(export.getExportProducts() != null) {
            for (ExportProduct ep : export.getExportProducts()) {
                exportProductDao.updateByPrimaryKeySelective(ep);
            }
        }
    }

    @Override
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }

    @Override
    public void updateE(ExportResult exportResult) {
        //1.更新报运单的报运结果
        Export epExport = new Export();
        epExport.setId(exportResult.getExportId());
        epExport.setRemark(exportResult.getRemark());
        epExport.setState(exportResult.getState());
        exportDao.updateByPrimaryKeySelective(epExport);
        //2.更新报运单商品的税收
        Set<ExportProductResult> products = exportResult.getProducts();
        if(products != null) {
            for (ExportProductResult product : products) {
                ExportProduct ep = new ExportProduct();
                ep.setId(product.getExportProductId());
                ep.setTax(BigDecimal.valueOf(product.getTax()));
                exportProductDao.updateByPrimaryKeySelective(ep);
            }
        }
    }

    @Override
    public List<Export> findAll(ExportExample example) {
        return exportDao.selectByExample(example);
    }
}
