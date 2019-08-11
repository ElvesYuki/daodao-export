package cn.xmo.web.controller.cargo;

import cn.xmo.domain.cargo.Export;
import cn.xmo.domain.cargo.ExportExample;
import cn.xmo.domain.cargo.Packing;
import cn.xmo.domain.cargo.PackingExample;
import cn.xmo.service.cargo.ExportService;
import cn.xmo.service.cargo.PackingService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
/**
 * @Author: Elves
 * @Description: 装箱模块消费者
 * @Date: Created in 11:51 2019/8/10
 * @Modified By:
 */
@Controller
@RequestMapping("/cargo/packing")
public class PackingController extends BaseController {

    @Reference
    private PackingService packingService;

    @Reference
    private ExportService exportService;


    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size
                        ){
        //1.分页查询所有装箱
        PackingExample example = new PackingExample();
        PackingExample.Criteria criteria = example.createCriteria();
        PageInfo info = packingService.findAll(example, page, size);
        request.setAttribute("page", info);
        return "cargo/packing/packing-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "5") int size) {
            //查询所有状态为1的报运单
        ExportExample example = new ExportExample();
        ExportExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(getLoginCompanyId());
        criteria.andStateEqualTo(2L);
        PageInfo info = exportService.findAll(example, page, size);
        request.setAttribute("page", info);
        return "cargo/packing/packing-add";

    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        Packing packing = packingService.findById(id);
        request.setAttribute("pack",packing);
        ExportExample example = new ExportExample();
        ExportExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(getLoginCompanyId());
        List<Export> list=exportService.findAll(example);
        String[] ids = packing.getExportIds().split(",");
        List<String> asList = Arrays.asList(ids);
        request.setAttribute("ids", asList);

        request.setAttribute("page", list);

        return "cargo/packing/packing-update";
    }

    @RequestMapping("/edit")
    public String edit(Packing packing){
        if (StringUtils.isEmpty(packing.getPackingListId())){
            packing.setCreateBy(getLoginUser().getUserName());
            packingService.save(packing);
        }else {
            packingService.update(packing);
        }
        return "redirect:/cargo/packing/list.do";
    }

    @RequestMapping("/delete")
    public String delete(String id){
       packingService.delete(id);
        return "redirect:/cargo/packing/list.do";
    }


    /**
     * 提交
     * @param id
     * @return
     */
    @RequestMapping("/submit")
    public String submit(String id){
        //1 查询
        Packing packing = packingService.findById(id);
        //2.判断状态、设置状态并修改
            packing.setState(1);
            packingService.update(packing);
        return "redirect:/cargo/packing/list.do";
    }

    /**
     * 取消
     * @param id
     * @return
     */
    @RequestMapping("/cancel")
    public String cancel(String id){
        Packing packing = packingService.findById(id);
        //设置状态并修改
        packing.setState(0);
        packingService.update(packing);

        return "redirect:/cargo/packing/list.do";
    }
}
