package cn.xmo.web.controller;

import cn.xmo.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 16:27 2019/7/9
 * @Modified By:
 */
public class BaseController {
    /**
     * 在方法中获取request对象的话
     *     方式一: 将对象配置到方法参数上
     *     方式二: 可以将对象配置到控制器的属性上
     *     是在发送每次请求的时候,SpringMVC自动的帮助我们获取到的
     */
    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected HttpSession session;


    /**
     * 从当前登录的用户中获取到企业id
     * @return String:当前登录用户id
     */
    public String getLoginCompanyId() {
        /*
         * 获取当前登录的用户
         */
        Object obj = session.getAttribute("loginUser");
        if(obj != null) {
            User user = (User) obj;
            return user.getCompanyId();
        }else{
            return null;
        }
    }

    /**
     * 从当前登录的用户中获取企业的名称
     * @return String:当前登录企业名称
     */
    public String getLoginCompanyName() {
        Object obj = session.getAttribute("loginUser");
        if(obj != null) {
            User user = (User) obj;
            return user.getCompanyName();
        }else{
            return null;
        }
    }

    /**
     * 获取当前登录的用户
     * @return User:用户对象
     */
    public User getLoginUser() {
        Object obj = session.getAttribute("loginUser");
        if(obj != null) {
            return (User) obj;
        }else{
            return null;
        }
    }
}
