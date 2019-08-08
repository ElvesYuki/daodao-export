package cn.xmo.service.cargo.impl;

import cn.xmo.dao.cargo.ContractDao;
import cn.xmo.dao.cargo.ContractProductDao;
import cn.xmo.dao.cargo.ExtCproductDao;
import cn.xmo.dao.cargo.FactoryDao;
import cn.xmo.domain.cargo.*;
import cn.xmo.service.cargo.ContractProductService;
import cn.xmo.vo.ContractProductVo;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 14:35 2019/8/5
 * @Modified By:
 */
@Service
public class ContractProductServiceImpl implements ContractProductService {

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ExtCproductDao extCproductDao;

    @Autowired
    private FactoryDao factoryDao;
    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @param example
     */
    @Override
    public PageInfo findAll(int page, int size, ContractProductExample example) {
        PageHelper.startPage(page,size);
        List<ContractProduct> list = contractProductDao.selectByExample(example);
        return new PageInfo(list);
    }

    /**
     * 保存
     *
     * @param contractProduct
     */
    @Override
    public void save(ContractProduct contractProduct) {
//		1.设置货物id
        contractProduct.setId(UUID.randomUUID().toString());
//		2.计算货物总金额
        double money = 0;
        if (contractProduct.getCnumber() != null && contractProduct.getPrice() != null) {
            money = contractProduct.getCnumber() * contractProduct.getPrice();
        }
//		3.设置货物的总金额
        contractProduct.setAmount(money);
//		4.保存货物
        contractProductDao.insertSelective(contractProduct);
//		5.根据id查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
//		6.设置购销合同总金额
        contract.setTotalAmount(contract.getTotalAmount() + money);
//		7.设置购销合同货物数量
        contract.setProNum(contract.getProNum() + 1);
//		8.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 更新
     *
     * @param contractProduct
     */
    @Override
    public void update(ContractProduct contractProduct) {
        //1.计算修改之后的货物总金额
        double newMoney = 0;
        if (contractProduct.getCnumber() != null && contractProduct.getPrice() != null) {
            newMoney = contractProduct.getCnumber() * contractProduct.getPrice();
        }
        //2.查询修改之前的货物总金额
        ContractProduct ocp = contractProductDao.selectByPrimaryKey(contractProduct.getId());
        double oldMoney = ocp.getAmount();
        //3.设置货物的总金额
        contractProduct.setAmount(newMoney);
        //4.更新货物
        contractProductDao.updateByPrimaryKeySelective(contractProduct);
        //5.查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //6.设置购销合同总金额
        contract.setTotalAmount(contract.getTotalAmount() - oldMoney + newMoney);
        //7.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        //1.查询货物对象
        ContractProduct cp = contractProductDao.selectByPrimaryKey(id);
        //2.查询此货物下所有附件
        ExtCproductExample example  =  new ExtCproductExample();
        ExtCproductExample.Criteria criteria = example.createCriteria();
        criteria.andContractProductIdEqualTo(id);
        List<ExtCproduct> exts = extCproductDao.selectByExample(example);
        //3.查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(cp.getContractId());
        //4.计算总金额 (货物金额 + 所有附件金额)
        double money = cp.getAmount();
        for (ExtCproduct ext : exts) {
            money += ext.getAmount();
            //5.删除附件
            extCproductDao.deleteByPrimaryKey(ext.getId());
        }
        //6.删除货物
        contractProductDao.deleteByPrimaryKey(id);
        //7.设置合同的总金额
        contract.setTotalAmount(contract.getTotalAmount() - money);
        //8.设置附件和货物数量
        contract.setProNum(contract.getProNum()-1);
        contract.setExtNum(contract.getExtNum() - exts.size());
        //9.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 根据id查询
     *
     * @param id
     */
    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    /**
     * 批量保存
     * @param list
     */
    @Override
    public void saveAll(List<ContractProduct> list) {
        for (ContractProduct contractProduct : list) {
            Factory  factory = factoryDao.findByName(contractProduct.getFactoryName());
            contractProduct.setFactoryId(factory.getId());
            save(contractProduct);
        }
    }

    /**
     * 根据船期查询出货表数据
     *
     * @param inputDate
     * @return
     */
    @Override
    public List<ContractProductVo> findContractProductByShipTime(String inputDate) {
        return contractProductDao.findContractProductByShipTime(inputDate);
    }
}
