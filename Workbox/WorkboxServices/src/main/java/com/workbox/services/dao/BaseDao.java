package com.workbox.services.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDao<T extends Serializable> {

	private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);

	private Class<T> clazz;

	@Autowired
	private SessionFactory sessionFactory;

	public final void setClazz(Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	protected final Session getSession() {
		try {
			return sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			logger.error("[Workbox][BaseDao][session][error] " + e.getMessage());
			return sessionFactory.openSession();
		}
	}

	@SuppressWarnings("unchecked")
	public T findOne(Serializable id) {
		return (T) getSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return getSession().createQuery("from " + clazz.getName()).list();
	}

	public void create(T entity) {
		getSession().persist(entity);
	}

	public void createOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}

	public void update(T entity) {
		getSession().merge(entity);
	}

	public void delete(T entity) {
		getSession().delete(entity);
	}

	public void deleteById(Serializable entityId) {
		T entity = findOne(entityId);
		delete(entity);
	}
}