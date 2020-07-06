package com.peiqi.hibernate.dao;

import java.util.List;
import java.util.Map;

public interface ReadAndWriteObjectDao extends ReadObjectDao {

	/**
	 * 传参Object进行更新
	 * 
	 * @param object
	 */
	public void updateObject(Object object);

	/**
	 * 根据hql语句使用预编译进行更新操作
	 * 
	 * @param hql
	 * @param preParameters
	 * @return int 影响行数
	 */
	public int updateByHql(String hql, List<Object> preParameters);

	/**
	 * 根据sql语句使用预编译进行更新操作
	 * 
	 * @param sql
	 * @param preParameters
	 * @return int 影响行数
	 */
	public int updateBySql(String sql, List<Object> preParameters);

	/**
	 * 新增一个对象
	 * 
	 * @param object
	 */
	public void addObject(Object object);

	/**
	 * 删除一个对象
	 * 
	 * @param object
	 */
	public void delObject(Object object);

	/**
	 * 根据ID删除一个对象
	 * 
	 * @param objclass
	 * @param id
	 */
	public void delObjectById(Class<?> objclass, Integer id);

	/**
	 * 通过Map修改持久化对象（此处必须为持久化对象且为readAndWriteObjectDao获取的对象）来更新对象
	 * 
	 * @param Object
	 * @param updateMap
	 * @throws Exception
	 */
	public void updateObjectByMap(Object Object, Map<String, Object> updateMap) throws Exception;

	void mergeObject(Object object);

}
