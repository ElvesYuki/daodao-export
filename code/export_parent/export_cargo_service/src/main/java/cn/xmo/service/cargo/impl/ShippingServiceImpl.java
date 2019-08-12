package cn.xmo.service.cargo.impl;

import cn.xmo.dao.cargo.PackingDao;
import cn.xmo.dao.cargo.ShippingDao;
import cn.xmo.domain.cargo.Packing;
import cn.xmo.domain.cargo.Shipping;
import cn.xmo.domain.cargo.ShippingExample;
import cn.xmo.service.cargo.ShippingService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 15:57 2019/8/10
 * @Modified By:
 */
@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingDao shippingDao;
    @Autowired
    private PackingDao packingDao;
    @Override
    public PageInfo findAll(ShippingExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<Shipping> list = shippingDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Override
    public void save(Shipping shipping) {
        //修改装箱单状态
        //设置状态
        shipping.setState(0);
        shippingDao.insertSelective(shipping);
    }

    @Override
    public void update(Shipping shipping) {
        shippingDao.updateByPrimaryKeySelective(shipping);
    }

    @Override
    public void delete(String id) {
        Packing packing = packingDao.selectByPrimaryKey(id);
        packing.setState(3);
        packingDao.updateByPrimaryKey(packing);
        shippingDao.deleteByPrimaryKey(id);
    }

    @Override
    public Shipping findById(String id) {

        return shippingDao.selectByPrimaryKey(id);
    }
}
