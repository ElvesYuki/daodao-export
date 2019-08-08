package cn.xmo.service.stat;

import java.util.List;
import java.util.Map;

/**
 * 统计分析的service接口
 */
public interface StatService {

	/**
	 * 获取厂家销量统计数据
	 * @param companyId
	 * @return
	 */
	List<Map> getFactoryData(String companyId);

	/**
	 * 获取销量排行数据
	 * @param companyId
	 * @return
	 */
	List<Map> getSellData(String companyId);

	/**
	 * 获取系统访问压力图统计数据
	 * @param companyId
	 * @return
	 */
	List<Map> getOnLineData(String companyId);

	/**
	 * 登录ip次数的前十名
	 * @param companyId
	 * @return
	 */
    List<Map> getIpTopTenData(String companyId);
}
