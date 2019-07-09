package cn.xmo.service.system.impl;

import cn.xmo.dao.system.ModuleDao;
import cn.xmo.domain.system.Module;
import cn.xmo.domain.system.ModuleExample;
import cn.xmo.service.system.ModuleService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: Elves
 * @Description:此处使用dubbo的@Service注解
 * @Date: Created in 18:46 2019/7/9
 * @Modified By:
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    @Override
    public PageInfo findAll(int page, int size, ModuleExample moduleExample) {
        PageHelper.startPage(page,size);
        List<Module> list = moduleDao.selectByExample(moduleExample);
        return new PageInfo<>(list);
    }

    @Override
    public void save(Module module) {

    }

    @Override
    public void update(Module module) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Module findById(String id) {
        return null;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public List<Module> findByRoleId(String roleId) {
        return null;
    }

    @Override
    public List<Module> findByUserId(String id) {
        return null;
    }
}
