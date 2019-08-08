package cn.xmo.service.cargo;

import cn.xmo.domain.cargo.ExportProduct;
import cn.xmo.domain.cargo.ExportProductExample;

import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 21:10 2019/8/5
 * @Modified By:
 */
public interface ExportProductService {

    List<ExportProduct> findAll(ExportProductExample example);

    ExportProduct findById(String id);


}
