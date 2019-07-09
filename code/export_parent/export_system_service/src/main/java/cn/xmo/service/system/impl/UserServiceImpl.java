package cn.xmo.service.system.impl;

import cn.xmo.dao.system.UserDao;
import cn.xmo.domain.system.User;
import cn.xmo.domain.system.UserExample;
import cn.xmo.service.system.UserService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    @Override
    public PageInfo findAll(int page, int size, UserExample userExample) {
        PageHelper.startPage(page, size);
        List<User> list = userDao.selectByExample(userExample);
        return new PageInfo<>(list);
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public User findById(String id) {
        return null;
    }
}
