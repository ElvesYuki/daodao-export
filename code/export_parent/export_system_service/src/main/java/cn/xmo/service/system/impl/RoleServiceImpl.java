package cn.xmo.service.system.impl;

import cn.xmo.dao.system.RoleDao;
import cn.xmo.dao.system.RoleModuleDao;
import cn.xmo.dao.system.UserDao;
import cn.xmo.domain.system.Role;
import cn.xmo.domain.system.RoleExample;
import cn.xmo.service.system.RoleService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

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

    @Autowired
    private RoleModuleDao roleModuleDao;

    @Override
    public PageInfo findAll(int page, int size, RoleExample roleExample) {
        PageHelper.startPage(page, size);
        List<Role> list = roleDao.selectByExample(roleExample);
        return new PageInfo<>(list);
    }

    @Override
    public void save(Role role) {
        role.setRoleId(UUID.randomUUID().toString());
        roleDao.insert(role);
    }

    @Override
    public void update(Role role) {
        roleDao.updateByPrimaryKeySelective(role);
    }

    @Override
    public void delete(String id) {
        roleDao.deleteByPrimaryKey(id);
    }

    @Override
    public Role findById(String id) {
        return roleDao.selectByPrimaryKey(id);
    }

    /**
     * 分配权限:对角色模块中间表添加数据
     *
     * @param roleId    :用户角色id
     * @param moduleIds :角色对应的模块
     */
    @Override
    public void updateRoleModule(String roleId, String moduleIds) {
        String[] mids = moduleIds.split(",");
        //根据角色id删除中间表数据
        roleModuleDao.deleteRoleModule(roleId);
        //循环向中间表中添加数据
        for (String moduleId : mids) {
            roleModuleDao.saveRoleModule(roleId ,moduleId);
        }
    }


    @Override
    public List findAll(String loginCompanyId) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andCompanyIdEqualTo(loginCompanyId);
        return roleDao.selectByExample(roleExample);
    }

    @Override
    public List<Role> findByUserId(String id) {
        return  roleDao.findByUserId(id);
    }
}
