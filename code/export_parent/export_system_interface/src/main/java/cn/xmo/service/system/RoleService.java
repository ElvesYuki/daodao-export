package cn.xmo.service.system;

import cn.xmo.domain.system.Role;
import cn.xmo.domain.system.RoleExample;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 19:02 2019/7/9
 * @Modified By:
 */
public interface RoleService {
    /**
     * 展示页面，查询所有
     * @param page:分页查询参数
     * @param size:分页查询大小
     * @param roleExample:查询条件
     * @return PageInfo:返回分页查询对象
     */
    PageInfo findAll(int page, int size, RoleExample roleExample);

    /**
     * 保存
     * @param role:保存对象
     */
    void save(Role role);

    /**
     * 更新
     * @param role:更新对象
     */
    void update(Role role);

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
    Role findById(String id);

    /**
     * 分配权限:对角色模块中间表添加数据
     * @param roleId:用户角色id
     * @param moduleIds:角色对应的模块
     */
    void updateRoleModule(String roleId, String moduleIds);

    /**
     * 查询全部角色
     * @param loginCompanyId:登录用户企业id
     * @return list:返回角色list集合
     */
    List findAll(String loginCompanyId);

    List<Role> findByUserId(String id);
}
