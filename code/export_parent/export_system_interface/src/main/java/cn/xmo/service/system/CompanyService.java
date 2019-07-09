package cn.xmo.service.system;

import cn.xmo.domain.system.Company;
import cn.xmo.domain.system.CompanyExample;
import com.github.pagehelper.PageInfo;

/**
 * @Author: Elves
 * @Description:企业模块的增删改查接口
 * @Date: Created in 15:43 2019/7/9
 * @Modified By:
 */
public interface CompanyService {
    /**
     * 展示页面，查询所有
     * @param page:分页查询参数
     * @param size:分页查询大小
     * @param companyExample:查询条件
     * @return PageInfo:返回分页查询对象
     */
    PageInfo findAll(int page, int size, CompanyExample companyExample);

    /**
     * 保存
     * @param company:保存对象
     */
    void save(Company company);

    /**
     * 更新
     * @param company:更新对象
     */
    void update(Company company);

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
    Company findById(String id);
}
