package cn.xmo.service.cargo.impl;

import cn.xmo.dao.cargo.*;
import cn.xmo.domain.cargo.Export;
import cn.xmo.domain.cargo.Packing;
import cn.xmo.domain.cargo.PackingExample;
import cn.xmo.service.cargo.PackingService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
/**
 * @Author: Elves
 * @Description: 装箱模块
 * @Date: Created in 11:45 2019/8/10
 * @Modified By:
 */
@Service
public class PackingServiceImpl implements PackingService {

    @Autowired
    private PackingDao packingDao;

    @Autowired
    private ExportDao exportDao;

    @Autowired
    private ShippingDao shippingDao;

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private FinanceDao financeDao;


    @Override
    public PageInfo findAll(PackingExample packingExample, int page, int size) {
        PageHelper.startPage(page,size);
        List<Packing> list = packingDao.selectByExample(packingExample);
        return new PageInfo(list);
    }

    @Override
    public void save(Packing packing) {
        //设置装箱单id
        packing.setPackingListId(UUID.randomUUID().toString());
        //设置打断字段     （多个打段字段之间，使用“，”分隔）
        String[] exportIds = packing.getExportIds().split(",");
        List<String> stringList = Arrays.asList(exportIds);
        Integer size = stringList.size();
        //设置报运单数量
        packing.setExportNos(size.toString());

        packing.setState(0);
        packingDao.insert(packing);
    }

    @Override
    public void update(Packing packing) {
        Packing oldPacking = packingDao.selectByPrimaryKey(packing.getPackingListId());
        String[] oldIds = oldPacking.getExportIds().split(",");
        for (String oldId : oldIds) {
            Export oldExport = exportDao.selectByPrimaryKey(oldId);
            oldExport.setState(2);
            exportDao.updateByPrimaryKeySelective(oldExport);
        }

        String[] exportIds = packing.getExportIds().split(",");
        List<String> stringList = Arrays.asList(exportIds);
        Integer size = stringList.size();
        packing.setExportNos(size.toString());

        for (String exportId : exportIds) {
            Export newExport = exportDao.selectByPrimaryKey(exportId);
            newExport.setState(3);
            exportDao.updateByPrimaryKeySelective(newExport);
        }
        packingDao.updateByPrimaryKey(packing);
    }

    @Override
    public void delete(String id) {
        packingDao.deleteByPrimaryKey(id);
    }

    @Override
    public Packing findById(String id) {
        return packingDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Packing> findAll(PackingExample packingExample) {

        return packingDao.selectByExample(packingExample);
    }

}
