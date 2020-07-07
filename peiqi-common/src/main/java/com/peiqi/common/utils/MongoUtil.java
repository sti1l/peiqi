package com.peiqi.common.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * 
 * @author STILL
 *
 */
@Component
public class MongoUtil {
	public static MongoUtil mongodbUtils;

	@PostConstruct
	public void init() {
		mongodbUtils = this;
		mongodbUtils.mongoTemplate = this.mongoTemplate;
	}

	@Resource
	private MongoTemplate mongoTemplate;

	public static MongoTemplate getMongoTemplate() {
		return mongodbUtils.mongoTemplate;
	}

	/**
	 * 保存数据对象，集合为数据对象中
	 *
	 * @param obj 数据对象
	 */
	public static void save(Object obj) {
		mongodbUtils.mongoTemplate.save(obj);
	}

	/**
	 * 指定集合保存数据对象
	 *
	 * @param obj            数据对象
	 * @param collectionName 集合名
	 */
	public static void save(Object obj, String collectionName) {
		mongodbUtils.mongoTemplate.save(obj, collectionName);
	}

	/**
	 * 根据数据对象中的id删除数据，集合为数据对象中
	 *
	 * @param obj 数据对象
	 */
	public static void remove(Object obj) {
		mongodbUtils.mongoTemplate.remove(obj);
	}

	/**
	 * 指定集合 根据数据对象中的id删除数据
	 *
	 * @param obj            数据对象
	 * @param collectionName 集合名
	 */
	public static void remove(Object obj, String collectionName) {
		mongodbUtils.mongoTemplate.remove(obj, collectionName);
	}

	/**
	 * 根据key，value到指定集合删除数据
	 *
	 * @param key            键
	 * @param value          值
	 * @param collectionName 集合名
	 */
	public static void removeByKey(String key, Object value, String collectionName) {
		Criteria criteria = Criteria.where(key).is(value);
		// criteria.and(key).is(value);
		Query query = Query.query(criteria);
		mongodbUtils.mongoTemplate.remove(query, collectionName);
	}

	/**
	 * 指定集合 修改数据，且仅修改找到的第一条数据
	 *
	 * @param accordingKey   修改条件 key
	 * @param accordingValue 修改条件 value
	 * @param updateKeys     修改内容 key数组
	 * @param updateValues   修改内容 value数组
	 * @param collectionName 集合名
	 */
	public static void updateFirst(String accordingKey, Object accordingValue, String[] updateKeys,
			Object[] updateValues, String collectionName) {
		Criteria criteria = Criteria.where(accordingKey).is(accordingValue);
		Query query = Query.query(criteria);
		Update update = new Update();
		for (int i = 0; i < updateKeys.length; i++) {
			update.set(updateKeys[i], updateValues[i]);
		}
		mongodbUtils.mongoTemplate.updateFirst(query, update, collectionName);
	}

	/**
	 * 指定集合 修改数据，且修改所找到的所有数据
	 *
	 * @param accordingKey   修改条件 key
	 * @param accordingValue 修改条件 value
	 * @param updateKeys     修改内容 key数组
	 * @param updateValues   修改内容 value数组
	 * @param collectionName 集合名
	 */
	public static void updateMulti(String accordingKey, Object accordingValue, String[] updateKeys,
			Object[] updateValues, String collectionName) {
		Criteria criteria = Criteria.where(accordingKey).is(accordingValue);
		Query query = Query.query(criteria);
		Update update = new Update();
		for (int i = 0; i < updateKeys.length; i++) {
			update.set(updateKeys[i], updateValues[i]);
		}
		mongodbUtils.mongoTemplate.updateMulti(query, update, collectionName);
	}

	/**
	 * 指定集合 修改数据，且修改所找到的所有数据
	 * 
	 * @param accordingKeys   修改条件 key数组
	 * @param accordingValues 修改条件 value数组
	 * @param updateKeys      修改内容 key数组
	 * @param updateValues    修改内容 value数组
	 * @param collectionName  集合名
	 */
	public static void updateMulti(String[] accordingKeys, Object[] accordingValues, String[] updateKeys,
			Object[] updateValues, String collectionName) {
		Criteria criteria = null;
		for (int i = 0; i < accordingKeys.length; i++) {
			if (i == 0) {
				criteria = Criteria.where(accordingKeys[i]).is(accordingValues[i]);
			} else {
				criteria.and(accordingKeys[i]).is(accordingValues[i]);
			}
		}
		Query query = Query.query(criteria);
		Update update = new Update();
		for (int i = 0; i < updateKeys.length; i++) {
			update.set(updateKeys[i], updateValues[i]);
		}
		mongodbUtils.mongoTemplate.updateMulti(query, update, collectionName);
	}

	/**
	 * 根据条件查询出所有结果集 集合为数据对象中
	 *
	 * @param obj        数据对象
	 * @param findKeys   查询条件 key
	 * @param findValues 查询条件 value
	 * @return
	 */
	public static List<? extends Object> find(Object obj, String[] findKeys, Object[] findValues) {
		Criteria criteria = null;
		for (int i = 0; i < findKeys.length; i++) {
			if (i == 0) {
				criteria = Criteria.where(findKeys[i]).is(findValues[i]);
			} else {
				criteria.and(findKeys[i]).is(findValues[i]);
			}
		}
		Query query = Query.query(criteria);
		List<? extends Object> resultList = mongodbUtils.mongoTemplate.find(query, obj.getClass());
		return resultList;
	}

	/**
	 * 指定集合 根据条件查询出所有结果集
	 *
	 * @param obj            数据对象
	 * @param findKeys       查询条件 key
	 * @param findValues     查询条件 value
	 * @param collectionName 集合名
	 * @return
	 */
	public static List<? extends Object> find(Object obj, String[] findKeys, Object[] findValues,
			String collectionName) {
		Criteria criteria = null;
		for (int i = 0; i < findKeys.length; i++) {
			if (i == 0) {
				criteria = Criteria.where(findKeys[i]).is(findValues[i]);
			} else {
				criteria.and(findKeys[i]).is(findValues[i]);
			}
		}
		Query query = Query.query(criteria);
		List<? extends Object> resultList = mongodbUtils.mongoTemplate.find(query, obj.getClass(), collectionName);
		return resultList;
	}

	/**
	 * 指定集合 根据条件查询出所有结果集 并排倒序
	 *
	 * @param obj            数据对象
	 * @param findKeys       查询条件 key
	 * @param findValues     查询条件 value
	 * @param collectionName 集合名
	 * @param sort           排序字段
	 * @return
	 */
	public static List<? extends Object> find(Object obj, String[] findKeys, Object[] findValues, String collectionName,
			String sort) {
		Criteria criteria = null;
		for (int i = 0; i < findKeys.length; i++) {
			if (i == 0) {
				criteria = Criteria.where(findKeys[i]).is(findValues[i]);
			} else {
				criteria.and(findKeys[i]).is(findValues[i]);
			}
		}
		Query query = Query.query(criteria);
		// query.with(new Sort(Sort.Direction.DESC, sort));
		List<? extends Object> resultList = mongodbUtils.mongoTemplate.find(query, obj.getClass(), collectionName);
		return resultList;
	}

	/**
	 * 根据条件查询出符合的第一条数据 集合为数据对象中
	 *
	 * @param obj        数据对象
	 * @param findKeys   查询条件 key
	 * @param findValues 查询条件 value
	 * @return
	 */
	public static Object findOne(Object obj, String[] findKeys, Object[] findValues) {
		Criteria criteria = null;
		for (int i = 0; i < findKeys.length; i++) {
			if (i == 0) {
				criteria = Criteria.where(findKeys[i]).is(findValues[i]);
			} else {
				criteria.and(findKeys[i]).is(findValues[i]);
			}
		}
		Query query = Query.query(criteria);
		Object resultObj = mongodbUtils.mongoTemplate.findOne(query, obj.getClass());
		return resultObj;
	}

	/**
	 * 指定集合 根据条件查询出符合的第一条数据
	 *
	 * @param obj            数据对象
	 * @param findKeys       查询条件 key
	 * @param findValues     查询条件 value
	 * @param collectionName 集合名
	 * @return
	 */
	public static Object findOne(Object obj, String[] findKeys, Object[] findValues, String collectionName) {
		Criteria criteria = null;
		for (int i = 0; i < findKeys.length; i++) {
			if (i == 0) {
				criteria = Criteria.where(findKeys[i]).is(findValues[i]);
			} else {
				criteria.and(findKeys[i]).is(findValues[i]);
			}
		}
		Query query = Query.query(criteria);
		Object resultObj = mongodbUtils.mongoTemplate.findOne(query, obj.getClass(), collectionName);
		return resultObj;
	}

	/**
	 * 查询出所有结果集 集合为数据对象中
	 *
	 * @param obj 数据对象
	 * @return
	 */
	public static List<? extends Object> findAll(Object obj) {
		List<? extends Object> resultList = mongodbUtils.mongoTemplate.findAll(obj.getClass());
		return resultList;
	}

	/**
	 * 指定集合 查询出所有结果集
	 *
	 * @param obj            数据对象
	 * @param collectionName 集合名
	 * @return
	 */
	public static List<? extends Object> findAll(Object obj, String collectionName) {
		List<? extends Object> resultList = mongodbUtils.mongoTemplate.findAll(obj.getClass(), collectionName);
		return resultList;
	}

	/**
	 * 分页查询
	 * 
	 * @param skip
	 * @param limit
	 * @param criteria
	 * @param sortStr
	 * @param sortType
	 * @param t
	 * @return
	 */

	public static <T> List<T> findListByTerm(Integer skip, Integer limit, Criteria criteria, String sortStr,
			int sortType, Class<T> t) {
		Query query = new Query();
		query.addCriteria(criteria);
		Direction direction = Direction.ASC;
		switch (sortType) {
		case 1:
			direction = Direction.DESC;
			break;
		default:
			break;
		}
		query.with(new Sort(direction, sortStr));
		if (skip != null) {
			query.skip(skip).limit(limit);
		}
		return mongodbUtils.mongoTemplate.find(query, t);
	}

	/**
	 * 获取实体
	 * 
	 * @param id
	 * @param t
	 * @return
	 */
	public static <T> T findOne(String id, Class<T> t) {
		Query query = new Query();
		query.addCriteria(new Criteria("_id").is(id));
		return mongodbUtils.mongoTemplate.findOne(query, t);
	}

	/**
	 * 实体更新
	 * 
	 * @param t
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> void update(T t) {
		try {
			Query query = new Query();
			Class tc = t.getClass();
			query.addCriteria(new Criteria("_id").is(tc.getDeclaredMethod("getId").invoke(t)));
			Update update = new Update();
			Field[] fields = tc.getDeclaredFields();
			for (Field field : fields) {
				if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
					continue;
				}
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), tc);
				Method getMethod = pd.getReadMethod();
				update.set(field.getName(), getMethod.invoke(t));
			}
			mongodbUtils.mongoTemplate.updateFirst(query, update, t.getClass());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}
