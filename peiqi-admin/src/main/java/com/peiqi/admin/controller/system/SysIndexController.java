package com.peiqi.admin.controller.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.peiqi.common.core.controller.BaseController;
import com.peiqi.system.domain.SysMenu;
import com.peiqi.system.domain.SysUser;
import com.peiqi.system.service.ISysMenuService;

/**
 * 首页 业务处理
 * 
 * @author STILL
 */
@Controller
public class SysIndexController extends BaseController {
	@Autowired
	private ISysMenuService sysMenuService;

	/**
	 * 系统首页
	 * 
	 * @param modelMap
	 * @return
	 */
	@GetMapping("/index")
	public String index(ModelMap modelMap) {
		// 取身份信息
		SysUser user = new SysUser();
		user.setUserId(1L);
		// 根据用户id取出菜单
		List<SysMenu> menus = sysMenuService.selectMenusByUser(user);
		modelMap.put("menus", menus);
		modelMap.put("user", user);
		/*
		 * 获取系统配置的主题和皮肤
		 * 
		 * modelMap.put("sideTheme", "theme-dark"); modelMap.put("skinName",
		 * "skin-blue");
		 */
		return "index";
	}

	/**
	 * 切换主题
	 * 
	 * @param modelMap
	 * @return
	 */
	@GetMapping("/system/switchSkin")
	public String switchSkin(ModelMap modelMap) {
		return "skin";
	}

	/**
	 * 主页
	 * 
	 * @param modelMap
	 * @return
	 */
	@GetMapping("/system/main")
	public String main(ModelMap modelMap) {
		// mmap.put("version", Global.getVersion());
		return "main_v1";
	}
}
