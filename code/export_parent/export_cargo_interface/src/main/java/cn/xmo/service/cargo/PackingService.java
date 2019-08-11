package cn.xmo.service.cargo;

import cn.xmo.domain.cargo.Packing;
import cn.xmo.domain.cargo.PackingExample;
import com.github.pagehelper.PageInfo;

import java.util.List;
/**
 * @Author: Elves
 * @Description: 装箱模块接口
 * @Date: Created in 11:45 2019/8/10
 * @Modified By:
 */
public interface PackingService {

    PageInfo findAll(PackingExample packingExample, int page, int size);

    void save(Packing packing);

    void update(Packing packing);

    void delete(String id);

    Packing findById(String id);

    List<Packing> findAll(PackingExample packingExample);

}
