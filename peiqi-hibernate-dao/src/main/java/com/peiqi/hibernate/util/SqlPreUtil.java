package com.peiqi.hibernate.util;

import java.util.List;

import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

/**
 * sql预编译辅助类
 * 
 * @author STILL
 *
 */
public class SqlPreUtil {

	/**
	 * 为query进行参数赋值
	 * 
	 * @param query
	 * @param preParameters
	 */
	public static void setValForQuery(Query<?> query, List<Object> preParameters) {
		if (preParameters != null) {
			int i = 1;// 表示第几个？
			for (Object object : preParameters) {
				/*
				 * if (object instanceof String) { query.setParameter(i, (String)object); } else
				 * if (object instanceof Short) { query.setParameter(i, (Short) object); } else
				 * if (object instanceof Integer) { query.setParameter(i, (Integer) object); }
				 * else if (object instanceof Double) { query.setParameter(i, (Double) object);
				 * } else if (object instanceof Float) { query.setParameter(i, (Float) object);
				 * } else if (object instanceof Timestamp) { query.setParameter(i, (Timestamp)
				 * object); }else if (object instanceof Date) { query.setParameter(i, (Date)
				 * object); }else if(object == null){ query.setParameter(i, null); }else if
				 * (object instanceof BigDecimal) { query.setParameter(i, (BigDecimal)object);
				 * }else { System.out.println("请补充数据类型"); }
				 */
				query.setParameter(i, object);
				i++;
			}
		}
	}

	public static void setValForSqlQuery(NativeQuery<?> sqlQuery, List<Object> preParameters) {
		if (preParameters != null) {
			int i = 1;// 表示第几个？
			for (Object object : preParameters) {
				/*
				 * if (object instanceof String) { sqlQuery.setString(i, (String)object); } else
				 * if (object instanceof Short) { sqlQuery.setShort(i, (Short) object); } else
				 * if (object instanceof Integer) { sqlQuery.setInteger(i, (Integer) object); }
				 * else if (object instanceof Short) { sqlQuery.setShort(i, (Short) object); }
				 * else if (object instanceof Double) { sqlQuery.setDouble(i, (Double) object);
				 * } else if (object instanceof Float) { sqlQuery.setFloat(i, (Float) object); }
				 * else if (object instanceof Timestamp) { sqlQuery.setTimestamp(i, (Timestamp)
				 * object); }else if (object instanceof Date) { sqlQuery.setTimestamp(i, (Date)
				 * object); }else if(object == null){ sqlQuery.setDate(i, null); }else {
				 * System.out.println("请补充数据类型"); }
				 */
				sqlQuery.setParameter(i, object);
				i++;
			}
		}
	}

}
