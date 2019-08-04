package cn.xmo.service.system;

import cn.xmo.domain.system.Module;
import cn.xmo.domain.system.ModuleExample;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 18:41 2019/7/9
 * @Modified By:
 */
public interface ModuleService {
    /**
     * 展示页面，查询所有
     * @param page:分页查询参数
     * @param size:分页查询大小
     * @param moduleExample:查询条件
     * @return PageInfo:返回分页查询对象
     */
    PageInfo findAll(int page, int size, ModuleExample moduleExample);

    /**
     * 保存
     * @param module:保存对象
     */
    void save(Module module);

    /**
     * 更新
     * @param module:更新对象
     */
    void update(Module module);

    /**
     * 删除
     * @param id:根据id删除
     */
    void delete(String id);

    /**
     * 根据id查询
     * @param id:查询条件
     * @return Company;查询返回对象
     */
    Module findById(String id);

    List findAll();

    /**
     * 根据角色id查询所有的模块
     * @param roleId
     * @return
     */
    List<Module> findByRoleId(String roleId);

    /**
     * 根据用户id查询用户的所有可操作模块
     * @param userId
     * @return
     */
    List<Module> findByUserId(String userId);
}
