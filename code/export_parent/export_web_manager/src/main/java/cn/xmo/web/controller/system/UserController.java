package cn.xmo.web.controller.system;

import cn.xmo.domain.system.UserExample;
import cn.xmo.service.system.UserService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 18:25 2019/7/9
 * @Modified By:
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Reference
    private UserService userService;

    /**
     * 展示页面，分页展示用户
     * @param page:分页查询参数
     * @param size:分页查询大小
     * @return String:返回的访问页面地址
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size){
        UserExample userExample = new UserExample();

        PageInfo info = userService.findAll(page, size, userExample);
        request.setAttribute("page",info);
        return "system/user/user-list";
    }
}
