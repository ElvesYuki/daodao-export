package cn.xmo.service.system.impl;

import cn.xmo.dao.system.RoleDao;
import cn.xmo.domain.system.Role;
import cn.xmo.domain.system.RoleExample;
import cn.xmo.service.system.RoleService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: Elves
 * @Description:此处使用dubbo的@Service注解
 * @Date: Created in 19:09 2019/7/9
 * @Modified By:
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public PageInfo findAll(int page, int size, RoleExample roleExample) {
        PageHelper.startPage(page, size);
        List<Role> list = roleDao.selectByExample(roleExample);
        return new PageInfo<>(list);
    }

    @Override
    public void save(Role role) {

    }

    @Override
    public void update(Role role) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Role findById(String id) {
        return null;
    }

    @Override
    public void updateRoleModule(String roleId, String moduleIds) {

    }

    @Override
    public List findAll(String loginCompanyId) {
        return null;
    }

    @Override
    public List<Role> findByUserId(String id) {
        return null;
    }
}
