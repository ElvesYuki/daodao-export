package cn.xmo.service.cargo;

import cn.xmo.domain.cargo.Factory;
import cn.xmo.domain.cargo.FactoryExample;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 14:46 2019/8/5
 * @Modified By:
 */
public interface FactoryService {
    /**
     * 查询所有
     * @param example
     * @return
     */
    public List<Factory> findAll(FactoryExample example);

    public PageInfo findAll(int page, int size, FactoryExample factoryExample);

    /**
     * 保存
     */
    void save(Factory factory);

    /**
     * 更新
     */
    void update(Factory factory);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据id查询
     */
    Factory findById(String id);


}
