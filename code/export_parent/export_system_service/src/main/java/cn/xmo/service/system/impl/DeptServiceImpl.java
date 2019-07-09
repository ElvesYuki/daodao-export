package cn.xmo.service.system.impl;

import cn.xmo.dao.system.DeptDao;
import cn.xmo.domain.system.Dept;
import cn.xmo.domain.system.DeptExample;
import cn.xmo.service.system.DeptService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 19:26 2019/7/9
 * @Modified By:
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptDao deptDao;

    @Override
    public PageInfo findAll(int page, int size, DeptExample deptExample) {
        PageHelper.startPage(page, size);
        List<Dept> list = deptDao.selectByExample(deptExample);
        return new PageInfo<>(list);
    }

    @Override
    public void save(Dept dept) {

    }

    @Override
    public void update(Dept dept) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Dept findById(String id) {
        return null;
    }

    @Override
    public List findAll(String companyId) {
        return null;
    }
}
