package cn.xmo.web.portal.controller;

import cn.xmo.domain.system.Company;
import cn.xmo.service.system.CompanyService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 9:53 2019/8/5
 * @Modified By:
 */
@Controller
public class ApplyController {

    @Reference
    private CompanyService companyService;

    /**
     * 企业申请：
     *  租用企业：申请使用saas系统，输入企业的基本信息
     *          将企业信息保存到企业数据库表中
     */
    @RequestMapping("/apply")
    public @ResponseBody String apply(Company company) {
        try{
            companyService.save(company);
            return "1";
        }catch (Exception e){
            e.printStackTrace();
            return "2";
        }
    }
}
