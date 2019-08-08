package cn.xmo.service.cargo;

import cn.xmo.domain.cargo.Contract;
import cn.xmo.domain.cargo.ContractExample;
import com.github.pagehelper.PageInfo;

/**
 * 购销合同的service接口
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
