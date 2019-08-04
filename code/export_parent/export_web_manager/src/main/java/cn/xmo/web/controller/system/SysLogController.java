package cn.xmo.web.controller.system;

import cn.xmo.service.system.SysLogService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 18:49 2019/7/9
 * @Modified By:
 */
@Controller
@RequestMapping("/system/log")
public class SysLogController extends BaseController {

	@Reference
	private SysLogService sysLogService;

	@RequestMapping("/list")
	public String list(@RequestParam(defaultValue = "1") int page,
	                   @RequestParam(defaultValue = "5") int size){
		PageInfo info = sysLogService.findAll(page, size, getLoginCompanyId());
		request.setAttribute("page",info);
		return "system/log/log-list";
	}
}
