package cn.xmo.web.controller.system;

import cn.xmo.domain.system.DeptExample;
import cn.xmo.service.system.DeptService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 19:27 2019/7/9
 * @Modified By:
 */
@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {

    @Reference
    private DeptService deptService;

    /**
     * 展示页面，分页展示部门
     * @param page:分页查询参数
     * @param size:分页查询大小
     * @return String:返回的访问页面地址
     */
    @RequestMapping(value = "/list",name = "查询部门列表")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {
        DeptExample deptExample = new DeptExample();
        DeptExample.Criteria criteria = deptExample.createCriteria();
        criteria.andCompanyIdEqualTo(getLoginCompanyId());
        PageInfo info = deptService.findAll(page, size, deptExample);
        request.setAttribute("page",info);
        return "system/dept/dept-list";
    }
}
