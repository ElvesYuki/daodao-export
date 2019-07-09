package cn.xmo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Elves
 * @Description:用户登录
 * @Date: Created in 17:37 2019/7/9
 * @Modified By:
 */
@Controller
public class LoginController extends BaseController {

    @RequestMapping("login")
    public String login(String email, String password){
        return "home/main";
    }

    @RequestMapping("logout")
    public String logou(){
        return "forward:login.jsp";
    }

    @RequestMapping("home")
    public String home(){
        return "home/home";
    }

}
