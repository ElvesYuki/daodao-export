package cn.xmo.service.system.impl;

import cn.xmo.com.utils.Encrypt;
import cn.xmo.dao.system.RoleModuleDao;
import cn.xmo.dao.system.UserDao;
import cn.xmo.domain.system.User;
import cn.xmo.domain.system.UserExample;
import cn.xmo.service.system.UserService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @Author: Elves
 * @Description:此处使用dubbo的@Service注解
 * @Date: Created in 18:22 2019/7/9
 * @Modified By:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleModuleDao roleModuleDao;

    @Override
    public PageInfo findAll(int page, int size, UserExample userExample) {
        PageHelper.startPage(page, size);
        List<User> list = userDao.selectByExample(userExample);
        return new PageInfo<>(list);
    }

    @Override
    public void save(User user) {
        user.setUserId(UUID.randomUUID().toString());
        String password = Encrypt.md5(user.getPassword(), user.getEmail());
        user.setPassword(password);
        userDao.insertSelective(user);
    }

    @Override
    public void update(User user) {
        String password = Encrypt.md5(user.getPassword(), user.getEmail());
        user.setPassword(password);
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public void delete(String id) {
        userDao.deleteByPrimaryKey(id);
    }

    @Override
    public User findById(String id) {
        return userDao.selectByPrimaryKey(id);
    }

    //对用户分配角色
    /**
     * 1.调用dao删除用户角色中间表数据
     * 2.循环调用dao保存用户角色中间表数据
     */
    @Override
    public void changeRole(String userId, String[] roleIds) {
        //1.调用dao删除用户角色中间表数据
        roleModuleDao.deleteUserRole(userId);
        //2.循环调用dao保存用户角色中间表数据
        for (String roleId : roleIds) {
            roleModuleDao.saveUserRole(userId, roleId);
        }
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }


}
