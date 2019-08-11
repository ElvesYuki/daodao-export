package cn.xmo.service.cargo.impl;

import cn.xmo.dao.cargo.FinanceDao;
import cn.xmo.domain.cargo.Finance;
import cn.xmo.domain.cargo.FinanceExample;
import cn.xmo.service.cargo.FinanceService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 8:50 2019/8/11
 * @Modified By:
 */
@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceDao financeDao;

    @Override
    public PageInfo findAll(int page, int size, FinanceExample financeExample) {
        PageHelper.startPage(page,size);
        List<Finance> list = financeDao.selectByExample(financeExample);
        return new PageInfo(list);
    }

    @Override
    public void save(Finance finance) {
        financeDao.insertSelective(finance);
    }

    @Override
    public void update(Finance finance) {
        financeDao.updateByPrimaryKeySelective(finance);
    }

    @Override
    public void delete(String id) {
        financeDao.deleteByPrimaryKey(id);
    }

    @Override
    public Finance findById(String id) {
        return financeDao.selectByPrimaryKey(id);
    }
}
