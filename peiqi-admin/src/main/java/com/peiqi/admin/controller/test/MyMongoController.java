package com.peiqi.admin.controller.test;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peiqi.entity.mongo.ErrorLog;
import com.peiqi.util.MongoUtil;

/**
 * 测试MongoDB
 * 
 * @author STILL
 *
 */
@RestController
@RequestMapping("/_test_mongo")
public class MyMongoController {
	
	/**
	 * 查询所有的错误日志
	 */
	@RequestMapping("/find_all_error_log")
	public void findAllErrorLog() {
		@SuppressWarnings("unchecked")
		List<ErrorLog> personList = (List<ErrorLog>) MongoUtil.findAll(new ErrorLog());
		for (ErrorLog error_log : personList) {
			System.err.println(error_log);
		}
		
	}
}
