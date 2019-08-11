package cn.xmo.web.controller.cargo;

import cn.xmo.domain.cargo.Finance;
import cn.xmo.domain.cargo.FinanceExample;
import cn.xmo.domain.cargo.InvoiceExample;
import cn.xmo.service.cargo.FinanceService;
import cn.xmo.service.cargo.InvoiceService;
import cn.xmo.service.system.DeptService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 8:53 2019/8/11
 * @Modified By:
 */
@Controller
@RequestMapping("/cargo/finance")
public class FinanceController extends BaseController {
    @Reference
    private FinanceService financeService;
    @Reference
    private DeptService deptService;
    @Reference
    private InvoiceService invoiceService;

    @RequestMapping("/list")
    public String list ( @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "5") int size ) {
        FinanceExample financeExample = new FinanceExample();
        FinanceExample.Criteria criteria = financeExample.createCriteria();

        PageInfo info = financeService.findAll( page, size, financeExample );
        request.setAttribute( "page", info );
        return "cargo/finance/finance-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd () {
        List all = deptService.findAll(getLoginCompanyId());
        request.setAttribute( "deptList", all );
        InvoiceExample invoiceExample = new InvoiceExample();
        invoiceExample.createCriteria().andStateEqualTo( 1 );
        PageInfo all1 = invoiceService.findAll(1, 5, invoiceExample);
        request.setAttribute( "page",all1 );
        return "cargo/finance/finance-add";
    }

    /**
     * 进入修改页面
     * 参数：报运单id
     * 业务：
     * 1.根据id查询报运单
     * 2.根据报运单id查询所有的商品
     */
    @RequestMapping("/toUpdate")
    public String toUpdate ( String id ) {
        Finance byId = financeService.findById( id );
        request.setAttribute( "finance", byId );
        List all = deptService.findAll( getLoginCompanyId() );
        request.setAttribute( "deptList", all );
        InvoiceExample invoiceExample = new InvoiceExample();
        invoiceExample.createCriteria().andStateEqualTo( 1 );
        PageInfo all1 = invoiceService.findAll(1, 5, invoiceExample);
        request.setAttribute( "page",all1 );
        return "cargo/finance/finance-update";
    }

    /**
     * 保存或者更新报运单
     */
    @RequestMapping("/update")
    public String update ( Finance finance ) {
        financeService.update( finance );

        return "redirect:/cargo/finance/list.do";
    }

    @RequestMapping("/delete")
    public String delete ( String id ) {
        financeService.delete( id );
        return "redirect:/cargo/finance/list.do";
    }

    @RequestMapping("/save")
    public String save ( Finance finance ) throws ParseException {
        finance.setInputDate( new Date() );
        finance.setCreateBy(getLoginUser().getUserName());
        finance.setCreateDept(getLoginUser().getDeptName());
        financeService.save( finance );
        return "redirect:/cargo/finance/list.do";
    }
}
