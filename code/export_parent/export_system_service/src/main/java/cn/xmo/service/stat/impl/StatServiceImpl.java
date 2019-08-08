package cn.xmo.service.stat.impl;

import cn.xmo.dao.stat.StatDao;
import cn.xmo.service.stat.StatService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 11:51 2019/8/8
 * @Modified By:
 */
@Service
public class StatServiceImpl implements StatService {

    @Autowired
    private StatDao statDao;

    /**
     * 获取厂家销量统计数据
     *
     * @param companyId
     * @return
     */
    @Override
    public List<Map> getFactoryData(String companyId) {
        return statDao.getFactoryData(companyId);
    }

    /**
     * 获取销量排行数据
     *
     * @param companyId
     * @return
     */
    @Override
    public List<Map> getSellData(String companyId) {
        return statDao.getSellData(companyId);
    }

    /**
     * 获取系统访问压力图统计数据
     *
     * @param companyId
     * @return
     */
    @Override
    public List<Map> getOnLineData(String companyId) {
        return statDao.getOnLineData(companyId);
    }

    /**
     * 登录ip次数的前十名
     *
     * @param companyId
     * @return
     */
    @Override
    public List<Map> getIpTopTenData(String companyId) {
        return statDao.getIpTopTenData(companyId);
    }
}
