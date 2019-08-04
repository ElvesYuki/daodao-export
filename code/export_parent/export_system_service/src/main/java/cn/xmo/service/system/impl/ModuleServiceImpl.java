package cn.xmo.service.system.impl;

import cn.xmo.dao.system.ModuleDao;
import cn.xmo.dao.system.UserDao;
import cn.xmo.domain.system.Module;
import cn.xmo.domain.system.ModuleExample;
import cn.xmo.domain.system.User;
import cn.xmo.service.system.ModuleService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
@Service
/**
 * @Author: Elves
 * @Description:此处使用dubbo的@Service注解
 * @Date: Created in 18:46 2019/7/9
 * @Modified By:
 */
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private UserDao userDao;

    @Override
    public PageInfo findAll(int page, int size, ModuleExample moduleExample) {
        PageHelper.startPage(page,size);
        List<Module> list = moduleDao.selectByExample(moduleExample);
        return new PageInfo<>(list);
    }

    @Override
    public void save(Module module) {
        module.setModuleId(UUID.randomUUID().toString());
        moduleDao.insertSelective(module);
    }

    @Override
    public void update(Module module) {
        moduleDao.updateByPrimaryKeySelective(module);
    }

    @Override
    public void delete(String id) {
        moduleDao.deleteByPrimaryKey(id);
    }

    @Override
    public Module findById(String id) {
        return moduleDao.selectByPrimaryKey(id);
    }

    @Override
    public List findAll() {
        ModuleExample moduleExample = new ModuleExample();
        return moduleDao.selectByExample(moduleExample);
    }

    @Override
    public List<Module> findByRoleId(String roleId) {
        return moduleDao.findByRoleId(roleId);
    }

    @Override
    public List<Module> findByUserId(String userId) {
        //1.根据用户id查询用户
        User user = userDao.selectByPrimaryKey(userId);
        //2.获取用户的等级
        Integer degree = user.getDegree();
        //3.根据不同的等级判断
        if(degree ==0) {
            // 3.1 如果为saas管理员  : 查询belong=0的所有菜单
            return moduleDao.findByBelong(0);
        }else if(degree ==1){
            // 3.2 如果为企业管理员   : 查询belong=1的所有菜单
            return moduleDao.findByBelong(1);
        }else{
            // 3.3 如果是企业的冉员工 : 根据用户id按照rbac的数据库表关联查询所有模块
            return moduleDao.findByUserId(userId);
        }
    }
}
