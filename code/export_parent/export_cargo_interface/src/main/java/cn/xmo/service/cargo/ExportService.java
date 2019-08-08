package cn.xmo.service.cargo;

import cn.xmo.domain.cargo.Export;
import cn.xmo.domain.cargo.ExportExample;
import cn.xmo.vo.ExportResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 20:37 2019/8/5
 * @Modified By:
 */
public interface ExportService {

    PageInfo findAll (ExportExample example, int page, int size );

    void save ( Export export );

    void update ( Export export );

    Export findById ( String id );

    void updateE (ExportResult exportResult);

    List<Export> findAll (ExportExample example );

}
