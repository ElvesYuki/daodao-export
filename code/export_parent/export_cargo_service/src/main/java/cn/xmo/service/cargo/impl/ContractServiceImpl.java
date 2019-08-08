package cn.xmo.service.cargo.impl;

import cn.xmo.dao.cargo.ContractDao;
import cn.xmo.dao.cargo.ContractProductDao;
import cn.xmo.dao.cargo.ExtCproductDao;
import cn.xmo.domain.cargo.Contract;
import cn.xmo.domain.cargo.ContractExample;
import cn.xmo.service.cargo.ContractService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Elves
 * @Description: 购销合同的增删改查接口
 * @Date: Created in 14:05 2019/8/5
 * @Modified By:
 */
@Service
public class ContractServiceImpl implements ContractService {

	@Autowired
	private ContractDao contractDao;
	@Autowired
	private ContractProductDao contractProductDao;
	@Autowired
	private ExtCproductDao extCproductDao;

	/**
	 * 分页查询列表
	 *
	 * @param page
	 * @param size
	 * @param example
	 */
	@Override
	public PageInfo findAll(int page, int size, ContractExample example) {
		PageHelper.startPage(page, size);
		List<Contract> list = contractDao.selectByExample(example);
		return new PageInfo(list);
	}

	@Override
	public void save(Contract contract) {
		//1.设置id
		contract.setId(UUID.randomUUID().toString());
		//2.设置基本属性
		//设置状态  0：草稿 ，1:已确认
		contract.setState(0);
		//创建时间
		contract.setCreateTime(new Date());
		//货物数量
		contract.setProNum(0);
		//附件数量
		contract.setExtNum(0);
		contract.setTotalAmount(0d);
		contractDao.insertSelective(contract);

	}

	@Override
	public void update(Contract contract) {
		contractDao.updateByPrimaryKeySelective(contract);
	}

	@Override
	public void delete(String id) {
		//删除附件（根据购销合同id删除附件）
		extCproductDao.deleteByContractId(id);
		//删除货物(根据购销合同id删除货物)
		contractProductDao.deleteByContractId(id);
		//删除购销合同
		contractDao.deleteByPrimaryKey(id);

	}

	@Override
	public Contract findById(String id) {
		return contractDao.selectByPrimaryKey(id);
	}
}
