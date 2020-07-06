package com.peiqi.hibernate.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import com.peiqi.hibernate.dao.ReadAndWriteObjectDao;
import com.peiqi.hibernate.util.SqlPreUtil;

public abstract class AbstractReadAndWriteObjectDao extends AbstractReadObjectDao implements ReadAndWriteObjectDao {

	@Override
	public void mergeObject(Object object) {
		getCurrentSession().merge(object);
	}

	public void updateObject(Object object) {
		getCurrentSession().update(object);
	}

	public int updateByHql(String hql, List<Object> preParameters) {
		Query query = getCurrentSession().createQuery(hql);
		SqlPreUtil.setValForQuery(query, preParameters);
		return query.executeUpdate();
	}

	public int updateBySql(String sql, List<Object> preParameters) {
		NativeQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
		SqlPreUtil.setValForSqlQuery(sqlQuery, preParameters);
		return sqlQuery.executeUpdate();
	}

	public void addObject(Object object) {
		getCurrentSession().save(object);
	}

	public void delObject(Object object) {
		getCurrentSession().delete(object);
	}

	public void delObjectById(Class<?> objclass, Integer id) {
		Object object = getCurrentSession().get(objclass, id);
		getCurrentSession().delete(object);
	}

	public void updateObjectByMap(Object Object, Map<String, Object> updateMap) throws Exception {
		Class<?> classObject = Object.getClass();
		Set<Entry<String, Object>> paramEntrys = updateMap.entrySet();
		// System.out.println(classObject);
		// 属性
		Field field = null;
		// 属性类型
		String fieldType = null;
		// set方法名
		String setMetName = null;
		// set方法
		Method fieldSetMet = null;
		// 需要赋值的方法名
		String fieldName = null;
		for (Entry<String, Object> entry : paramEntrys) {
			fieldName = entry.getKey();
			if (!"id".equals(fieldName)) {// id不需要修改
				// 根据Map中参数获取属性
				field = classObject.getDeclaredField(fieldName);
				// 获取属性类型
				fieldType = field.getType().getSimpleName();
				// set方法必须为统一生成
				setMetName = new StringBuilder("set").append(StringUtils.capitalize(fieldName)).toString();
				// 获取属性set方法
				fieldSetMet = classObject.getMethod(setMetName, field.getType());
				// 根据属性类型执行属性set方法
				if ("int".equals(fieldType) || "Integer".equals(fieldType)) {
					fieldSetMet.invoke(Object, (Integer) entry.getValue());
				} else if ("short".equals(fieldType) || "Short".equals(fieldType)) {
					if (entry.getValue() instanceof Integer) {
						fieldSetMet.invoke(Object, ((Integer) entry.getValue()).shortValue());
					} else {
						fieldSetMet.invoke(Object, (Short) entry.getValue());
					}
				} else if ("byte".equals(fieldType) || "Byte".equals(fieldType)) {
					fieldSetMet.invoke(Object, (Byte) entry.getValue());
				} else if ("double".equals(fieldType) || "Double".equals(fieldType)) {
					fieldSetMet.invoke(Object, (Double) entry.getValue());
				} else if ("float".equals(fieldType) || "Float".equals(fieldType)) {
					fieldSetMet.invoke(Object, (Float) entry.getValue());
				} else if ("long".equals(fieldType) || "Long".equals(fieldType)) {
					fieldSetMet.invoke(Object, (Long) entry.getValue());
				} else if ("boolean".equals(fieldType) || "Boolean".equals(fieldType)) {
					fieldSetMet.invoke(Object, (Boolean) entry.getValue());
				} else if ("Timestamp".equals(fieldType)) {
					if (entry.getValue() instanceof Timestamp) {
						fieldSetMet.invoke(Object, (Timestamp) entry.getValue());
					} else {
						fieldSetMet.invoke(Object, new Timestamp((Long) entry.getValue()));
					}
				} else if ("Date".equals(fieldType)) {
					if (entry.getValue() instanceof Date) {
						fieldSetMet.invoke(Object, (Date) entry.getValue());
					} else {
						// 特殊格式处理 如：2020-03-23T01:08:55.266+0000
						if (entry.getValue() != null && !"".equals(entry.getValue())
								&& entry.getValue().toString().contains("T")) {
							Date datetime = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ")
									.parse(entry.getValue().toString());
							fieldSetMet.invoke(Object, datetime);
						} else {
							fieldSetMet.invoke(Object, new Date((Long) entry.getValue()));
						}
					}
				} else if ("String".equals(fieldType)) {
					fieldSetMet.invoke(Object, (String) entry.getValue());
				} else if ("BigDecimal".equals(fieldType)) {
					fieldSetMet.invoke(Object, new BigDecimal(entry.getValue().toString()));
				} else {
					System.out.println("请补充数据类型");
				}
			}
		}
	}

}
