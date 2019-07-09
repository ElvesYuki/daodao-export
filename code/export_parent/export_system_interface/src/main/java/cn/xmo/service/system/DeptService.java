package cn.xmo.service.system;

import cn.xmo.domain.system.Dept;
import cn.xmo.domain.system.DeptExample;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 19:20 2019/7/9
 * @Modified By:
 */
public interface DeptService {
    /**
     * 展示页面，查询所有
     * @param page:分页查询参数
     * @param size:分页查询大小
     * @param deptExample:查询条件
     * @return PageInfo:返回分页查询对象
     */
    PageInfo findAll(int page, int size, DeptExample deptExample);

    /**
     * 保存
     * @param dept:保存对象
     */
    void save(Dept dept);

    /**
     * 更新
     * @param dept:更新对象
     */
    void update(Dept dept);

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
    Dept findById(String id);


    /**
     * 根据企业id查询所有
     * @param companyId:企业id
     * @return List:所有部门的list集合
     */
    List findAll(String companyId);
}
