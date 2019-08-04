package cn.xmo.web.controller.system;

import cn.xmo.domain.system.Company;
import cn.xmo.domain.system.CompanyExample;
import cn.xmo.service.system.CompanyService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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

    /**
     * 进入到新增界面
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "company/company-add";
    }
    /**
     * 进入到更新的页面       ${company.name}
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        //1.获取请求的id参数
        //2.调用service根据id查询
        Company company = companyService.findById(id);
        //3.存入reqeust域中
        request.setAttribute("company",company);
        //4.跳转到更新的页面
        return "company/company-update";
    }

    /**
     *
     * @param company 前端页面传入对象
     * @return 请求地址
     */
    @RequestMapping("/edit")
    public String edit(Company company){
        if (StringUtils.isEmpty(company.getId())){
            companyService.save(company);
        }else{
            companyService.update(company);
        }
        return "redirect:/company/list.do";
    }

    /**
     * 删除企业
     */
    @RequestMapping("/delete")
    public String delete(String id) {
        companyService.delete(id);
        return "redirect:/company/list.do";
    }
}
