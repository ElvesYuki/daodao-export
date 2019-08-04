package cn.xmo.web.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 22:11 2019/8/4
 * @Modified By:
 * 自定义异常处理器
 */
public class CustomHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) {
        if(ex instanceof UnauthorizedException) {

             //基于shiro注解的授权抛出的异常
             //跳转到未授权页面

            ModelAndView mv = new ModelAndView();
            mv.setViewName("forward:/unauthorized.jsp");
            return mv;
        }else{
            ModelAndView mv = new ModelAndView();
            mv.addObject("errorMsg","对不起,我错了");
            mv.addObject("ex",ex);
            mv.setViewName("error");
            return mv;
        }
    }
}
