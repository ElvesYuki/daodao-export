package cn.xmo.service.system;

import cn.xmo.domain.system.User;
import cn.xmo.domain.system.UserExample;
import com.github.pagehelper.PageInfo;

/**
 * @Author: Elves
 * @Description:此处使用dubbo的@Service注解
 * @Date: Created in 18:19 2019/7/9
 * @Modified By:
 */
public interface UserService  {
    /**
     * 展示页面，查询所有
     * @param page:分页查询参数
     * @param size:分页查询大小
     * @param userExample:查询条件
     * @return PageInfo:返回分页查询对象
     */
    PageInfo findAll(int page, int size, UserExample userExample);

    /**
     * 保存
     * @param user:保存对象
     */
    void save(User user);

    /**
     * 更新
     * @param user:更新对象
     */
    void update(User user);

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
    User findById(String id);
}
