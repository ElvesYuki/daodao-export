package cn.xmo.dao.stat;

import java.util.List;
import java.util.Map;

public interface StatDao {

	/**
	 *
	 * 通过mybatis查询,返回map集合
	 *      map集合的key = 查询的字段名
	 *      map集合的value = 查询的数据
	 *
	 * @param companyId
	 * @return
	 */
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
