package com.peiqi.hibernate.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.peiqi.hibernate.bean.PageBean;
import com.peiqi.hibernate.dao.ReadObjectDao;
import com.peiqi.hibernate.util.SqlPreUtil;

@Transactional(propagation = Propagation.REQUIRED)
public abstract class AbstractReadObjectDao implements ReadObjectDao {

	public List<Object> getObjectsByHql(String hql) {
		return this.getObjectsByHql(hql, null, null, null);
	}

	public <T> List<T> getObjectsByHql(String hql, Integer start, Integer size, List<Object> preParameters) {
		Query query = getCurrentSession().createQuery(hql);
		SqlPreUtil.setValForQuery(query, preParameters);
		if (start != null) {
			query.setFirstResult(start).setMaxResults(size);
		}
		return query.list();
	}

	public <T> List<T> getObjectsBySql(String sql, Class<?> classObj) {
		return this.getObjectsBySql(sql, classObj, null, null, null);
	}

	public <T> List<T> getObjectsBySql(String sql, Class<?> classObj, Integer start, Integer size,
			List<Object> preParameters) {
		NativeQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
		sqlQuery.addEntity(classObj);
		SqlPreUtil.setValForSqlQuery(sqlQuery, preParameters);
		if (start != null) {
			sqlQuery.setFirstResult(start).setMaxResults(size);
		}
		return sqlQuery.list();
	}

	public List<Map<String, Object>> getMapOfObjectsBySql(String sql) {
		return this.getMapOfObjectsBySql(sql, null, null, null);
	}

	public List<Map<String, Object>> getMapOfObjectsBySql(String sql, List<Object> preParameters) {
		return this.getMapOfObjectsBySql(sql, null, null, preParameters);
	}

	public Map<String, Object> getMapOfObjectBySql(String sql) {
		List<Map<String, Object>> olist = this.getMapOfObjectsBySql(sql);
		return olist == null || olist.isEmpty() ? null : olist.get(0);
	}

	public Map<String, Object> getMapOfObjectBySql(String sql, List<Object> preParameters) {
		List<Map<String, Object>> olist = this.getMapOfObjectsBySql(sql, null, null, preParameters);
		return olist == null || olist.isEmpty() ? null : olist.get(0);
	}

	public List<Map<String, Object>> getMapOfObjectsBySql(String sql, Integer start, Integer size,
			List<Object> preParameters) {
		NativeQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
		sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		SqlPreUtil.setValForSqlQuery(sqlQuery, preParameters);
		if (start != null) {
			sqlQuery.setFirstResult(start).setMaxResults(size);
		}
		return sqlQuery.list();
	}

	public Object getObjectByHql(String hql) {
		Query query = getCurrentSession().createQuery(hql);
		query.setFirstResult(0).setMaxResults(1);
		return query.uniqueResult();
	}

	public <T> T getObjectById(Class<T> objclass, Integer id) {
		Object object = getCurrentSession().get(objclass, id);
		return object == null ? null : (T) object;
	}

	public <T> T getObjectBySql(String sql, Class<T> classObj) {
		return this.getObjectBySql(sql, classObj, null);
	}

	public <T> T getObjectBySql(String sql, Class<T> classObj, List<Object> preParameters) {
		NativeQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
		SqlPreUtil.setValForQuery(sqlQuery, preParameters);
		sqlQuery.addEntity(classObj);
		sqlQuery.setFirstResult(0).setMaxResults(1);
		return (T) sqlQuery.uniqueResult();
	}

	public int countNumByHql(String hql, List<Object> preParameters) {
		Query query = getCurrentSession().createQuery(hql);
		SqlPreUtil.setValForQuery(query, preParameters);
		List<Long> num = query.list();
		return num == null || num.isEmpty() ? 0 : (num.get(0) == null ? 0 : num.get(0).intValue());
	}

	public int countNumBySql(String sql, List<Object> preParameters) {
		NativeQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
		SqlPreUtil.setValForQuery(sqlQuery, preParameters);
		return ((BigInteger) sqlQuery.uniqueResult()).intValue();
	}

	public List<String> getStringListByHql(String hql) {
		return this.getStringListByHql(hql, null, null);
	}

	public List<String> getStringListByHql(String hql, Integer start, Integer size) {
		Query query = getCurrentSession().createQuery(hql);
		if (start != null) {
			query.setFirstResult(start).setMaxResults(size);
		}
		return query.list();
	}

	public List<String> getStringListBySql(String sql) {
		return this.getStringListBySql(sql, null, null);
	}

	public List<String> getStringListBySql(String sql, Integer start, Integer size) {
		NativeQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
		if (start != null) {
			sqlQuery.setFirstResult(start).setMaxResults(size);
		}
		return sqlQuery.list();
	}

	public List<Object[]> getObjectArrayListByHql(String hql) {
		return this.getObjectArrayListByHql(hql, null, null, null);
	}

	public List<Object[]> getObjectArrayListByHql(String hql, Integer start, Integer size, List<Object> preParameters) {
		Query query = getCurrentSession().createQuery(hql);
		SqlPreUtil.setValForQuery(query, preParameters);
		if (start != null) {
			query.setFirstResult(start).setMaxResults(size);
		}

		return query.list();
	}

	public <T> List<T> getPageBeanBySql(String query, String condition, String sort, Class<?> classObj,
			PageBean pagebean) {
		StringBuilder sql = new StringBuilder();
		sql.append(query).append(" ").append(condition).append(" ");
		if (sort != null) {
			sql.append(sort);
		} else if (pagebean != null) {
			sql.append("ORDER BY ").append(pagebean.getSortfeild());
			switch (pagebean.getSorttype()) {
			case 0:
				sql.append(" DESC");
				break;
			case 1:
				sql.append(" ASC");
				break;
			}
		} else {
			sql.append("ORDER BY id DESC");
		}
		// 查询起始行数
		Integer start = null;
		// 查询数据条数
		Integer size = null;
		if (pagebean != null) {
			// 根据条件修改pagebean
			this.updatePageBean(condition, pagebean);

			start = (pagebean.getCurpage() - 1) * pagebean.getPagesize();
			size = pagebean.getPagesize();
		}
		List<T> list = this.getObjectsBySql(sql.toString(), classObj, start, size, null);
		return list;
	}

	public <T> List<T> getPageBeanBySql(String query, String condition, String sort, Class<?> classObj,
			PageBean pagebean, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append(query).append(" ").append(condition).append(" ");
		if (sort != null) {
			sql.append(sort);
		} else if (pagebean != null) {
			sql.append("ORDER BY ").append(pagebean.getSortfeild());
			switch (pagebean.getSorttype()) {
			case 0:
				sql.append(" DESC");
				break;
			case 1:
				sql.append(" ASC");
				break;
			}
		} else {
			sql.append("ORDER BY id DESC");
		}
		// 查询起始行数
		Integer start = null;
		// 查询数据条数
		Integer size = null;
		if (pagebean != null) {
			// 根据条件修改pagebean
			this.updatePageBean(condition, pagebean, params);

			start = (pagebean.getCurpage() - 1) * pagebean.getPagesize();
			size = pagebean.getPagesize();
		}
		List<T> list = this.getObjectsBySql(sql.toString(), classObj, start, size, params);
		return list;
	}

	public List<Map<String, Object>> getPageBeanBySql(String query, String condition, String sort, PageBean pagebean) {
		StringBuilder sql = new StringBuilder();
		sql.append(query).append(" ").append(condition).append(" ");
		if (sort != null) {
			sql.append(sort);
		} else if (pagebean != null) {
			sql.append("ORDER BY ").append(pagebean.getSortfeild());
			switch (pagebean.getSorttype()) {
			case 0:
				sql.append(" DESC");
				break;
			case 1:
				sql.append(" ASC");
				break;
			}
		} else {
			sql.append("ORDER BY id DESC");
		}
		// 查询起始行数
		Integer start = null;
		// 查询数据条数
		Integer size = null;
		if (pagebean != null) {
			// 根据条件修改pagebean
			this.updatePageBean(condition, pagebean);
			start = (pagebean.getCurpage() - 1) * pagebean.getPagesize();
			size = pagebean.getPagesize();
		}
		List<Map<String, Object>> list = this.getMapOfObjectsBySql(sql.toString(), start, size, null);
		return list;
	}

	public List<Map<String, Object>> getPageBeanBySql(String query, String condition, String sort, PageBean pagebean,
			List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append(query).append(" ").append(condition).append(" ");
		if (sort != null) {
			sql.append(sort);
		} else if (pagebean != null) {
			sql.append("ORDER BY ").append(pagebean.getSortfeild());
			switch (pagebean.getSorttype()) {
			case 0:
				sql.append(" DESC");
				break;
			case 1:
				sql.append(" ASC");
				break;
			}
		} else {
			sql.append("ORDER BY id DESC");
		}
		// 查询起始行数
		Integer start = null;
		// 查询数据条数
		Integer size = null;
		if (pagebean != null) {
			// 根据条件修改pagebean
			this.updatePageBean(condition, pagebean, params);
			start = (pagebean.getCurpage() - 1) * pagebean.getPagesize();
			size = pagebean.getPagesize();
		}
		List<Map<String, Object>> list = this.getMapOfObjectsBySql(sql.toString(), start, size, params);
		return list;
	}

	/**
	 * 修改pagebean参数
	 * 
	 * @param condition
	 * @param pagebean
	 */
	protected void updatePageBean(String condition, PageBean pagebean) {
		if (pagebean.getIsnewcomp() == 1) {
			StringBuilder count = new StringBuilder();
			count.append("select count(*)").append(" ").append(condition);

			int totalRows = this.countNumBySql(count.toString(), null);
			pagebean.setTotalNum(totalRows);
		}
		pagebean.updatePage();
	}

	/**
	 * 修改pagebean参数
	 * 
	 * @param condition
	 * @param pagebean
	 */
	protected void updatePageBean(String condition, PageBean pagebean, List<Object> params) {
		if (pagebean.getIsnewcomp() == 1) {
			StringBuilder count = new StringBuilder();
			count.append("select count(*)").append(" ").append(condition);

			int totalRows = this.countNumBySql(count.toString(), params);
			pagebean.setTotalNum(totalRows);
		}
		pagebean.updatePage();
	}
}
