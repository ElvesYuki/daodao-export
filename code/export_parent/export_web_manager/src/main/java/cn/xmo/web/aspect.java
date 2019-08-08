package cn.xmo.web;

import cn.xmo.domain.system.SysLog;
import cn.xmo.domain.system.User;
import cn.xmo.service.system.SysLogService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Author: Elves
 * @Description:日志记录的切面类
 * @Date: Created in 15:06 2019/8/4
 * @Modified By:
 */
@Component
@Aspect
public class aspect {

    @Reference
    private SysLogService sysLogService;

    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    @Around(value = "execution(* cn.xmo.web.controller.*.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        SysLog log = new SysLog();

        MethodSignature ms = (MethodSignature)pjp.getSignature();
        Method method = ms.getMethod();
        if (method.isAnnotationPresent(RequestMapping.class)){
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            String path = annotation.value()[0];
            String name = annotation.name();
            //添加基本属性
            log.setIp(request.getLocalAddr());
            log.setTime(new Date());
            Object obj = session.getAttribute("loginUser");
            if (obj != null){
                User user = (User)obj;
                log.setUserName(user.getUserName());
                log.setCompanyId(user.getCompanyId());
                log.setCompanyName(user.getCompanyName());
            }else {
                log.setUserName("匿名");
                log.setCompanyId("1");
            }
            log.setMethod(method.getName());
            log.setAction(path+"-"+name);
        }

        sysLogService.save(log);
        return pjp.proceed();
    }
    /*切换仓库测试*/
}
