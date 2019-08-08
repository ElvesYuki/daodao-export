package cn.xmo.service.cargo.impl;

import cn.xmo.dao.cargo.ContractDao;
import cn.xmo.dao.cargo.ExtCproductDao;
import cn.xmo.domain.cargo.Contract;
import cn.xmo.domain.cargo.ExtCproduct;
import cn.xmo.domain.cargo.ExtCproductExample;
import cn.xmo.service.cargo.ExtCproductService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 15:47 2019/8/5
 * @Modified By:
 */
@Service
public class ExtCproductServiceImpl implements ExtCproductService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ExtCproductDao extCproductDao;

    @Override
    public PageInfo findAll(int page, int size, ExtCproductExample example) {
        PageHelper.startPage(page,size);
        List<ExtCproduct> list = extCproductDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Override
    public void save(ExtCproduct extCproduct) {
        //1.设置id
        extCproduct.setId(UUID.randomUUID().toString());
        //2.计算附件的总金额
        double money = 0;
        if (extCproduct.getCnumber() != null && extCproduct.getPrice() != null ) {
            money = extCproduct.getCnumber() * extCproduct.getPrice();
        }
        //3.设置附件总金额
        extCproduct.setAmount(money);
        //4.保存附件
        extCproductDao.insertSelective(extCproduct);
        //5.根据id查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //6.设置购销合同的总金额
        contract.setTotalAmount(contract.getTotalAmount() + money);
        //7.设置附件数量
        contract.setExtNum(contract.getExtNum() + 1);
        //8.更新合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void update(ExtCproduct extCproduct) {
        //1.计算修改之后的总金额
        double newMoney = 0;
        if (extCproduct.getCnumber() != null && extCproduct.getPrice() != null ) {
            newMoney = extCproduct.getCnumber() * extCproduct.getPrice();
        }
        //2.获取修改之前的总金额
        ExtCproduct extc = extCproductDao.selectByPrimaryKey(extCproduct.getId());
        double oldMoney = extc.getAmount();
        //3.设置附件的总金额
        extCproduct.setAmount(newMoney);
        //4.更新附件
        extCproductDao.updateByPrimaryKeySelective(extCproduct);
        //5.查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //6.设置合同总金额
        contract.setTotalAmount(contract.getTotalAmount() - oldMoney + newMoney);
        //7.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
//1.根据id查询附件
        ExtCproduct extc = extCproductDao.selectByPrimaryKey(id);
        //2.获取删除附件的总金额
        Double money = extc.getAmount();
        //3.查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(extc.getContractId());
        //4.设置购销合同总金额
        contract.setTotalAmount(contract.getTotalAmount() - money);
        //5.设置购销合同的附件数
        contract.setExtNum(contract.getExtNum() - 1);
        //6.更新合同
        contractDao.updateByPrimaryKeySelective(contract);
        //7.删除附件
        extCproductDao.deleteByPrimaryKey(id);
    }

    @Override
    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }
}
