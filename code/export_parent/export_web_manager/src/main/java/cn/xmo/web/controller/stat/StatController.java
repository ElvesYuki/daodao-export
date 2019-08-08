package cn.xmo.web.controller.stat;

import cn.xmo.service.stat.StatService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 11:27 2019/8/8
 * @Modified By:
 */
@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {

	@Reference
	private StatService statService;

	@RequestMapping("/toCharts")
	public String toCharts(String chartsType) {

		return "stat/stat-"+chartsType;
	}

	/**
	 * 获取生产厂家销售统计数据
	 * @return
	 */
	@RequestMapping("/getFactoryData")
	public @ResponseBody List getFactoryData() {

		return statService.getFactoryData(getLoginCompanyId());
	}


	/**
	 * 根据货号统计销量的前15名
	 * @return
	 */
	@RequestMapping("/getSellData")
	public @ResponseBody List getSellData() {

		return statService.getSellData(getLoginCompanyId());
	}

	/**
	 * 系统访问压力图
	 * @return
	 */
	@RequestMapping("/getOnlineData")
	public @ResponseBody List getOnLineData() {

		return statService.getOnLineData(getLoginCompanyId());
	}


	/**
	 * 登录ip次数的前十名
	 * @return
	 */
	@RequestMapping("/getIpTopTenData")
	public @ResponseBody List getIpTopTenData() {

		return statService.getIpTopTenData(getLoginCompanyId());
	}


}
