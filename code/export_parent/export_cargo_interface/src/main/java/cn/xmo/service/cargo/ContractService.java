package cn.xmo.service.cargo;

import cn.xmo.domain.cargo.Contract;
import cn.xmo.domain.cargo.ContractExample;
import com.github.pagehelper.PageInfo;

/**
 * @Author: Elves
 * @Description: 购销合同
 * @Date: Created in 11:07 2019/8/10
 * @Modified By:
 */
public interface ContractService {

	/**
	 * 分页查询列表
	 */
	PageInfo findAll(int page, int size, ContractExample example);

	void save(Contract contract);

	void update(Contract contract);

	void delete(String id);

	Contract findById(String id);
}
