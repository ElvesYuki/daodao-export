package cn.xmo.web.controller.cargo;

import cn.xmo.domain.cargo.*;
import cn.xmo.domain.system.User;
import cn.xmo.service.cargo.ContractService;
import cn.xmo.service.cargo.ExportProductService;
import cn.xmo.service.cargo.ExportService;
import cn.xmo.vo.ExportProductVo;
import cn.xmo.vo.ExportResult;
import cn.xmo.vo.ExportVo;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 20:35 2019/8/5
 * @Modified By:
 */
@Controller
@RequestMapping("/cargo/export")
public class ExportController extends BaseController {

    @Reference
    private ContractService contractService;
    @Reference
    private ExportService exportService;
    @Reference
    private ExportProductService exportProductService;

    /**
     * 合同管理:查询所有已经上报的所有购销合同
     */
    @RequestMapping("/contractList")
    public String contractList(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "5") int size){

        //1.创建example
        ContractExample example = new ContractExample();
        //2.example创建criteria
        ContractExample.Criteria criteria = example.createCriteria();
        //3.添加条件
        criteria.andCompanyIdEqualTo(getLoginCompanyId());
        //所有状态=1的购销合同
        criteria.andStateEqualTo(1);

        /*
         * 细粒度权限控制
         *      1.获取当前用户的级别
         *      2.根据不用用户的级别添加不同的查询条件
         */
 /*       User user = getLoginUser();
        Integer degree = user.getDegree();

        if(degree == 4) {
            //4-普通员工
            criteria.andCreateByEqualTo(user.getUserId());
        }else if(degree ==3) {
            //管理本部门 (经理：部门id)
            criteria.andCreateDeptEqualTo(user.getDeptId());
        }else if(degree ==2 ) {
            //2-管理所有下属部门和人员
            criteria.andCreateDeptLike(user.getDeptId()+"%");
        }
        //1-企业管理员*/

        PageInfo pageInfo = contractService.findAll(page, size, example);
        request.setAttribute("page",pageInfo);
        return "cargo/export/export-contractList";
    }

    /**
     * 报运单的列表显示
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size){
        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria criteria = exportExample.createCriteria();
        criteria.andCompanyIdEqualTo(getLoginCompanyId());
        PageInfo info = exportService.findAll( exportExample,page, size);
        request.setAttribute("page",info);
        return "cargo/export/export-list";
    }

    /**
     * 进入到报运页面
     *      参数 ： 同名参数id
     *          1.已数组形式接受 String [] id
     *          2.已字符串的形式接口  String id
     *              * 多个id拼接一个字符串 ，多个id之间以“，”隔开
     */
    @RequestMapping("/toExport")
    public String toExport(String id) {
        request.setAttribute("id",id);
        return "cargo/export/export-toExport";
    }

    /**
     * 进入修改页面
     *      参数：报运单id
     *      业务：
     *          1.根据id查询报运单
     *          2.根据报运单id查询所有的商品
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        //1.根据id查询报运单
        Export export = exportService.findById(id);
        request.setAttribute("export",export);
        //2.根据报运单id查询所有的商品
        ExportProductExample example = new ExportProductExample();
        ExportProductExample.Criteria criteria = example.createCriteria();
        criteria.andExportIdEqualTo(id);
        List<ExportProduct> eps = exportProductService.findAll(example);
        request.setAttribute("eps",eps);
        return "cargo/export/export-update";
    }

    /**
     * 保存或者更新报运单
     */
    @RequestMapping("/edit")
    public String edit(Export export) {
        export.setCompanyName(getLoginCompanyName());
        export.setCompanyId(getLoginCompanyId());
        if(StringUtils.isEmpty(export.getId())) {
            exportService.save(export);
        }else{
            exportService.update(export);
        }
        return "redirect:/cargo/export/list.do";
    }

    /**
     * 电子报运
     *  参数 : 报运单id
     */
    @RequestMapping("/exportE")
    public String exportE(String id) {
        //1.根据id查询报运单
        Export export = exportService.findById(id);
        //2.构造电子报运的ExportVo
        ExportVo evo = new ExportVo();
        BeanUtils.copyProperties(export,evo);
        evo.setExportId(export.getId());
        //3.根据报运单id查询商品
        ExportProductExample example = new ExportProductExample();
        ExportProductExample.Criteria criteria = example.createCriteria();
        criteria.andExportIdEqualTo(id);
        List<ExportProduct> eps = exportProductService.findAll(example);
        //4.构造电子报运的ExportProductVo
        List<ExportProductVo> epvos = new ArrayList<>();
        for (ExportProduct ep : eps) {
            ExportProductVo epvo = new ExportProductVo();
            BeanUtils.copyProperties(ep,epvo);
            epvo.setExportProductId(ep.getId());
            epvo.setEid(id);
            epvos.add(epvo);
        }
        evo.setProducts(epvos);
        //5.webservice调用海关平台实现电子报运    http://localhost:8082/ws/export/user  post
        WebClient client = WebClient.create("http://39.105.45.130:8610/jk_export/ws/export/user");
        client.post(evo);
        //6.查询电子报运结果 调用webservice
        client = WebClient.create("http://39.105.45.130:8610/jk_export/ws/export/user/"+id);
        ExportResult exportResult = client.get(ExportResult.class);
        //7.更新报运单和报运单商品
        exportService.updateE(exportResult);
        return "redirect:/cargo/export/list.do";
    }

}
