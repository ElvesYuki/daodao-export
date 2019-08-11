package cn.xmo.web.controller.cargo;

import cn.xmo.domain.cargo.Packing;
import cn.xmo.domain.cargo.PackingExample;
import cn.xmo.domain.cargo.Shipping;
import cn.xmo.domain.cargo.ShippingExample;
import cn.xmo.service.cargo.PackingService;
import cn.xmo.service.cargo.ShippingService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 15:54 2019/8/10
 * @Modified By:
 */
@Controller
@RequestMapping("/cargo/shipping")
public class ShippingController extends BaseController {

    @Reference
    private PackingService packingService;

    @Reference
    private ShippingService shippingService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size
    ){
        //1.分页查询所有装箱
        ShippingExample example = new ShippingExample();
        ShippingExample.Criteria criteria = example.createCriteria();
        PageInfo info = shippingService.findAll(example,page, size);
        request.setAttribute("page", info);
        return "cargo/shipping/shipping-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd(@RequestParam(defaultValue = "1")int page,
                        @RequestParam(defaultValue = "5")int size){
        PackingExample example = new PackingExample();
        //添加查询对象
        PackingExample.Criteria criteria = example.createCriteria();
        //添加查询条件  根据装箱单状态为1的查询
        criteria.andStateEqualTo( 1);
        //查询所有装箱单
        PageInfo info = packingService.findAll(example, page, size );
        request.setAttribute( "pack",info);
        return "cargo/shipping/shipping-add";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        //根据id查询委托单
        Shipping shipping = shippingService.findById( id );
        //回显数据
        request.setAttribute( "shipping" ,shipping);
        return "cargo/shipping/shipping-update";
    }

    @RequestMapping("/edit")
    public String edit( Shipping shipping ){
            shippingService.save(shipping);
        return "redirect:/cargo/shipping/list.do";
    }

    @RequestMapping("/update")
    public String update( Shipping shipping ){
        shippingService.update(shipping);
        return "redirect:/cargo/shipping/list.do";
    }

    @RequestMapping("/delete")
    public String delete( String id ){
        shippingService.delete(id);
        return "redirect:/cargo/shipping/list.do";
    }

    @RequestMapping("/submit")
    public String submit(String id){
        //查询当前委托单状态
        Shipping shipping = shippingService.findById( id );
        //根据状态进行修改
        if ( shipping.getState() == 0 ){
            //修改状态
            shipping.setState(1);
            shippingService.update( shipping );
            Packing pack  = packingService.findById(id);
            pack.setState(5);
            packingService.update(pack);
        }
        return "redirect:/cargo/shipping/list.do";
    }

    @RequestMapping("/cancel")
    public String cancel(String id){
        //查询当前委托单状态
        Shipping shipping = shippingService.findById( id );
        //根据状态进行修改
        if ( shipping.getState() == 1 ){
            //修改状态
            shipping.setState(0);
            shippingService.update( shipping );
            Packing pack  = packingService.findById(id);
            pack.setState(4);
            packingService.update(pack);
        }
        return "redirect:/cargo/shipping/list.do";
    }
}
