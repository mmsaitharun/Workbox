package com.agco.workbox.util.component;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtil {

	private final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		try {
			return sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			logger.error("[Workbox][HibernateUtil][getSession][error] " + e.getMessage());
			return sessionFactory.openSession();
		}
	}
}
