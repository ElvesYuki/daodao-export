package cn.xmo.dao.system;

import cn.xmo.domain.system.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 19:12 2019/8/3
 * @Modified By:
 */
public interface RoleModuleDao {

    void deleteRoleModule(String roleId);

    void saveRoleModule(@Param("roleId") String roleId, @Param("moduleId") String moduleId);


    void deleteUserRole(String userId);

    void saveUserRole(@Param("userId") String userId,@Param("roleId") String roleId);
}
