package cn.xmo.service.cargo.impl;

import cn.xmo.dao.cargo.InvoiceDao;
import cn.xmo.domain.cargo.Invoice;
import cn.xmo.domain.cargo.InvoiceExample;
import cn.xmo.service.cargo.InvoiceService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceDao invoiceDao;

    @Override
    public PageInfo findAll(int page, int size, InvoiceExample invoiceExample) {
        PageHelper.startPage(page,size);
        List<Invoice> list = invoiceDao.selectByExample(invoiceExample);
        return new PageInfo(list);
    }

    @Override
    public void save(Invoice invoice) {
        /* *
         * 初始状态 0 ：草稿
         */
        invoice.setState(0);
        invoiceDao.insertSelective(invoice);
    }

    @Override
    public void update(Invoice invoice) {
        invoiceDao.updateByPrimaryKeySelective(invoice);
    }

    @Override
    public void delete(String id) {
        invoiceDao.deleteByPrimaryKey(id);
    }

    @Override
    public Invoice findById(String id) {
        return invoiceDao.selectByPrimaryKey(id);
    }
}
