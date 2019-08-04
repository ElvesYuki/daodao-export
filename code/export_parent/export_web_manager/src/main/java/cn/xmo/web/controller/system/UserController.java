package cn.xmo.web.controller.system;

import cn.xmo.domain.system.Role;
import cn.xmo.domain.system.User;
import cn.xmo.domain.system.UserExample;
import cn.xmo.service.system.DeptService;
import cn.xmo.service.system.RoleService;
import cn.xmo.service.system.UserService;
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
 * @Date: Created in 18:25 2019/7/9
 * @Modified By:
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Reference
    private UserService userService;

    @Reference
    private DeptService deptService;
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

    @RequestMapping("/toAdd")
    public String toAdd() {
        //1.查询当前登录用户的所有部门
        List deptList = deptService.findAll(getLoginCompanyId());
        //2.将部门列表存入到request,为了构造下拉框数据
        request.setAttribute("deptList",deptList);
        //3.跳转页面
        return "system/user/user-add";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        //1.查询当前登录用户的所有部门
        List deptList = deptService.findAll(getLoginCompanyId());
        //2.将部门列表存入到request,为了构造下拉框数据
        request.setAttribute("deptList",deptList);
        //3.根据id查询
        User user = userService.findById(id);
        request.setAttribute("user",user);
        //3.跳转页面
        return "system/user/user-update";
    }

    @RequestMapping("/edit")
    public String edit(User user) {
        //1.设置企业id
        user.setCompanyId(getLoginCompanyId());
        user.setCompanyName(getLoginCompanyName());
        String password = user.getPassword() ;
        user.setPassword(password);
        if(StringUtils.isEmpty(user.getUserId())){
            userService.save(user);
/*            //保存成功,向用户邮箱发送一封邮件
            String to = user.getEmail();
            String subject = "欢迎使用saas-export系统";
            String content = "尊敬的用户:"+user.getUserName()+"你好,欢迎使用saas系统,您登陆的访问url:http://localhost:8088/login.jsp," +
                    "登陆账户:"+to + ",登陆密码:"+password;
            try {
                MailUtil.sendMsg(to,subject,content);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }else{
            userService.update(user);
        }
        return "redirect:/system/user/list.do";
    }

    @RequestMapping("/delete")
    public String delete(String id) {
        userService.delete(id);
        return "redirect:/system/user/list.do";
    }

    @Reference
    private RoleService roleService;

    @RequestMapping("/roleList")
    public String roleList(String id) {
        //1.根据用户id查询用户数据
        User user = userService.findById(id);
        request.setAttribute("user",user);
        //2.查询所有的角色数据
        List roleList = roleService.findAll(getLoginCompanyId());
        request.setAttribute("roleList",roleList);
        //3.根据用户id查询此用户的所有角色数据
        List<Role> userRoles = roleService.findByUserId(id);
        //构造页面需要的角色id字符串    1,2,3,4,5,6,    1
        String userRoleStr = "";
        for (Role userRole : userRoles) {
            userRoleStr += userRole.getRoleId() +",";
        }
        request.setAttribute("userRoleStr",userRoleStr);
        return "system/user/user-role";
    }

    /**
     * 对用户分配角色
     *  1.参数
     *  2.业务:调用service
     */
    @RequestMapping("/changeRole")
    public String changeRole(String userId,String [] roleIds) {
        userService.changeRole(userId,roleIds);
        return "redirect:/system/user/list.do";
    }


}
