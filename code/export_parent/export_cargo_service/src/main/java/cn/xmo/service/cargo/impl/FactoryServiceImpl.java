package cn.xmo.service.cargo.impl;

import cn.xmo.dao.cargo.FactoryDao;
import cn.xmo.domain.cargo.Factory;
import cn.xmo.domain.cargo.FactoryExample;
import cn.xmo.service.cargo.FactoryService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 14:48 2019/8/5
 * @Modified By:
 */
@Service
public class FactoryServiceImpl implements FactoryService {

    @Autowired
    private FactoryDao factoryDao;


    /**
     * 查询所有
     *
     * @param example
     * @return
     */
    @Override
    public List<Factory> findAll(FactoryExample example) {
        return factoryDao.selectByExample(example);
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @param factoryExample
     * @return
     */
    @Override
    public PageInfo findAll(int page, int size, FactoryExample factoryExample) {
        PageHelper.startPage(page, size);
        List<Factory> list = factoryDao.selectByExample(factoryExample);
        return new PageInfo(list);
    }
    /**
     * 保存
     *
     * @param factory
     */
    @Override
    public void save(Factory factory) {
        factory.setId(UUID.randomUUID().toString());
        factoryDao.insertSelective(factory);
    }

    /**
     * 更新
     *
     * @param factory
     */
    @Override
    public void update(Factory factory) {
        factoryDao.updateByPrimaryKeySelective(factory);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        factoryDao.deleteByPrimaryKey(id);
    }

    /**
     * 根据id查询
     *
     * @param id
     */
    @Override
    public Factory findById(String id) {
        return factoryDao.selectByPrimaryKey(id);
    }


}
