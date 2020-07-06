package com.peiqi.hibernate.dao.impl;

import java.sql.Connection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("readAndWriteObjectDao")
@Scope("prototype")
@Transactional(propagation = Propagation.REQUIRED)
public class ReadAndWriteObjectDaoImpl extends AbstractReadAndWriteObjectDao {

	public Session getCurrentSession() {
		return (Session) manager.getDelegate();
	}

	@PersistenceContext
	protected EntityManager manager;

	@Override
	public Connection getConn() {
		Connection conn = manager.unwrap(Connection.class);
		return conn;
	}

}
