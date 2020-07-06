package com.peiqi.hibernate.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * SQL 工具类
 * 
 * @author STILL
 *
 */
public class SqlUtil {

	/**
	 * 利用反射机制构建插入语句，支持预编译，parmMap存放需要使用占位符的字段，key字段名，value标识第几个占位符
	 * 
	 * @param object	实体类
	 * @param tb_name	表名
	 * @param parmMap  	parmMap存放需要使用占位符的字段，key字段名，value标识第几个占位符
	 * @param setNopre 	不需要在set子句出现的字段
	 * @return
	 * @throws Exception
	 */
	public static String parsePreInsqlByEntity(Object object, String tb_name, Map<String, Integer> parmMap,
			List<String> setNopre) throws Exception {
		StringBuilder builder = new StringBuilder("insert into ");
		builder.append(tb_name).append("(");
		StringBuilder vbuilder = new StringBuilder();
		StringBuilder getName = new StringBuilder("get");
		Class<?> ordclass = object.getClass();
		Field[] fields = ordclass.getDeclaredFields();
		int i = 0;
		int j = 1;
		Method method;
		String type;
		String fname;
		Object obj;
		for (Field field : fields) {
			fname = field.getName();
			if ("id".equals(fname)) {
				continue;
			}
			if ("serialVersionUID".equals(fname))
				continue;
			if (setNopre != null && setNopre.contains(fname)) {
				continue;
			}
			if (i++ > 0) {
				builder.append(",");
				vbuilder.append(",");
			}
			builder.append(fname);
			if (parmMap != null && parmMap.keySet().contains(fname)) {
				vbuilder.append("?");
				parmMap.put(fname, j++);
			} else {
				getName.append(fname.substring(0, 1).toUpperCase()).append(fname.substring(1));
				method = ordclass.getMethod(getName.toString());
				obj = method.invoke(object);
				if (obj == null) {
					vbuilder.append("null");
				} else {
					type = field.getType().getName();
					if ("java.lang.String".equals(type)) {
						vbuilder.append("'").append(StringEscapeUtils.escapeSql(String.valueOf(obj))).append("'");
					} else if ("int".equals(type) || "java.lang.Integer".equals(type)) {
						vbuilder.append(obj);
					} else if ("double".equals(type) || "java.lang.Double".equals(type)) {
						vbuilder.append(obj);
					} else if ("float".equals(type) || "java.lang.Float".equals(type)) {
						vbuilder.append(obj);
					} else if ("java.sql.Timestamp".equals(type)) {
						vbuilder.append("'").append((Timestamp) obj).append("'");
					} else if ("java.util.Date".equals(type)) {
						vbuilder.append("'").append(TimeUtil.formatDateToMill((Date) obj)).append("'");
					} else if ("java.sql.Time".equals(type)) {
						vbuilder.append("'").append((Time) obj).append("'");
					} else if ("short".equals(type) || "java.lang.Short".equals(type)) {
						vbuilder.append(obj);
					} else if ("byte".equals(type) || "java.lang.Byte".equals(type)) {
						vbuilder.append(obj);
					} else if ("java.math.BigDecimal".equals(type)) {
						vbuilder.append(obj);
					} else {
						System.out.println("***未知数据类型，需要补充****");
						throw new RuntimeException();
					}
				}
				getName.delete(3, getName.length());
			}
		}

		builder.append(") values (").append(vbuilder.toString()).append(")");
		return builder.toString();
	}

	/**
	 * 利用反射机制构建sql插入语句，支持预编译，parmMap存放需要使用占位符的字段，key字段名，value标识第几个占位符
	 * 重写方法，加一个参数wholeTbname，原有方法表名无法指定库实例
	 * 
	 * @param object      实体类
	 * @param tbname      此字段弃用
	 * @param parmMap     parmMap存放需要使用占位符的字段，key字段名，value标识第几个占位符
	 * @param setNopre    不需要在set子句出现的字段
	 * @param wholeTbname 可以携带库实例名的表名
	 * @return
	 * @throws Exception
	 */
	public static String parsePreInsqlByEntity(Object object, String tbname, Map<String, Integer> parmMap,
			List<String> setNopre, String wholeTbname) throws Exception {
		StringBuilder builder = new StringBuilder("insert into ");
		builder.append(wholeTbname).append("(");
		StringBuilder vbuilder = new StringBuilder();
		StringBuilder getName = new StringBuilder("get");
		Class<?> ordclass = object.getClass();
		Field[] fields = ordclass.getDeclaredFields();
		int i = 0;
		int j = 1;
		Method method;
		String type;
		String fname;
		Object obj;
		for (Field field : fields) {
			fname = field.getName();
			if ("id".equals(fname)) {
				continue;
			}
			if ("serialVersionUID".equals(fname))
				continue;
			if (setNopre != null && setNopre.contains(fname)) {
				continue;
			}
			if (i++ > 0) {
				builder.append(",");
				vbuilder.append(",");
			}
			builder.append(fname);
			if (parmMap != null && parmMap.keySet().contains(fname)) {
				vbuilder.append("?");
				parmMap.put(fname, j++);
			} else {
				getName.append(fname.substring(0, 1).toUpperCase()).append(fname.substring(1));
				method = ordclass.getMethod(getName.toString());
				obj = method.invoke(object);
				if (obj == null) {
					vbuilder.append("null");
				} else {
					type = field.getType().getName();
					if ("java.lang.String".equals(type)) {
						vbuilder.append("'").append(StringEscapeUtils.escapeSql(String.valueOf(obj))).append("'");
					} else if ("int".equals(type) || "java.lang.Integer".equals(type)) {
						vbuilder.append(obj);
					} else if ("double".equals(type) || "java.lang.Double".equals(type)) {
						vbuilder.append(obj);
					} else if ("float".equals(type) || "java.lang.Float".equals(type)) {
						vbuilder.append(obj);
					} else if ("java.sql.Timestamp".equals(type)) {
						vbuilder.append("'").append((Timestamp) obj).append("'");
					} else if ("java.util.Date".equals(type)) {
						vbuilder.append("'").append(TimeUtil.formatDateToMill((Date) obj)).append("'");
					} else if ("java.sql.Time".equals(type)) {
						vbuilder.append("'").append((Time) obj).append("'");
					} else if ("short".equals(type) || "java.lang.Short".equals(type)) {
						vbuilder.append(obj);
					} else if ("byte".equals(type) || "java.lang.Byte".equals(type)) {
						vbuilder.append(obj);
					} else if ("java.math.BigDecimal".equals(type)) {
						vbuilder.append(obj);
					} else {
						System.out.println("***未知数据类型，需要补充****");
						throw new RuntimeException();
					}
				}
				getName.delete(3, getName.length());
			}
		}

		builder.append(") values (").append(vbuilder.toString()).append(")");
		return builder.toString();
	}

	/**
	 * 利用反射机制构建sql更新语句，支持预编译，
	 * 
	 * @param object	java对象
	 * @param tbname	表名
	 * @param parmMap  	parmMap存放需要使用占位符的字段，key字段名，value标识第几个占位符
	 * @param cond    	where字句要用到的字段，key字段名，value值，需要用占位符的直接存null即可
	 * @param setNopre 	不需要在set子句出现的字段
	 * @return
	 * @throws Exception
	 */
	public static String parsePreUpdsqlByEntity(Object object, String tbname, Map<String, Integer> parmMap,
			Map<String, Object> cond, List<String> setNopre) throws Exception {
		StringBuilder setBuilder = new StringBuilder("update tb_");
		setBuilder.append(tbname).append(" set ");
		StringBuilder getName = new StringBuilder("get");
		Class<?> ordclass = object.getClass();
		Field[] fields = ordclass.getDeclaredFields();
		int i = 0;
		int j = 1;
		Method method;
		String type;
		String fname;
		Object obj;
		for (Field field : fields) {
			fname = field.getName();
			if ("id".equals(fname)) {
				continue;
			}
			if ("serialVersionUID".equals(fname))
				continue;
			if (setNopre == null || !setNopre.contains(fname)) {
				if (i++ > 0) {
					setBuilder.append(",");
				}
				setBuilder.append(fname).append(" = ");
				if (parmMap != null && parmMap.keySet().contains(fname)) {
					setBuilder.append("?");
					parmMap.put(fname, j++);
				} else {
					getName.append(fname.substring(0, 1).toUpperCase()).append(fname.substring(1));
					method = ordclass.getMethod(getName.toString());
					obj = method.invoke(object);
					if (obj == null) {
						setBuilder.append(obj);
					} else {
						type = field.getType().getName();
						if ("java.lang.String".equals(type)) {
							setBuilder.append(" '").append(String.valueOf(obj)).append("'");
						} else if ("short".equals(type) || "java.lang.Short".equals(type)) {
							setBuilder.append(obj);
						} else if ("int".equals(type) || "java.lang.Integer".equals(type)) {
							setBuilder.append(obj);
						} else if ("double".equals(type) || "java.lang.Double".equals(type)) {
							setBuilder.append(obj);
						} else if ("float".equals(type) || "java.lang.Float".equals(type)) {
							setBuilder.append(obj);
						} else if ("byte".equals(type) || "java.lang.Byte".equals(type)) {
							setBuilder.append(obj);
						} else if ("java.sql.Timestamp".equals(type)) {
							setBuilder.append(" '").append((Timestamp) obj).append("'");
						} else if ("java.util.Date".equals(type)) {
							setBuilder.append(" '").append(TimeUtil.formatDateToMill((Date) obj)).append("'");
						} else if ("java.sql.Time".equals(type)) {
							setBuilder.append("'").append((Time) obj).append("'");
						} else if ("java.math.BigDecimal".equals(type)) {
							setBuilder.append("'").append(obj).append("'");
						} else {
							System.out.println("***未知数据类型，需要补充****" + type);
							throw new RuntimeException();
						}
					}
					getName.delete(3, getName.length());
				}
			}

		}
		setBuilder.append(" where 1=1 ");
		if (cond != null) {
			for (Entry<String, Object> e : cond.entrySet()) {
				setBuilder.append(" and ").append(e.getKey());
				if (parmMap != null && parmMap.keySet().contains(e.getKey())) {
					setBuilder.append(" = ?");
					parmMap.put(e.getKey(), j++);
				} else {
					getName.append(e.getKey().substring(0, 1).toUpperCase()).append(e.getKey().substring(1));
					method = ordclass.getMethod(getName.toString());
					obj = e.getValue();
					if (e.getValue() == null) {
						setBuilder.append(" is null ");
					} else {
						type = method.getReturnType().getName();
						if ("java.lang.String".equals(type)) {
							setBuilder.append("= '").append(String.valueOf(obj)).append("'");
						} else if ("int".equals(type) || "java.lang.Integer".equals(type)) {
							setBuilder.append(" = ").append(obj);
						} else if ("double".equals(type) || "java.lang.Double".equals(type)) {
							setBuilder.append(" = ").append(obj);
						} else if ("float".equals(type) || "java.lang.Float".equals(type)) {
							setBuilder.append(" = ").append(obj);
						} else if ("java.sql.Timestamp".equals(type)) {
							setBuilder.append("= '").append((Timestamp) obj).append("'");
						} else if ("java.util.Date".equals(type)) {
							setBuilder.append("= '").append(TimeUtil.formatDateToMill((Date) obj)).append("'");
						} else if ("java.sql.Time".equals(type)) {
							setBuilder.append("='").append((Time) obj).append("'");
						} else if ("short".equals(type) || "java.lang.Short".equals(type)) {
							setBuilder.append("=").append(obj);
						} else if ("byte".equals(type) || "java.lang.Byte".equals(type)) {
							setBuilder.append("='").append(obj);
						} else {
							System.out.println("***未知数据类型，需要补充****" + type);
							throw new RuntimeException();
						}
					}
					getName.delete(3, getName.length());
				}
			}
		}
		return setBuilder.toString();
	}

}
