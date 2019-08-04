package cn.xmo.web.controller;

import cn.xmo.domain.system.Module;
import cn.xmo.domain.system.User;
import cn.xmo.service.system.ModuleService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author: Elves
 * @Description:用户登录
 * @Date: Created in 17:37 2019/7/9
 * @Modified By:
 */
@Controller
public class LoginController extends BaseController {

	@Reference
	private ModuleService moduleService;

	/**
	 * 基于shiro的用户登录
	 * @param email 登录邮箱
	 * @param password 登录密码
	 * @return 跳转页面
	 */
	@RequestMapping("/login")
	public String login(String email,String password) {
		//1.将用户输入的邮箱和密码转化为shiro中的token对象(token:就是邮箱和密码)
		UsernamePasswordToken token = new UsernamePasswordToken(email,password);
		//2.获取subject对象
		Subject subject = SecurityUtils.getSubject();
		try {
			//3.调用subject对象的login方法进入到realm域的认证方法
			subject.login(token);
			//4.如果代码没有异常,登录成功
			//如果使用shiro进入登录登录成,会将用户的对象存入到shiro内部
			//从shiro中获取到用户对象,并保存到session中即可
			//获取安全对象: 登录的用户对象user
			User user = (User)subject.getPrincipal();
			session.setAttribute("loginUser",user);
			//构造动态菜单的相关数据
			List<Module> moduleList = moduleService.findByUserId(user.getUserId());
			session.setAttribute("modules",moduleList);
			return "home/main";
		}catch (Exception e) {
			e.printStackTrace();
			//5.如果出现异常:登录失败
			//重新登录
			request.setAttribute("error","用户名或密码错误");
			return "forward:/login.jsp";
		}
	}


	@RequestMapping("logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
		return "forward:login.jsp";
    }

    @RequestMapping("home")
    public String home(){
        return "home/home";
    }

}
