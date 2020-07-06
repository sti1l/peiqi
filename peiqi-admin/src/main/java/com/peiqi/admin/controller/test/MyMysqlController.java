package com.peiqi.admin.controller.test;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peiqi.hibernate.dao.ReadObjectDao;

/**
 * 测试mysql
 * 
 * @author STILL
 *
 */
@RestController
@RequestMapping("/_test_durid")
public class MyMysqlController {
	
	/**
	 * 从配置文件中读取配置name
	 */
	@Value("${name}")
	private String name;
	
	@RequestMapping("/testReadProperty")
	public String testReadProperty() {
		return name;
	}
	
	/**
	 * 数据源注入
	 */
	@Resource
	private ReadObjectDao readObjectDao;
	
	/**
	 * 测试hibernate持久层
	 */
	@RequestMapping("/testFindAllPerson")
	public void testFindAllPerson() {
		
		String sql = "select * from person";
		List<Map<String, Object>> personList = 
				readObjectDao.getMapOfObjectsBySql(sql);
		
		for (Map<String, Object> personMap : personList) {
			System.err.println(personMap);
		}
	}

}
