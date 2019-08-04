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
import java.util.UUID;

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
        for (Dept dept : list) {
            if (dept.getParentId()!=null) {
                Dept deptParent = deptDao.selectByPrimaryKey(dept.getParentId());
                dept.setParentName(deptParent.getDeptName());
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public void save(Dept dept) {
        dept.setDeptId(UUID.randomUUID().toString());
        deptDao.insertSelective(dept);
    }

    @Override
    public void update(Dept dept) {
        deptDao.updateByPrimaryKeySelective(dept);
    }

    @Override
    public void delete(String id) {
        deptDao.deleteByPrimaryKey(id);
    }

    @Override
    public Dept findById(String id) {

        return deptDao.selectByPrimaryKey(id);
    }

    /**
     * 根据企业id构造部门下拉框
     * @param companyId:企业id
     * @return 下拉框数据
     */
    @Override
    public List findAll(String companyId) {
        DeptExample deptExample = new DeptExample();
        DeptExample.Criteria criteria = deptExample.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        List<Dept> deptList = deptDao.selectByExample(deptExample);
        return deptList;
    }
}
