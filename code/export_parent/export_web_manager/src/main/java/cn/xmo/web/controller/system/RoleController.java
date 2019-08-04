package cn.xmo.web.controller.system;

import cn.xmo.domain.system.Module;
import cn.xmo.domain.system.Role;
import cn.xmo.domain.system.RoleExample;
import cn.xmo.service.system.DeptService;
import cn.xmo.service.system.ModuleService;
import cn.xmo.service.system.RoleService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        PageInfo info = roleService.findAll(page, size, roleExample);
        request.setAttribute("page",info);
        return "system/role/role-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "system/role/role-add";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        //1.根据id查询
        Role role = roleService.findById(id);
        request.setAttribute("role",role);
        //2.跳转页面
        return "system/role/role-update";
    }

    @RequestMapping("/edit")
    public String edit(Role role) {
        //1.设置企业id
        role.setCompanyId(getLoginCompanyId());
        role.setCompanyName(getLoginCompanyName());
        if(StringUtils.isEmpty(role.getRoleId())){
            //保存
            roleService.save(role);
        }else{
            //更新
            roleService.update(role);
        }
        //跳转页面
        return "redirect:/system/role/list.do";
    }

    @RequestMapping("/delete")
    public String delete(String id) {
        roleService.delete(id);
        return "redirect:/system/role/list.do";
    }

    /**
     * 进入分配权限的页面
     *    根据id查询角色
     */
    @RequestMapping("/roleModule")
    public String roleModule(String roleId) {
        //根据id查询角色
        Role role = roleService.findById(roleId);
        request.setAttribute("role",role);
        return "system/role/role-module";
    }

    @Reference
    private ModuleService moduleService;

    /**
     * 构造ztree树的节点数据
     *          //节点数据
     var zNodes =[
     { id:11, pId:1, name:"随意勾选 1-1"},
     { id:111, pId:11, name:"随意勾选 1-1-1"}
     ];
     *    1.方法的返回值 :list<Map>集合
     *    2.需要在返回值@ResponseBody
     *    3.List集合中的每个元素 Map集合
     */
    @RequestMapping("/getZtreeNodes")
    public @ResponseBody List getZtreeNodes(String roleId) {
        List modules = new ArrayList();
        //1.查询所有的菜单
        List<Module> list = moduleService.findAll();
        //2.根据当前的角色id查询此角色的所有可访问菜单
        // 部门管理,用户管理,系统管理
        List<Module> roleModules = moduleService.findByRoleId(roleId);
        //3.循环所有的菜单,构造一个一个对象数据
        for (Module module : list) {
            Map map = new HashMap<>();
            map.put("id",module.getModuleId());
            map.put("pId",module.getParentId());
            map.put("name",module.getName());
            //判断角色的模块集合中是否有当前的模块对象
            if (roleModules.contains(module)) {
                map.put("checked",true);
            }
            modules.add(map);
        }
        return modules;
    }

    @RequestMapping("/updateRoleModule")
    public String updateRoleModule(String roleId,String moduleIds) {
        roleService.updateRoleModule(roleId ,moduleIds);
        return "redirect:/system/role/list.do";
    }
}
