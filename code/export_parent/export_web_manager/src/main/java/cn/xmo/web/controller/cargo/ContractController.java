package cn.xmo.web.controller.cargo;

import cn.xmo.domain.cargo.Contract;
import cn.xmo.domain.cargo.ContractExample;
import cn.xmo.domain.system.User;
import cn.xmo.service.cargo.ContractService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Elves
 * @Description: 购销合同消费者
 * @Date: Created in 14:07 2019/8/5
 * @Modified By:
 */
@Controller
@RequestMapping("/cargo/contract")
public class ContractController extends BaseController {

    @Reference
    private ContractService contractService;

    /**
     * 分页查询购销合同列表
     * @param page 分页参数
     * @param size 分页参数
     * @return 跳转页面
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1")int page,
                       @RequestParam(defaultValue = "5")int size) {
        ContractExample example = new ContractExample();
        //查询条件
        //创建criteria
        ContractExample.Criteria criteria = example.createCriteria();
        //指定条件
        criteria.andCompanyIdEqualTo(getLoginCompanyId());

        /*
         * 细粒度权限控制
         *      1.获取当前用户的级别
         *      2.根据不用用户的级别添加不同的查询条件
         */
        User user = getLoginUser();
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
        //1-企业管理员

        //根据create_time desc
        example.setOrderByClause("create_time desc");
        PageInfo info = contractService.findAll(page, size,example);
        request.setAttribute("page",info);
        return "/cargo/contract/contract-list";
    }

    @RequestMapping(value="/toAdd")
    public String toAdd() {
        return "/cargo/contract/contract-add";
    }

    @RequestMapping(value="/toUpdate")
    public String toUpdate(String id) {
        Contract contract = contractService.findById(id);
        request.setAttribute("contract",contract);
        return "/cargo/contract/contract-update";
    }

    @RequestMapping(value="/edit")
    public String edit(Contract contract) {
        contract.setCompanyId(getLoginCompanyId());
        contract.setCompanyName(getLoginCompanyName());
        if(StringUtils.isEmpty(contract.getId())) {
            //保存购销合同 （设置创建人和创建人部门）
            //1.获取当前登录用户
            User loginUser = getLoginUser();
            //2.设置创建人和部门
            contract.setCreateBy(loginUser.getUserId());
            contract.setCreateDept(loginUser.getDeptId());
            contractService.save(contract);
        }else{
            contractService.update(contract);
        }
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 提交
     * @param id
     * @return
     */
    @RequestMapping(value="/submit")
    public String submit(String id) {
        //将合同状态改为1
        Contract contract = new Contract();
        contract.setId(id);
        contract.setState(1);
        contractService.update(contract);
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 取消
     * @param id
     * @return
     */
    @RequestMapping(value="/cancel")
    public String cancel(String id) {
        //将合同状态改为0
        Contract contract = new Contract();
        contract.setId(id);
        contract.setState(0);
        contractService.update(contract);
        return "redirect:/cargo/contract/list.do";
    }

    @RequestMapping(value="/delete")
    public String delete(String id) {
        contractService.delete(id);
        return "redirect:/cargo/contract/list.do";
    }
}
