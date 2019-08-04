package cn.xmo.web.shiro;

import cn.xmo.com.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @Author: Elves
 * @Description: 自定义密码比较器
 * @Date: Created in 17:37 2019/7/9
 * @Modified By:
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

	/**
	 * 重写父类中的doCredentialsMatch
	 *      密码比较方法
	 *      参数:
	 *          token : 用户输入的用户名和密码
	 *          info : 安全数据 (用户对象,数据库密码,域名称)
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		//1.获取到用户登录的密码
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String loginPassword = new String(upToken.getPassword());
		String email = upToken.getUsername();
		//2.获取数据库中的密码
		String dbPassword = (String)info.getCredentials();
		//3.对用户登录的密码进行加密
		loginPassword = Encrypt.md5(loginPassword,email);
		//4.比较用户登录的加密的密码和数据库密码是否一致
		return loginPassword.equals(dbPassword);
	}
}
