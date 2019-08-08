package cn.xmo.service.cargo;

import cn.xmo.domain.cargo.ExtCproduct;
import cn.xmo.domain.cargo.ExtCproductExample;
import com.github.pagehelper.PageInfo;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 15:42 2019/8/5
 * @Modified By:
 */
public interface ExtCproductService {


    PageInfo findAll(int page, int size, ExtCproductExample example);

    void save(ExtCproduct extCproduct);

    void update(ExtCproduct extCproduct);

    void delete(String id);

    ExtCproduct findById(String id);

}
