package com.peiqi.hibernate.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.peiqi.hibernate.bean.PageBean;

public interface ReadObjectDao {

	/**
	 * 根据hql语句查询集合列表
	 * 
	 * @param hql
	 * @return List<Object>
	 */
	public <T> List<T> getObjectsByHql(String hql);

	/**
	 * 根据hql语句查询集合列表,带分页
	 * 
	 * @param hql
	 * @param start         起始记录，从0开始, 如不需要分页则传参null
	 * @param size          最大抓取记录数量 ,如不需要分页则传参null
	 * @param preParameters 根据参数类型填充预编译数据, 如不需要预编译则传参null
	 * @return
	 */
	public <T> List<T> getObjectsByHql(String hql, Integer start, Integer size, List<Object> preParameters);

	/**
	 * 根据sql语句查询集合列表
	 * 
	 * @param sql
	 * @return List<Object>
	 */
	public <T> List<T> getObjectsBySql(String sql, Class<?> classObj);

	/**
	 * 根据sql语句查询集合列表,带分页
	 * 
	 * @param sql
	 * @param classObj
	 * @param start         起始记录，从0开始, 如不需要分页则传参null
	 * @param size          最大抓取记录数量, 如不需要分页则传参null
	 * @param preParameters 根据参数类型填充预编译数据, 如不需要预编译则传参null
	 * @return List<Object>
	 */
	public <T> List<T> getObjectsBySql(String sql, Class<?> classObj, Integer start, Integer size,
			List<Object> preParameters);

	/**
	 * 根据sql语句查询集合列表
	 * 
	 * @param sql
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getMapOfObjectsBySql(String sql);

	/**
	 * 根据sql语句查询集合列表
	 * 
	 * @param sql
	 * @param preParameters
	 * @return
	 */
	public List<Map<String, Object>> getMapOfObjectsBySql(String sql, List<Object> preParameters);

	/**
	 * 获取单个map集合
	 * 
	 * @param sql
	 * @param preParameters
	 * @return
	 */
	public Map<String, Object> getMapOfObjectBySql(String sql, List<Object> preParameters);

	/**
	 * 获取单个map集合
	 * 
	 * @param sql
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getMapOfObjectBySql(String sql);

	/**
	 * 根据sql语句使用预编译查询集合列表,带分页
	 * 
	 * @param sql
	 * @param start         起始记录，从0开始, 如不需要分页则传参null
	 * @param size          最大抓取记录数量, 如不需要分页则传参null
	 * @param preParameters 根据参数类型填充预编译数据, 如不需要预编译则传参null
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getMapOfObjectsBySql(String sql, Integer start, Integer size,
			List<Object> preParameters);

	/**
	 * 根据hql语句查询唯一对象
	 * 
	 * @param hql
	 * @return Object
	 */
	public Object getObjectByHql(String hql);

	/**
	 * 根据sql语句查询唯一对象
	 * 
	 * @param sql
	 * @return Object
	 */
	public <T> T getObjectBySql(String sql, Class<T> classObj);

	/**
	 * 根据ID查询唯一对象
	 * 
	 * @param objclass
	 * @param id
	 * @return
	 */
	public <T> T getObjectById(Class<T> objclass, Integer id);

	/**
	 * 根据sql语句使用预编译查询唯一对象
	 * 
	 * @param sql
	 * @param preParameters 根据参数类型填充预编译数据, 如不需要预编译则传参null
	 * @return Object
	 */
	public <T> T getObjectBySql(String sql, Class<T> classObj, List<Object> preParameters);

	/**
	 * 根据hql语句统计数量
	 * 
	 * @param hql
	 * @param preParameters 根据参数类型填充预编译数据, 如不需要预编译则传参null
	 * @return int
	 */
	public int countNumByHql(String hql, List<Object> preParameters);

	/**
	 * 根据sql语句统计数量
	 * 
	 * @param sql
	 * @param preParameters 根据参数类型填充预编译数据, 如不需要预编译则传参null
	 * @return int
	 */
	public int countNumBySql(String sql, List<Object> preParameters);

	/**
	 * 根据hql语句查询字符串集合
	 * 
	 * @param hql
	 * @return List<String>
	 */
	public List<String> getStringListByHql(String hql);

	/**
	 * 根据hql语句查询字符串集合,带分页
	 * 
	 * @param hql
	 * @param start 起始记录，从0开始
	 * @param size  最大抓取记录数量
	 * @return List<String>
	 */
	public List<String> getStringListByHql(String hql, Integer start, Integer size);

	/**
	 * 根据sql语句查询字符串集合
	 * 
	 * @param sql
	 * @return List<String>
	 */
	public List<String> getStringListBySql(String sql);

	/**
	 * 根据sql语句查询字符串集合,带分页
	 * 
	 * @param sql
	 * @param start 起始记录，从0开始
	 * @param size  最大抓取记录数量
	 * @return List<String>
	 */
	public List<String> getStringListBySql(String sql, Integer start, Integer size);

	/**
	 * 根据hql语句查询Object数组集合
	 * 
	 * @param hql
	 * @return List<Object[]>
	 */
	public List<Object[]> getObjectArrayListByHql(String hql);

	/**
	 * 根据hql语句查询Object数组集合,带分页
	 * 
	 * @param hql
	 * @param start         起始记录，从0开始, 如不需要分页则传参null
	 * @param size          最大抓取记录数量 ,如不需要分页则传参null
	 * @param preParameters 根据参数类型填充预编译数据, 如不需要预编译则传参null
	 * @return List<Object[]>
	 */
	public List<Object[]> getObjectArrayListByHql(String hql, Integer start, Integer size, List<Object> preParameters);

	/**
	 * 获取连接，以调用存储过程
	 * 
	 * @return
	 */
	public Connection getConn();

	/**
	 * 根据分页组件查询实体数据列表
	 * 
	 * @param query     查询结果所需的字段select 条件
	 * @param condition from和where条件
	 * @param sort      排序的字段
	 * @param pagebean  分页bean
	 * @return
	 */
	public <T> List<T> getPageBeanBySql(String query, String condition, String sort, Class<?> classObj,
			PageBean pagebean);

	/**
	 * 根据分页组件查询实体数据列表
	 * 
	 * @param query     查询结果所需的字段select 条件
	 * @param condition from和where条件
	 * @param sort      排序的字段
	 * @param classObj  返回实体类型
	 * @param pagebean  分页bean
	 * @param params    参数
	 * @return
	 */
	public <T> List<T> getPageBeanBySql(String query, String condition, String sort, Class<?> classObj,
			PageBean pagebean, List<Object> params);

	/**
	 * 根据分页组件查询数据map列表
	 * 
	 * @param query     查询结果所需的字段select 条件
	 * @param condition from和where条件
	 * @param sort      排序的字段
	 * @param pagebean  分页bean
	 * @return
	 */
	public List<Map<String, Object>> getPageBeanBySql(String query, String condition, String sort, PageBean pagebean);

	/**
	 * 根据分页组件查询数据map列表
	 * 
	 * @param query     查询结果所需的字段select 条件
	 * @param condition from和where条件
	 * @param sort      排序的字段
	 * @param pagebean  分页bean
	 * @param params    参数
	 * @return
	 */
	public List<Map<String, Object>> getPageBeanBySql(String query, String condition, String sort, PageBean pagebean,
			List<Object> params);

	/**
	 * 获取当前的session用于批量操作
	 * 
	 * @return
	 */
	public Session getCurrentSession();

}
