package cn.xmo.web.controller.cargo;

import cn.xmo.domain.cargo.Factory;
import cn.xmo.domain.cargo.FactoryExample;
import cn.xmo.service.cargo.FactoryService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 10:28 2019/8/11
 * @Modified By:
 */
@Controller
@RequestMapping("/cargo/factory")
public class FactoryController extends BaseController {

    @Reference
    private FactoryService factoryService;

    @RequestMapping("/list.do")
    public String list(@RequestParam(defaultValue = "1")int page,
                       @RequestParam(defaultValue = "5")int size){
        FactoryExample factoryExample = new FactoryExample();
        PageInfo info = factoryService.findAll(page, size, factoryExample);
        request.setAttribute("page",info);
        return "/cargo/factory/factory-list";
    }
}
