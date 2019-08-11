package cn.xmo.web.controller.cargo;

import cn.xmo.domain.cargo.Invoice;
import cn.xmo.domain.cargo.InvoiceExample;
import cn.xmo.domain.cargo.ShippingExample;
import cn.xmo.domain.system.User;
import cn.xmo.service.cargo.InvoiceService;
import cn.xmo.service.cargo.PackingService;
import cn.xmo.service.cargo.ShippingService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cargo/invoice")
public class InvoiceController extends BaseController {

    @Reference
    private InvoiceService invoiceService;

    @Reference
    private ShippingService shippingService;

    @Reference
    private PackingService packingService;


    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size){

        InvoiceExample invoiceExample = new InvoiceExample();
        InvoiceExample.Criteria criteria = invoiceExample.createCriteria();
        criteria.andCompanyIdEqualTo(getLoginCompanyId());
        criteria.andCompanyNameEqualTo(getLoginCompanyName());

        PageInfo info = invoiceService.findAll(page, size, invoiceExample);
        request.setAttribute("page",info);

        return "cargo/invoice/invoice-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "5") int size){
        ShippingExample shippingExample = new ShippingExample();
        ShippingExample.Criteria criteria = shippingExample.createCriteria();
        criteria.andStateEqualTo(1);
        PageInfo info = shippingService.findAll(shippingExample, page, size);
        request.setAttribute("ship",info);
        return "cargo/invoice/invoice-add";
    }

    @RequestMapping("/toUpdate")
    public String update(String id){
        Invoice invoice = invoiceService.findById(id);
        request.setAttribute("invoice",invoice);
        return "cargo/invoice/invoice-update";
    }

    @RequestMapping("/edit")
    public String edit(Invoice invoice){
        invoice.setCompanyId(getLoginCompanyId());
            User loginUser = getLoginUser();
            invoice.setCreateBy(loginUser.getUserName());
            invoice.setCreateDept(loginUser.getDeptId());
            invoiceService.save(invoice);
        return "redirect:/cargo/invoice/list.do";
    }

    @RequestMapping("/update")
    public String update(Invoice invoice){
        invoiceService.update(invoice);
        return "redirect:/cargo/invoice/list.do";
    }

    @RequestMapping("/delete")
    public String delete(String id){
        invoiceService.delete(id);
        return "redirect:/cargo/invoice/list.do";
    }

    @RequestMapping("/submit")
    public String submit(String id){
        Invoice invoice = invoiceService.findById(id);
        if (invoice.getState() != 1){
            invoice.setState(1);
            invoiceService.update(invoice);
        }
        return "redirect:/cargo/invoice/list.do";
    }

    @RequestMapping("/cancel")
    public String cancel(String id){
        Invoice invoice = invoiceService.findById(id);
        invoice.setState(2);
        invoiceService.update(invoice);
        return "redirect:/cargo/invoice/list.do";
    }


}
