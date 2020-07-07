package com.peiqi.admin.controller.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peiqi.common.utils.RedisUtil;

/**
 * 测试Redis
 * 
 * @author STILL
 *
 */
@RestController
@RequestMapping("/_test_redis")
public class MyRidesController {

	/**
	 * 测试根据key获得value
	 * 
	 * @return
	 */
	@RequestMapping("/find_string")
	public String findString() {
		String name = RedisUtil.get("name");
		return name;
	}

}
