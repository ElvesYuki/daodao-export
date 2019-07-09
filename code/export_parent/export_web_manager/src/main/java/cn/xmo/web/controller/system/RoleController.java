package cn.xmo.web.controller.system;

import cn.xmo.domain.system.RoleExample;
import cn.xmo.service.system.RoleService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 19:11 2019/7/9
 * @Modified By:
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Reference
    private RoleService roleService;

    /**
     * 展示页面，分页展示角色
     * @param page:分页查询参数
     * @param size:分页查询大小
     * @return String:返回的访问页面地址
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andCompanyIdEqualTo(getLoginCompanyId());
        PageInfo info = roleService.findAll(page, size, roleExample);
        request.setAttribute("page",info);
        return "system/role/role-list";
    }
}
