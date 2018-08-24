//package com.agco.workbox.services.dao;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.UUID;
//
//import org.hibernate.HibernateException;
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import com.agco.workbox.services.dto.BaseDto;
//import com.agco.workbox.services.entity.BaseDo;
//import com.agco.workbox.services.entity.ProcessEventsDo;
//import com.agco.workbox.util.component.EnOperation;
//import com.agco.workbox.util.component.ExecutionFault;
//import com.agco.workbox.util.component.InvalidInputFault;
//import com.agco.workbox.util.component.NoResultFault;
//import com.agco.workbox.util.component.RecordExistFault;
//import com.agco.workbox.util.component.ServicesUtil;
//import com.agco.workbox.util.dto.MessageUIDto;
//
///**
// * The <code>BaseDao</code> abstract class comprise abstract functions for CRUD
// * operations and a few utility functions for child <code>Data Access
// * Objects<code>
// * 
// * @version 2, 21-June-2012
// * @since CR8313
// */
//@Repository("baseDao")
//public abstract class BaseDao {
//
//	private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);
//
//	@Autowired
//	private SessionFactory sessionFactory;
//
//	private Session session;
//
//	public Session getSession() {
//		try {
//			return sessionFactory.getCurrentSession();
//		} catch (HibernateException e) {
//			logger.error("[Workbox][BaseDao][session][error] " + e.getMessage());
//			return sessionFactory.openSession();
//		}
//	}
//
//	public void create(BaseDo object) {
//		session.save(object);
//	}
//	
//	public void saveOrUpdate(BaseDo object) {
//		session.saveOrUpdate(object);
//	}
//
//	public Object getByKeys(BaseDo object) {
//		Object primaryKey = object.getPrimaryKey();
//		if(primaryKey instanceof java.lang.String) {
//			return session.get(object.getClass(), primaryKey.toString());
//		} else if (primaryKey instanceof java.lang.Integer) {
//			return session.get(object.getClass(), (Integer) primaryKey);
//		} else if (primaryKey instanceof java.lang.Double) {
//			return session.get(object.getClass(), (Double) primaryKey);
//		}
//		return object;
//	}
//
//	/**
//	 * @return the entity, mainly used for setting FK
//	 */
//	public E getByKeysForFK(D dto) throws ExecutionFault, InvalidInputFault, NoResultFault {
//		return find(importDto(EnOperation.RETRIEVE, dto));
//	}
//
//	/**
//	 * @param dto
//	 *            the record to be updated
//	 * @return the updated record
//	 * @throws ExecutionFault
//	 *             in case for fatal error
//	 * @throws InvalidInputFault
//	 *             wrong inputs
//	 * @throws NoResultFault
//	 */
//	public void update(D dto) throws ExecutionFault, InvalidInputFault, NoResultFault {
//		getByKeysForFK(dto);
//		merge(importDto(EnOperation.UPDATE, dto));
//	}
//
//	public void delete(D dto) throws ExecutionFault, InvalidInputFault, NoResultFault {
//		remove(getByKeysForFK(dto));
//	}
//
//	// </WRAPPER OVER BASIC CRUD ONES, WITH IMPORT AND EXPORT FUNCTIONS>
//
//	// <BASIC CRUD OPERATIONS>
//	protected void persist(E pojo) throws ExecutionFault {
//		try {
//			getSession().persist(pojo);
//		} catch (Exception e) {
//			MessageUIDto faultInfo = new MessageUIDto();
//			logger.error(e.getMessage());
//			faultInfo.setMessage(e.getMessage());
//			String message = "Create of " + pojo.getClass().getSimpleName() + " with keys " + pojo.getPrimaryKey()
//					+ " failed!";
//			throw new ExecutionFault(message, faultInfo, e);
//		}
//	}
//
//	protected void merge(E pojo) throws ExecutionFault {
//		try {
//			getSession().clear();
//			// getSession().update(pojo);
//			getSession().update(pojo);
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			MessageUIDto faultInfo = new MessageUIDto();
//			faultInfo.setMessage(e.getMessage());
//			String message = "Update of " + pojo.getClass().getSimpleName() + " having keys " + pojo.getPrimaryKey()
//					+ " failed!";
//			throw new ExecutionFault(message, faultInfo, e);
//		}
//	}
//
//	protected void saveOrUpdate(E pojo) throws ExecutionFault {
//		try {
//			getSession().clear();
//			getSession().saveOrUpdate(pojo);
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			MessageUIDto faultInfo = new MessageUIDto();
//			faultInfo.setMessage(e.getMessage());
//			String message = "Save Or Update of " + pojo.getClass().getSimpleName() + " having keys "
//					+ pojo.getPrimaryKey() + " failed!";
//			throw new ExecutionFault(message, faultInfo, e);
//		}
//	}
//
//	protected void remove(E pojo) throws ExecutionFault {
//		try {
//			getSession().delete(pojo);
//		} catch (Exception e) {
//			MessageUIDto faultInfo = new MessageUIDto();
//			faultInfo.setMessage(e.getMessage());
//			String message = "Delete of " + pojo.getClass().getSimpleName() + " having keys " + pojo.getPrimaryKey()
//					+ " failed!";
//			throw new ExecutionFault(message, faultInfo, e);
//		}
//	}
//
//	// </BASIC CRUD OPERATIONS>
//	// <SIGNATURE FOR DATA TRANSFER FUNCTIONS>
//	private E importDto(EnOperation operation, D fromDto) throws InvalidInputFault, ExecutionFault, NoResultFault {
//		if (fromDto != null) {
//			fromDto.validate(operation);
//			return importDto(fromDto);
//		}
//		throw new InvalidInputFault("Empty DTO passed");
//	}
//
//	/**
//	 * @param fromDto
//	 *            Data object from which data needs to be copied to a new entity
//	 */
//	protected abstract E importDto(D fromDto) throws InvalidInputFault, ExecutionFault, NoResultFault;
//
//	// /**
//	// * To be used this if copy over existing entity is needed
//	// */
//	// protected abstract E importDto(D fromDto, E toEntity)
//	// throws InvalidInputFault, ExecutionFault;
//
//	/**
//	 * @param entity
//	 *            Copies data back to a new transfer object from entity
//	 */
//	protected abstract D exportDto(E entity);
//
//	protected List<D> exportDtoList(Collection<E> listDo) {
//		List<D> returnDtos = null;
//		if (!ServicesUtil.isEmpty(listDo)) {
//			returnDtos = new ArrayList<D>(listDo.size());
//			for (Iterator<E> iterator = listDo.iterator(); iterator.hasNext();) {
//				returnDtos.add(exportDto(iterator.next()));
//			}
//		}
//		return returnDtos;
//	}
//
//	/**
//	 * Its negation logic over getByKeys.
//	 * 
//	 * @param dto
//	 * @throws ExecutionFault
//	 * @throws RecordExistFault
//	 * @throws InvalidInputFault
//	 */
//	protected void entityExist(D dto) throws ExecutionFault, RecordExistFault, InvalidInputFault {
//		try {// Report entity exist
//			getByKeys(dto);
//			throw new RecordExistFault(recordExist, buildRecordExistFault(dto));
//		} catch (NoResultFault e) {
//		}
//	}
//
//	// </SIGNATURE FOR DATA TRANSFER FUNCTIONS>
//
//	/**
//	 * 
//	 * @param queryName
//	 *            used for logging
//	 * 
//	 * @param query
//	 *            object used for execution
//	 * @param parameters
//	 *            to be set in where clause
//	 * @return Single record, depending on columns in SELECT clause, it return a
//	 *         object of BaseDo type or an object array
//	 * @throws NoResultFault
//	 * @throws NonUniqueRecordFault
//	 */
//
//	/**
//	 * @param queryName
//	 *            used for logging
//	 * @param query
//	 *            object used for execution
//	 * @param parameters
//	 *            to be set in where clause
//	 * @return List of records based on startIndex and batchIndex
//	 * @throws NoResultFault
//	 */
//	protected List<?> getResultList(String queryName, Query query, Object... parameters) throws NoResultFault {
//		List<?> returnList = query.list();
//		if (ServicesUtil.isEmpty(returnList)) {
//			throw new NoResultFault(ServicesUtil.buildNoRecordMessage(queryName, parameters));
//		}
//		return returnList;
//	}
//
//	protected String uuidGen(BaseDto dto, Class<? extends BaseDo> classDo) throws ExecutionFault {
//		return UUID.randomUUID().toString();
//	}
//
//	private MessageUIDto buildRecordExistFault(BaseDto BaseDto) {
//		StringBuffer sb = new StringBuffer(recordExist);
//		if (BaseDto != null) {
//			// sb.append(BaseDto.getClass().getSimpleName());
//			// sb.append("#[");
//			sb.append(BaseDto.toString());
//			// sb.append(']');
//		}
//		MessageUIDto messageUIDto = new MessageUIDto();
//		messageUIDto.setMessage(sb.toString());
//		return messageUIDto;
//	}
//}