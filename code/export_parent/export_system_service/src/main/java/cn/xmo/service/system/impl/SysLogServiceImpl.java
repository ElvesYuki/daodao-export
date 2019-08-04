package cn.xmo.service.system.impl;

import cn.xmo.dao.system.SysLogDao;
import cn.xmo.domain.system.SysLog;
import cn.xmo.service.system.SysLogService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 12:59 2019/8/4
 * @Modified By:
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogDao sysLogDao;
    /**
     * 分页列表
     *
     * @param page
     * @param size
     * @param companyId
     * @return
     */
    @Override
    public PageInfo findAll(int page, int size, String companyId) {
        PageHelper.startPage(page,size);
        List<SysLog> list = sysLogDao.findAll(companyId);
        return new PageInfo(list);
    }

    /**
     * 保存
     *
     * @param log
     */
    @Override
    public void save(SysLog log) {
        log.setId(UUID.randomUUID().toString());
        sysLogDao.save(log);
    }
}
