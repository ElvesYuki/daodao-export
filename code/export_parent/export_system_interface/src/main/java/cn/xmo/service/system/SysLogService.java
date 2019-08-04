package cn.xmo.service.system;

import cn.xmo.domain.system.SysLog;
import com.github.pagehelper.PageInfo;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 12:58 2019/8/4
 * @Modified By:
 */
public interface SysLogService {

    /**
     * 分页列表
     * @param page
     * @param size
     * @param companyId
     * @return
     */
    PageInfo findAll(int page, int size, String companyId);

    /**
     * 保存
     * @param log
     */
    void save(SysLog log);
}
