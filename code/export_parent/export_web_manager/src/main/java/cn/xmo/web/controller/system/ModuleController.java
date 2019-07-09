package cn.xmo.web.controller.system;

import cn.xmo.domain.system.ModuleExample;
import cn.xmo.service.system.ModuleService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 18:49 2019/7/9
 * @Modified By:
 */
@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController {

    @Reference
    private ModuleService moduleService;

    /**
     * 展示页面，分页展示模块
     * @param page:分页查询参数
     * @param size:分页查询大小
     * @return String:返回的访问页面地址
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {
        ModuleExample moduleExample = new ModuleExample();
        PageInfo info = moduleService.findAll(page, size, moduleExample);
        request.setAttribute("page",info);
        return "system/module/module-list";
    }
}
