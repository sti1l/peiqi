package com.peiqi.util;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author STILL
 *
 */
@Component
public class RedisUtil {

	static RedisUtil redisUtil;

	@PostConstruct
	public void init() {
		redisUtil = this;
	}

	@Resource(name = "stringRedisTemplate")
	private StringRedisTemplate redisTemplate;

	/** -------------------事务相关操作，批量执行命令--------------------- */
	/**
	 * 批量执行命令（同一个事务）
	 * @param list	执行命令集合
	 *                 参数类型：List<Map>
	 *              	Map中key说明：key			说明
	 *              				 "key"			键
	 *              				 "value"		值（更新键操作时，值为正自增、值为负自减）
	 *              				 "opetype"	操作类型，set：新增键；increment：更新键；delete：删除键；
	 * @return	全部执行成功返回值：[true,true...]；出现错误返回值：[true,false..]；异常返回值：null；
	 */
	@SuppressWarnings("rawtypes")
	public static Object batchExecute(List<Map> list) {
		if (null == list || list.size() == 0) {
			return null;
		}
		try {
			SessionCallback<Object> callback = new SessionCallback<Object>() {
				@SuppressWarnings("unchecked")
				@Override
				public Object execute(RedisOperations operations) throws DataAccessException {
					operations.multi();
					for (Map map : list) {
						String opetype = map.get("opetype").toString();
						if (opetype.equals("set")) {
							//新增/重置
							operations.opsForValue().set(map.get("key").toString(), map.get("value").toString());
						} else if (opetype.equals("increment")) {
							//更新数量
							operations.opsForValue().increment(map.get("key").toString(), Integer.valueOf(map.get("value").toString()));
						} else if (opetype.equals("delete")) {
							//删除键
							operations.delete(map.get("key").toString());
						} else {
							System.out.println("未知的操作类型：" + opetype);
							return null;
						}
					}
					return operations.exec();
				}
			};
			return redisUtil.redisTemplate.execute(callback);
		} catch (Exception e) {
			System.out.println("批量执行命令（同一个事务）异常！");
			e.printStackTrace();
			return null;
		}
	}

	/** -------------------key相关操作--------------------- */

	/**
	 * 	key存在时删除key
	 * @param key
	 */
	public static  void delete(String key) {
		redisUtil.redisTemplate.delete(key);
	}

	/**
	 * 批量删除key
	 * @param keys
	 */
	public static  void delete(Collection<String> keys) {
		redisUtil.redisTemplate.delete(keys);
	}

	/**
	 * 序列化key，返回被序列化的值
	 * @param key
	 * @return
	 */
	public static  byte[] dump(String key) {
		return redisUtil.redisTemplate.dump(key);
	}

	/**
	 * 是否存在key
	 * @param key
	 * @return
	 */
	public static  Boolean hasKey(String key) {
		return redisUtil.redisTemplate.hasKey(key);
	}

	/**
	 * 设置过期时间
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public static  Boolean expire(String key, long timeout, TimeUnit unit) {
		return redisUtil.redisTemplate.expire(key, timeout, unit);
	}

	/**
	 * 设置过期时间
	 * @param key
	 * @param date
	 * @return
	 */
	public static  Boolean expireAt(String key, Date date) {
		return redisUtil.redisTemplate.expireAt(key, date);
	}

	/**
	 * 查找匹配的key
	 *
	 * @param pattern
	 * @return
	 */
	public static  Set<String> keys(String pattern) {
		return redisUtil.redisTemplate.keys(pattern);
	}

	/**
	 * 将当前数据库的 key 移动到给定的数据库 db 当中
	 *
	 * @param key
	 * @param dbIndex
	 * @return
	 */
	public static  Boolean move(String key, int dbIndex) {
		return redisUtil.redisTemplate.move(key, dbIndex);
	}

	/**
	 * 移除 key 的过期时间，key 将持久保持
	 *
	 * @param key
	 * @return
	 */
	public static  Boolean persist(String key) {
		return redisUtil.redisTemplate.persist(key);
	}

	/**
	 * 返回 key 的剩余的过期时间
	 *
	 * @param key
	 * @param unit
	 * @return
	 */
	public static  Long getExpire(String key, TimeUnit unit) {
		return redisUtil.redisTemplate.getExpire(key, unit);
	}

	/**
	 * 返回 key 的剩余的过期时间
	 *
	 * @param key
	 * @return
	 */
	public static  Long getExpire(String key) {
		return redisUtil.redisTemplate.getExpire(key);
	}

	/**
	 * 从当前数据库中随机返回一个 key
	 *
	 * @return
	 */
	public static  String randomKey() {
		return redisUtil.redisTemplate.randomKey();
	}

	/**
	 * 修改 key 的名称
	 *
	 * @param oldKey
	 * @param newKey
	 */
	public static  void rename(String oldKey, String newKey) {
		redisUtil.redisTemplate.rename(oldKey, newKey);
	}

	/**
	 * 仅当 newkey 不存在时，将 oldKey 改名为 newkey
	 *
	 * @param oldKey
	 * @param newKey
	 * @return
	 */
	public static  Boolean renameIfAbsent(String oldKey, String newKey) {
		return redisUtil.redisTemplate.renameIfAbsent(oldKey, newKey);
	}

	/**
	 * 返回 key 所储存的值的类型
	 *
	 * @param key
	 * @return
	 */
	public static  DataType type(String key) {
		return redisUtil.redisTemplate.type(key);
	}

	/* -------------------string相关操作--------------------- */

	/**
	 * 设置指定 key 的值
	 * @param key
	 * @param value
	 */
	public static  void set(String key, String value) {
		redisUtil.redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 获取指定 key 的值
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return redisUtil.redisTemplate.opsForValue().get(key);
	}

	/**
	 * 返回 key 中字符串值的子字符
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static  String getRange(String key, long start, long end) {
		return redisUtil.redisTemplate.opsForValue().get(key, start, end);
	}

	/**
	 * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static  String getAndSet(String key, String value) {
		return redisUtil.redisTemplate.opsForValue().getAndSet(key, value);
	}

	/**
	 * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)
	 *
	 * @param key
	 * @param offset
	 * @return
	 */
	public static  Boolean getBit(String key, long offset) {
		return redisUtil.redisTemplate.opsForValue().getBit(key, offset);
	}

	/**
	 * 批量获取
	 *
	 * @param keys
	 * @return
	 */
	public static  List<String> multiGet(Collection<String> keys) {
		return redisUtil.redisTemplate.opsForValue().multiGet(keys);
	}

	/**
	 * 设置ASCII码, 字符串'a'的ASCII码是97, 转为二进制是'01100001', 此方法是将二进制第offset位值变为value
	 *
	 * @param key
	 * @param offset
	 *            位置
	 * @param value
	 *            值,true为1, false为0
	 * @return
	 */
	public static  boolean setBit(String key, long offset, boolean value) {
		return redisUtil.redisTemplate.opsForValue().setBit(key, offset, value);
	}

	/**
	 * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
	 *
	 * @param key
	 * @param value
	 * @param timeout
	 *            过期时间
	 * @param unit
	 *            时间单位, 天:TimeUnit.DAYS 小时:TimeUnit.HOURS 分钟:TimeUnit.MINUTES
	 *            秒:TimeUnit.SECONDS 毫秒:TimeUnit.MILLISECONDS
	 */
	public static void setEx(String key, String value, long timeout, TimeUnit unit) {
		redisUtil.redisTemplate.opsForValue().set(key, value, timeout, unit);
	}

	/**
	 * 只有在 key 不存在时设置 key 的值
	 *
	 * @param key
	 * @param value
	 * @return 之前已经存在返回false,不存在返回true
	 */
	public static  boolean setIfAbsent(String key, String value) {
		return redisUtil.redisTemplate.opsForValue().setIfAbsent(key, value);
	}

	/**
	 * 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始
	 *
	 * @param key
	 * @param value
	 * @param offset
	 *            从指定位置开始覆写
	 */
	public static  void setRange(String key, String value, long offset) {
		redisUtil.redisTemplate.opsForValue().set(key, value, offset);
	}

	/**
	 * 获取字符串的长度
	 *
	 * @param key
	 * @return
	 */
	public static  Long size(String key) {
		return redisUtil.redisTemplate.opsForValue().size(key);
	}

	/**
	 * 批量添加
	 *
	 * @param maps
	 */
	public static  void multiSet(Map<String, String> maps) {
		redisUtil.redisTemplate.opsForValue().multiSet(maps);
	}

	/**
	 * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在
	 *
	 * @param maps
	 * @return 之前已经存在返回false,不存在返回true
	 */
	public static  boolean multiSetIfAbsent(Map<String, String> maps) {
		return redisUtil.redisTemplate.opsForValue().multiSetIfAbsent(maps);
	}

	/**
	 * 增加(自增长), 负数则为自减
	 *
	 * @param key
	 * @param increment
	 * @return
	 */
	public static  Long incrBy(String key, long increment) {
		return redisUtil.redisTemplate.opsForValue().increment(key, increment);
	}

	/**
	 *
	 * @param key
	 * @param increment
	 * @return
	 */
	public static  Double incrByFloat(String key, double increment) {
		return redisUtil.redisTemplate.opsForValue().increment(key, increment);
	}

	/**
	 * 追加到末尾
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static  Integer append(String key, String value) {
		return redisUtil.redisTemplate.opsForValue().append(key, value);
	}

	/** -------------------hash相关操作------------------------- */

	/**
	 * 获取存储在哈希表中指定字段的值
	 *
	 * @param key
	 * @param field
	 * @return
	 */
	public static  Object hGet(String key, String field) {
		return redisUtil.redisTemplate.opsForHash().get(key, field);
	}

	/**
	 * 获取所有给定字段的值
	 *
	 * @param key
	 * @return
	 */
	public static  Map<Object, Object> hGetAll(String key) {
		return redisUtil.redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 获取所有给定字段的值
	 *
	 * @param key
	 * @param fields
	 * @return
	 */
	public static  List<Object> hMultiGet(String key, Collection<Object> fields) {
		return redisUtil.redisTemplate.opsForHash().multiGet(key, fields);
	}

	/**
	 * 添加字段
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public static  void hPut(String key, String hashKey, String value) {
		redisUtil.redisTemplate.opsForHash().put(key, hashKey, value);
	}

	/**
	 * 添加多个字段
	 * @param key
	 * @param maps
	 */
	public static  void hPutAll(String key, Map<String, String> maps) {
		redisUtil.redisTemplate.opsForHash().putAll(key, maps);
	}

	/**
	 * 仅当hashKey不存在时才设置
	 *
	 * @param key
	 * @param hashKey
	 * @param value
	 * @return
	 */
	public static  Boolean hPutIfAbsent(String key, String hashKey, String value) {
		return redisUtil.redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
	}

	/**
	 * 删除一个或多个哈希表字段
	 *
	 * @param key
	 * @param fields
	 * @return
	 */
	public static  Long hDelete(String key, Object... fields) {
		return redisUtil.redisTemplate.opsForHash().delete(key, fields);
	}

	/**
	 * 查看哈希表 key 中，指定的字段是否存在
	 *
	 * @param key
	 * @param field
	 * @return
	 */
	public static  boolean hExists(String key, String field) {
		return redisUtil.redisTemplate.opsForHash().hasKey(key, field);
	}

	/**
	 * 为哈希表 key 中的指定字段的整数值加上增量 increment
	 *
	 * @param key
	 * @param field
	 * @param increment
	 * @return
	 */
	public static  Long hIncrBy(String key, Object field, long increment) {
		return redisUtil.redisTemplate.opsForHash().increment(key, field, increment);
	}

	/**
	 * 为哈希表 key 中的指定字段的整数值加上增量 increment
	 *
	 * @param key
	 * @param field
	 * @param delta
	 * @return
	 */
	public static  Double hIncrByFloat(String key, Object field, double delta) {
		return redisUtil.redisTemplate.opsForHash().increment(key, field, delta);
	}

	/**
	 * 获取所有哈希表中的字段
	 *
	 * @param key
	 * @return
	 */
	public static  Set<Object> hKeys(String key) {
		return redisUtil.redisTemplate.opsForHash().keys(key);
	}

	/**
	 * 获取哈希表中字段的数量
	 *
	 * @param key
	 * @return
	 */
	public static  Long hSize(String key) {
		return redisUtil.redisTemplate.opsForHash().size(key);
	}

	/**
	 * 获取哈希表中所有值
	 *
	 * @param key
	 * @return
	 */
	public static  List<Object> hValues(String key) {
		return redisUtil.redisTemplate.opsForHash().values(key);
	}

	/**
	 * 迭代哈希表中的键值对
	 *
	 * @param key
	 * @param options
	 * @return
	 */
	public static  Cursor<Entry<Object, Object>> hScan(String key, ScanOptions options) {
		return redisUtil.redisTemplate.opsForHash().scan(key, options);
	}

	/** ------------------------list相关操作---------------------------- */

	/**
	 * 通过索引获取列表中的元素
	 *
	 * @param key
	 * @param index
	 * @return
	 */
	public static  String lIndex(String key, long index) {
		return redisUtil.redisTemplate.opsForList().index(key, index);
	}

	/**
	 * 获取列表指定范围内的元素
	 *
	 * @param key
	 * @param start
	 *            开始位置, 0是开始位置
	 * @param end
	 *            结束位置, -1返回所有
	 * @return
	 */
	public static  List<String> lRange(String key, long start, long end) {
		return redisUtil.redisTemplate.opsForList().range(key, start, end);
	}

	/**
	 * 存储在list头部
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static  Long lLeftPush(String key, String value) {
		return redisUtil.redisTemplate.opsForList().leftPush(key, value);
	}

	/**
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static  Long lLeftPushAll(String key, String... value) {
		return redisUtil.redisTemplate.opsForList().leftPushAll(key, value);
	}

	/**
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static  Long lLeftPushAll(String key, Collection<String> value) {
		return redisUtil.redisTemplate.opsForList().leftPushAll(key, value);
	}

	/**
	 * 当list存在的时候才加入
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static  Long lLeftPushIfPresent(String key, String value) {
		return redisUtil.redisTemplate.opsForList().leftPushIfPresent(key, value);
	}

	/**
	 * 如果pivot存在,再pivot前面添加
	 *
	 * @param key
	 * @param pivot
	 * @param value
	 * @return
	 */
	public static  Long lLeftPush(String key, String pivot, String value) {
		return redisUtil.redisTemplate.opsForList().leftPush(key, pivot, value);
	}

	/**
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static  Long lRightPush(String key, String value) {
		return redisUtil.redisTemplate.opsForList().rightPush(key, value);
	}

	/**
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static  Long lRightPushAll(String key, String... value) {
		return redisUtil.redisTemplate.opsForList().rightPushAll(key, value);
	}

	/**
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static  Long lRightPushAll(String key, Collection<String> value) {
		return redisUtil.redisTemplate.opsForList().rightPushAll(key, value);
	}

	/**
	 * 为已存在的列表添加值
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static  Long lRightPushIfPresent(String key, String value) {
		return redisUtil.redisTemplate.opsForList().rightPushIfPresent(key, value);
	}

	/**
	 * 在pivot元素的右边添加值
	 *
	 * @param key
	 * @param pivot
	 * @param value
	 * @return
	 */
	public static  Long lRightPush(String key, String pivot, String value) {
		return redisUtil.redisTemplate.opsForList().rightPush(key, pivot, value);
	}

	/**
	 * 通过索引设置列表元素的值
	 *
	 * @param key
	 * @param index
	 *            位置
	 * @param value
	 */
	public static  void lSet(String key, long index, String value) {
		redisUtil.redisTemplate.opsForList().set(key, index, value);
	}

	/**
	 * 移出并获取列表的第一个元素
	 *
	 * @param key
	 * @return 删除的元素
	 */
	public static  String lLeftPop(String key) {
		return redisUtil.redisTemplate.opsForList().leftPop(key);
	}

	/**
	 * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
	 *
	 * @param key
	 * @param timeout
	 *            等待时间
	 * @param unit
	 *            时间单位
	 * @return
	 */
	public static  String lBLeftPop(String key, long timeout, TimeUnit unit) {
		return redisUtil.redisTemplate.opsForList().leftPop(key, timeout, unit);
	}

	/**
	 * 移除并获取列表最后一个元素
	 *
	 * @param key
	 * @return 删除的元素
	 */
	public static  String lRightPop(String key) {
		return redisUtil.redisTemplate.opsForList().rightPop(key);
	}

	/**
	 * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
	 *
	 * @param key
	 * @param timeout
	 *            等待时间
	 * @param unit
	 *            时间单位
	 * @return
	 */
	public static  String lBRightPop(String key, long timeout, TimeUnit unit) {
		return redisUtil.redisTemplate.opsForList().rightPop(key, timeout, unit);
	}

	/**
	 * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
	 *
	 * @param sourceKey
	 * @param destinationKey
	 * @return
	 */
	public static  String lRightPopAndLeftPush(String sourceKey, String destinationKey) {
		return redisUtil.redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,
				destinationKey);
	}

	/**
	 * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
	 *
	 * @param sourceKey
	 * @param destinationKey
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public static  String lBRightPopAndLeftPush(String sourceKey, String destinationKey,
												long timeout, TimeUnit unit) {
		return redisUtil.redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,
				destinationKey, timeout, unit);
	}

	/**
	 * 删除集合中值等于value得元素
	 *
	 * @param key
	 * @param index
	 *            index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素;
	 *            index<0, 从尾部开始删除第一个值等于value的元素;
	 * @param value
	 * @return
	 */
	public static  Long lRemove(String key, long index, String value) {
		return redisUtil.redisTemplate.opsForList().remove(key, index, value);
	}

	/**
	 * 裁剪list
	 *
	 * @param key
	 * @param start
	 * @param end
	 */
	public static  void lTrim(String key, long start, long end) {
		redisUtil.redisTemplate.opsForList().trim(key, start, end);
	}

	/**
	 * 获取列表长度
	 *
	 * @param key
	 * @return
	 */
	public static  Long lLen(String key) {
		return redisUtil.redisTemplate.opsForList().size(key);
	}

	/** --------------------set相关操作-------------------------- */

	/**
	 * set添加元素
	 *
	 * @param key
	 * @param values
	 * @return
	 */
	public static  Long sAdd(String key, String... values) {
		return redisUtil.redisTemplate.opsForSet().add(key, values);
	}

	/**
	 * set移除元素
	 *
	 * @param key
	 * @param values
	 * @return
	 */
	public static  Long sRemove(String key, Object... values) {
		return redisUtil.redisTemplate.opsForSet().remove(key, values);
	}

	/**
	 * 移除并返回集合的一个随机元素
	 *
	 * @param key
	 * @return
	 */
	public static  String sPop(String key) {
		return redisUtil.redisTemplate.opsForSet().pop(key);
	}

	/**
	 * 将元素value从一个集合移到另一个集合
	 *
	 * @param key
	 * @param value
	 * @param destKey
	 * @return
	 */
	public static  Boolean sMove(String key, String value, String destKey) {
		return redisUtil.redisTemplate.opsForSet().move(key, value, destKey);
	}

	/**
	 * 获取集合的大小
	 *
	 * @param key
	 * @return
	 */
	public static  Long sSize(String key) {
		return redisUtil.redisTemplate.opsForSet().size(key);
	}

	/**
	 * 判断集合是否包含value
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static  Boolean sIsMember(String key, Object value) {
		return redisUtil.redisTemplate.opsForSet().isMember(key, value);
	}

	/**
	 * 获取两个集合的交集
	 *
	 * @param key
	 * @param otherKey
	 * @return
	 */
	public static  Set<String> sIntersect(String key, String otherKey) {
		return redisUtil.redisTemplate.opsForSet().intersect(key, otherKey);
	}

	/**
	 * 获取key集合与多个集合的交集
	 *
	 * @param key
	 * @param otherKeys
	 * @return
	 */
	public static  Set<String> sIntersect(String key, Collection<String> otherKeys) {
		return redisUtil.redisTemplate.opsForSet().intersect(key, otherKeys);
	}

	/**
	 * key集合与otherKey集合的交集存储到destKey集合中
	 *
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public static  Long sIntersectAndStore(String key, String otherKey, String destKey) {
		return redisUtil.redisTemplate.opsForSet().intersectAndStore(key, otherKey,
				destKey);
	}

	/**
	 * key集合与多个集合的交集存储到destKey集合中
	 *
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public static  Long sIntersectAndStore(String key, Collection<String> otherKeys,
										   String destKey) {
		return redisUtil.redisTemplate.opsForSet().intersectAndStore(key, otherKeys,
				destKey);
	}

	/**
	 * 获取两个集合的并集
	 *
	 * @param key
	 * @param otherKeys
	 * @return
	 */
	public static  Set<String> sUnion(String key, String otherKeys) {
		return redisUtil.redisTemplate.opsForSet().union(key, otherKeys);
	}

	/**
	 * 获取key集合与多个集合的并集
	 *
	 * @param key
	 * @param otherKeys
	 * @return
	 */
	public static  Set<String> sUnion(String key, Collection<String> otherKeys) {
		return redisUtil.redisTemplate.opsForSet().union(key, otherKeys);
	}

	/**
	 * key集合与otherKey集合的并集存储到destKey中
	 *
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public static  Long sUnionAndStore(String key, String otherKey, String destKey) {
		return redisUtil.redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
	}

	/**
	 * key集合与多个集合的并集存储到destKey中
	 *
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public static  Long sUnionAndStore(String key, Collection<String> otherKeys,
									   String destKey) {
		return redisUtil.redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
	}

	/**
	 * 获取两个集合的差集
	 *
	 * @param key
	 * @param otherKey
	 * @return
	 */
	public static  Set<String> sDifference(String key, String otherKey) {
		return redisUtil.redisTemplate.opsForSet().difference(key, otherKey);
	}

	/**
	 * 获取key集合与多个集合的差集
	 *
	 * @param key
	 * @param otherKeys
	 * @return
	 */
	public static  Set<String> sDifference(String key, Collection<String> otherKeys) {
		return redisUtil.redisTemplate.opsForSet().difference(key, otherKeys);
	}

	/**
	 * key集合与otherKey集合的差集存储到destKey中
	 *
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public static  Long sDifference(String key, String otherKey, String destKey) {
		return redisUtil.redisTemplate.opsForSet().differenceAndStore(key, otherKey,
				destKey);
	}

	/**
	 * key集合与多个集合的差集存储到destKey中
	 *
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public static  Long sDifference(String key, Collection<String> otherKeys,
									String destKey) {
		return redisUtil.redisTemplate.opsForSet().differenceAndStore(key, otherKeys,
				destKey);
	}

	/**
	 * 获取集合所有元素
	 *
	 * @param key
	 * @return
	 */
	public static  Set<String> setMembers(String key) {
		return redisUtil.redisTemplate.opsForSet().members(key);
	}

	/**
	 * 随机获取集合中的一个元素
	 *
	 * @param key
	 * @return
	 */
	public static  String sRandomMember(String key) {
		return redisUtil.redisTemplate.opsForSet().randomMember(key);
	}

	/**
	 * 随机获取集合中count个元素
	 *
	 * @param key
	 * @param count
	 * @return
	 */
	public static  List<String> sRandomMembers(String key, long count) {
		return redisUtil.redisTemplate.opsForSet().randomMembers(key, count);
	}

	/**
	 * 随机获取集合中count个元素并且去除重复的
	 *
	 * @param key
	 * @param count
	 * @return
	 */
	public static  Set<String> sDistinctRandomMembers(String key, long count) {
		return redisUtil.redisTemplate.opsForSet().distinctRandomMembers(key, count);
	}

	/**
	 *
	 * @param key
	 * @param options
	 * @return
	 */
	public static  Cursor<String> sScan(String key, ScanOptions options) {
		return redisUtil.redisTemplate.opsForSet().scan(key, options);
	}

	/**------------------zSet相关操作--------------------------------*/

	/**
	 * 添加元素,有序集合是按照元素的score值由小到大排列
	 *
	 * @param key
	 * @param value
	 * @param score
	 * @return
	 */
	public static  Boolean zAdd(String key, String value, double score) {
		return redisUtil.redisTemplate.opsForZSet().add(key, value, score);
	}

	/**
	 *
	 * @param key
	 * @param values
	 * @return
	 */
	public static  Long zAdd(String key, Set<TypedTuple<String>> values) {
		return redisUtil.redisTemplate.opsForZSet().add(key, values);
	}

	/**
	 *
	 * @param key
	 * @param values
	 * @return
	 */
	public static  Long zRemove(String key, Object... values) {
		return redisUtil.redisTemplate.opsForZSet().remove(key, values);
	}

	/**
	 * 增加元素的score值，并返回增加后的值
	 *
	 * @param key
	 * @param value
	 * @param delta
	 * @return
	 */
	public static  Double zIncrementScore(String key, String value, double delta) {
		return redisUtil.redisTemplate.opsForZSet().incrementScore(key, value, delta);
	}

	/**
	 * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
	 *
	 * @param key
	 * @param value
	 * @return 0表示第一位
	 */
	public static  Long zRank(String key, Object value) {
		return redisUtil.redisTemplate.opsForZSet().rank(key, value);
	}

	/**
	 * 返回元素在集合的排名,按元素的score值由大到小排列
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static  Long zReverseRank(String key, Object value) {
		return redisUtil.redisTemplate.opsForZSet().reverseRank(key, value);
	}

	/**
	 * 获取集合的元素, 从小到大排序
	 *
	 * @param key
	 * @param start
	 *            开始位置
	 * @param end
	 *            结束位置, -1查询所有
	 * @return
	 */
	public static  Set<String> zRange(String key, long start, long end) {
		return redisUtil.redisTemplate.opsForZSet().range(key, start, end);
	}

	/**
	 * 获取集合元素, 并且把score值也获取
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static  Set<TypedTuple<String>> zRangeWithScores(String key, long start,
															long end) {
		return redisUtil.redisTemplate.opsForZSet().rangeWithScores(key, start, end);
	}

	/**
	 * 根据Score值查询集合元素
	 *
	 * @param key
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @return
	 */
	public static  Set<String> zRangeByScore(String key, double min, double max) {
		return redisUtil.redisTemplate.opsForZSet().rangeByScore(key, min, max);
	}

	/**
	 * 根据Score值查询集合元素, 从小到大排序
	 *
	 * @param key
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @return
	 */
	public static  Set<TypedTuple<String>> zRangeByScoreWithScores(String key,
																   double min, double max) {
		return redisUtil.redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
	}

	/**
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @param start
	 * @param end
	 * @return
	 */
	public static  Set<TypedTuple<String>> zRangeByScoreWithScores(String key,
																   double min, double max, long start, long end) {
		return redisUtil.redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max,
				start, end);
	}

	/**
	 * 获取集合的元素, 从大到小排序
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static  Set<String> zReverseRange(String key, long start, long end) {
		return redisUtil.redisTemplate.opsForZSet().reverseRange(key, start, end);
	}

	/**
	 * 获取集合的元素, 从大到小排序, 并返回score值
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static  Set<TypedTuple<String>> zReverseRangeWithScores(String key,
																   long start, long end) {
		return redisUtil.redisTemplate.opsForZSet().reverseRangeWithScores(key, start,
				end);
	}

	/**
	 * 根据Score值查询集合元素, 从大到小排序
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public static  Set<String> zReverseRangeByScore(String key, double min,
													double max) {
		return redisUtil.redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
	}

	/**
	 * 根据Score值查询集合元素, 从大到小排序
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public static  Set<TypedTuple<String>> zReverseRangeByScoreWithScores(
			String key, double min, double max) {
		return redisUtil.redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key,
				min, max);
	}

	/**
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @param start
	 * @param end
	 * @return
	 */
	public static  Set<String> zReverseRangeByScore(String key, double min,
													double max, long start, long end) {
		return redisUtil.redisTemplate.opsForZSet().reverseRangeByScore(key, min, max,
				start, end);
	}

	/**
	 * 根据score值获取集合元素数量
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public static  Long zCount(String key, double min, double max) {
		return redisUtil.redisTemplate.opsForZSet().count(key, min, max);
	}

	/**
	 * 获取集合大小
	 *
	 * @param key
	 * @return
	 */
	public static  Long zSize(String key) {
		return redisUtil.redisTemplate.opsForZSet().size(key);
	}

	/**
	 * 获取集合大小
	 *
	 * @param key
	 * @return
	 */
	public static  Long zZCard(String key) {
		return redisUtil.redisTemplate.opsForZSet().zCard(key);
	}

	/**
	 * 获取集合中value元素的score值
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static  Double zScore(String key, Object value) {
		return redisUtil.redisTemplate.opsForZSet().score(key, value);
	}

	/**
	 * 移除指定索引位置的成员
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static  Long zRemoveRange(String key, long start, long end) {
		return redisUtil.redisTemplate.opsForZSet().removeRange(key, start, end);
	}

	/**
	 * 根据指定的score值的范围来移除成员
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public static  Long zRemoveRangeByScore(String key, double min, double max) {
		return redisUtil.redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
	}

	/**
	 * 获取key和otherKey的并集并存储在destKey中
	 *
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public static  Long zUnionAndStore(String key, String otherKey, String destKey) {
		return redisUtil.redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
	}

	/**
	 *
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public static  Long zUnionAndStore(String key, Collection<String> otherKeys,
									   String destKey) {
		return redisUtil.redisTemplate.opsForZSet()
				.unionAndStore(key, otherKeys, destKey);
	}

	/**
	 * 交集
	 *
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public static  Long zIntersectAndStore(String key, String otherKey,
										   String destKey) {
		return redisUtil.redisTemplate.opsForZSet().intersectAndStore(key, otherKey,
				destKey);
	}

	/**
	 * 交集
	 *
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public static  Long zIntersectAndStore(String key, Collection<String> otherKeys,
										   String destKey) {
		return redisUtil.redisTemplate.opsForZSet().intersectAndStore(key, otherKeys,
				destKey);
	}

	/**
	 *
	 * @param key
	 * @param options
	 * @return
	 */
	public static  Cursor<TypedTuple<String>> zScan(String key, ScanOptions options) {
		return redisUtil.redisTemplate.opsForZSet().scan(key, options);
	}

}