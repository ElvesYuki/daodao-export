package cn.xmo.web.controller.cargo;

import cn.xmo.domain.cargo.Contract;
import cn.xmo.domain.cargo.ExtCproduct;
import cn.xmo.domain.cargo.ExtCproductExample;
import cn.xmo.domain.cargo.FactoryExample;
import cn.xmo.service.cargo.ExtCproductService;
import cn.xmo.service.cargo.FactoryService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 15:41 2019/8/5
 * @Modified By:
 */
@Controller
@RequestMapping("/cargo/extCproduct")
public class ExtCproductController extends BaseController {

    @Reference
    private ExtCproductService extCproductService;

    @Reference
    private FactoryService factoryService;

    @RequestMapping("/list")
    public String list(String  contractId,String contractProductId,
                       @RequestParam(defaultValue = "1")int page,
                       @RequestParam(defaultValue = "5")int size) {
        //1.查询附件列表
        ExtCproductExample example = new ExtCproductExample();
        ExtCproductExample.Criteria criteria = example.createCriteria();
        criteria.andContractProductIdEqualTo(contractProductId);
        PageInfo info = extCproductService.findAll(page, size, example);
        request.setAttribute("page",info);

        //2.查询所有附件的生产厂家
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria1 = factoryExample.createCriteria();
        criteria1.andCtypeEqualTo("附件");
        List factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);

        //3.页面参数
        request.setAttribute("contractId",contractId);
        request.setAttribute("contractProductId",contractProductId);
        return "/cargo/extc/extc-list";
    }

    @RequestMapping(value="/toUpdate")
    public String toUpdate(String id,String contractId,String contractProductId) {
        //附件对象
        ExtCproduct extCproduct = extCproductService.findById(id);
        request.setAttribute("extCproduct",extCproduct);
        //页面参数
        request.setAttribute("contractId",contractId);
        request.setAttribute("contractProductId",contractProductId);
        //厂家
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria1 = factoryExample.createCriteria();
        criteria1.andCtypeEqualTo("附件");
        List factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);
        return "/cargo/extc/extc-update";
    }

    @RequestMapping("/edit")
    public String edit(ExtCproduct extCproduct) {
        extCproduct.setCompanyId(getLoginCompanyId());
        extCproduct.setCompanyName(getLoginCompanyName());
        if(StringUtils.isEmpty(extCproduct.getId())) {
            //保存
            extCproductService.save(extCproduct);
        }else{
            //更新
            extCproductService.update(extCproduct);
        }
        return "redirect:/cargo/extCproduct/list.do?" +
                "contractId="+extCproduct.getContractId()+
                "&contractProductId="+extCproduct.getContractProductId();
    }

    @RequestMapping(value="/delete")
    public String delete(String id,String contractId,String contractProductId) {
        extCproductService.delete(id);
        return "redirect:/cargo/extCproduct/list.do?contractId="+contractId+"&contractProductId="+contractProductId;
    }


}
