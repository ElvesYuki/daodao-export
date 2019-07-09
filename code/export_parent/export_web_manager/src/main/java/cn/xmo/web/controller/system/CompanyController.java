package cn.xmo.web.controller.system;

import cn.xmo.domain.system.CompanyExample;
import cn.xmo.service.system.CompanyService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 16:15 2019/7/9
 * @Modified By:
 */
@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {

    @Reference
    private CompanyService companyService;

    /**
     * 展示页面，分页展示企业
     * @param page:分页查询参数
     * @param size:分页查询大小
     * @return String:返回的访问页面地址
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size){
        CompanyExample companyExample = new CompanyExample();
        PageInfo info = companyService.findAll(page, size, companyExample);
        request.setAttribute("page",info);
        return "company/company-list";
    }
}
