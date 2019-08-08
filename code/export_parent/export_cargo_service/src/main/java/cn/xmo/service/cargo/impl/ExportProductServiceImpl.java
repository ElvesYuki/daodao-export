package cn.xmo.service.cargo.impl;

import cn.xmo.dao.cargo.ExportProductDao;
import cn.xmo.domain.cargo.ExportProduct;
import cn.xmo.domain.cargo.ExportProductExample;
import cn.xmo.service.cargo.ExportProductService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 21:11 2019/8/5
 * @Modified By:
 */
@Service
public class ExportProductServiceImpl implements ExportProductService {
    @Autowired
    private ExportProductDao exportProductDao;

    @Override
    public List<ExportProduct> findAll(ExportProductExample example) {
        return exportProductDao.selectByExample(example);
    }

    @Override
    public ExportProduct findById(String id) {
        return exportProductDao.selectByPrimaryKey(id);
    }
}
