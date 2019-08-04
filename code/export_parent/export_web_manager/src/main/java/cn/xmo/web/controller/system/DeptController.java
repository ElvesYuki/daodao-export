package cn.xmo.web.controller.system;

import cn.xmo.domain.system.Dept;
import cn.xmo.domain.system.DeptExample;
import cn.xmo.service.system.DeptService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 19:27 2019/7/9
 * @Modified By:
 */
@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {

    @Reference
    private DeptService deptService;

    /**
     * 展示页面，分页展示部门
     * @param page:分页查询参数
     * @param size:分页查询大小
     * @return String:返回的访问页面地址
     */
    @RequestMapping(value = "/list",name = "查询部门列表")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {
        DeptExample deptExample = new DeptExample();
        DeptExample.Criteria criteria = deptExample.createCriteria();
        criteria.andCompanyIdEqualTo(getLoginCompanyId());
        PageInfo info = deptService.findAll(page, size, deptExample);
        request.setAttribute("page",info);
        return "system/dept/dept-list";
    }

    /**
     * 进入保存页面
     *      * 查询所有的部门,构造页面的下拉框
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        //从当前登录用户获取companyId , 模拟companyId
        //调用service查询所有的部门
        List deptList = deptService.findAll(getLoginCompanyId());
        request.setAttribute("deptList",deptList);
        return "system/dept/dept-add";
    }

    /**
     * 进入到修改页面
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        //1.调用service根据id查询
        Dept dept = deptService.findById(id);
        //2.将对象存入request域
        request.setAttribute("dept",dept);
        //3.设置下拉框的数据
        //从当前登录用户获取companyId , 模拟companyId
        //调用service查询所有的部门
        List deptList = deptService.findAll(getLoginCompanyId());
        request.setAttribute("deptList",deptList);
        //4.跳转更新页面
        return "system/dept/dept-update";
    }

    /**
     * 新增或者保存部门
     *   1.参数 = dept对象
     *   2.判断依据: dept对象中是否包含id属性
     *   3.页面跳转: 列表动作
     */
    @RequestMapping("/edit")
    public String edit(Dept dept) {
        //从当前登录用户获取companyId , 模拟companyId
        //设置企业id
        dept.setCompanyId(getLoginCompanyId());
        dept.setCompanyName(getLoginCompanyName());
        if(StringUtils.isEmpty(dept.getDeptId())) {
            //保存
            deptService.save(dept);
        }else{
            //更新
            deptService.update(dept);
        }
        return "redirect:/system/dept/list.do";
    }

    /**
     *
     * @param id 根据id删除
     * @return 跳转页面
     */
    @RequestMapping("/delete")
    public String delete(String id) {
        //1.调用service
        deptService.delete(id);
        //2.重定向到列表
        return "redirect:/system/dept/list.do";
    }
}
