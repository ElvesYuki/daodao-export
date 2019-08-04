package cn.xmo.web.shiro;

import cn.xmo.domain.system.Module;
import cn.xmo.domain.system.User;
import cn.xmo.service.system.ModuleService;
import cn.xmo.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Elves
 * @Description: 自定义realm
 * @Date: Created in 17:37 2019/7/9
 * @Modified By:
 */
public class AuthRealm extends AuthorizingRealm {

	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private ModuleService moduleService;

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}



	/**
	 *  principal : 安全数据(user对象)
	 *  credentials : 密码
	 *
	 *  授权方法: 获取当前登录用户的所有权限数据(模块)
	 *      参数 : principalCollection 安全数据的集合
	 *      返回值 : 所有权限数据
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		//1.从安全数据的集合中获取到登录的用户对象
		User user = (User) principalCollection.getPrimaryPrincipal();
		//2.调用moduleService查询当前用户对象的所有操作权限(模块数据)
		List<Module> moduleList = moduleService.findByUserId(user.getUserId());
		//3.构造所有可访问模块的名称的set集合
		Set<String> permissions = new HashSet<>();
		for (Module module : moduleList) {
			permissions.add(module.getName());
		}
		//4.构造返回值
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permissions);
		return info;
	}




	/**
	 *  身份认证
	 *      参数 AuthenticationToken :  UsernamepasswordToken
	 *      返回值  认证数据(安全数据)  用户对象
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		//1.从token中获取登录的邮箱和密码
		UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
		String email = upToken.getUsername();
		//2.调用service根据邮箱查询用户
		User user = userService.findByEmail(email);
		//3.判断用户是否存在
		if(user != null) {
			//3.1 存在,构造返回值并返回
			//第一参数:安全数据(user),数据库密码,realm域名称(随便写)
			return new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
		}else{
			//3.2 不存在,返回null,自动抛出异常
			System.out.println("reaml:用户不存在");
			return null;
		}
	}


}
