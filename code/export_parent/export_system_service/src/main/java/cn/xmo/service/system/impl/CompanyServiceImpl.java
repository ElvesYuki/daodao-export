package cn.xmo.service.system.impl;

import cn.xmo.dao.system.CompanyDao;
import cn.xmo.domain.system.Company;
import cn.xmo.domain.system.CompanyExample;
import cn.xmo.service.system.CompanyService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @Author: Elves
 * @Description:此处使用dubbo的@Service注解
 * @Date: Created in 15:53 2019/7/9
 * @Modified By:
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Override
    public PageInfo findAll(int page, int size, CompanyExample companyExample) {
        PageHelper.startPage(page, size);
        List<Company> list = companyDao.selectByExample(companyExample);
        return new PageInfo<>(list);
    }

    /**
     *
     * @param company:保存对象
     */
    @Override
    public void save(Company company) {
        company.setId(UUID.randomUUID().toString());
        company.setState(0);
        companyDao.insert(company);
    }

    /**
     *
     * @param company:更新对象
     */
    @Override
    public void update(Company company) {
        companyDao.updateByPrimaryKeySelective(company);
    }

    @Override
    public void delete(String id) {
        companyDao.deleteByPrimaryKey(id);
    }

    @Override
    public Company findById(String id) {
        return companyDao.selectByPrimaryKey(id);
    }
}
