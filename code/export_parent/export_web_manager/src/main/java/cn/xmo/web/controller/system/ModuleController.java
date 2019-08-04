package cn.xmo.web.controller.system;

import cn.xmo.domain.system.Module;
import cn.xmo.domain.system.ModuleExample;
import cn.xmo.service.system.ModuleService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @RequestMapping("/toAdd")
    public String toAdd() {
        //查询所有模块
        List moduleList = moduleService.findAll();
        request.setAttribute("menus",moduleList);
        return "system/module/module-add";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        //查询所有模块
        List moduleList = moduleService.findAll();
        request.setAttribute("menus",moduleList);

        Module module = moduleService.findById(id);
        request.setAttribute("module",module);
        return "system/module/module-update";
    }

    @RequestMapping("/edit")
    public String edit(Module module) {
        if(StringUtils.isEmpty(module.getModuleId())){
            moduleService.save(module);
        }else{
            moduleService.update(module);
        }
        return "redirect:/system/module/list.do";
    }

    @RequestMapping("/delete")
    public String delete(String id) {
        moduleService.delete(id);
        return "redirect:/system/module/list.do";
    }
}
