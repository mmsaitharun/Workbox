package com.workbox.services.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.workbox.services.entity.ReportAgingDo;
import com.workbox.util.common.dto.ReportAgingDto;
import com.workbox.util.component.NoResultFault;
import com.workbox.util.component.ServicesUtil;

@Repository("ReportAgingDao")
public class ReportAgingDao {

	private static final Logger logger = LoggerFactory.getLogger(ReportAgingDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		try {
			return sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			logger.error("[Workbox][BaseDao][session][error] " + e.getMessage());
			return sessionFactory.openSession();
		}
	}

	protected ReportAgingDo importDto(ReportAgingDto fromDto) {
		ReportAgingDo entity = new ReportAgingDo();
		if (!ServicesUtil.isEmpty(fromDto.getId()))
			entity.setId(fromDto.getId());
		if (!ServicesUtil.isEmpty(fromDto.getReportName()))
			entity.setReportName(fromDto.getReportName());
		if (!ServicesUtil.isEmpty(fromDto.getLowerSegment()))
			entity.setLowerSegment(fromDto.getLowerSegment());
		if (!ServicesUtil.isEmpty(fromDto.getHigherSegment()))
			entity.setHigherSegment(fromDto.getHigherSegment());
		if (!ServicesUtil.isEmpty(fromDto.getDisplayName()))
			entity.setDisplayName(fromDto.getDisplayName());
		if (!ServicesUtil.isEmpty(fromDto.getAgingRange()))
			entity.setAgingRange(fromDto.getAgingRange());
		return entity;
	}

	protected ReportAgingDto exportDto(ReportAgingDo entity) {
		ReportAgingDto reportAgingDto = new ReportAgingDto();
		if (!ServicesUtil.isEmpty(entity.getId()))
			reportAgingDto.setId(entity.getId());
		if (!ServicesUtil.isEmpty(entity.getReportName()))
			reportAgingDto.setReportName(entity.getReportName());
		if (!ServicesUtil.isEmpty(entity.getLowerSegment()))
			reportAgingDto.setLowerSegment(entity.getLowerSegment());
		if (!ServicesUtil.isEmpty(entity.getHigherSegment()))
			reportAgingDto.setHigherSegment(entity.getHigherSegment());
		if (!ServicesUtil.isEmpty(entity.getDisplayName()))
			reportAgingDto.setDisplayName(entity.getDisplayName());
		if (!ServicesUtil.isEmpty(entity.getAgingRange()))
			reportAgingDto.setAgingRange(entity.getAgingRange());
		return reportAgingDto;
	}

	public List<ReportAgingDto> getConfigByReportName(String reportName) {
		List<ReportAgingDto> reportAgingDto = null;
		try {
			reportAgingDto = getSpecificConfigResults("ReportAgingDo", "reportName", reportName);
			for (ReportAgingDto dto : reportAgingDto) {
				logger.error(
						"[pmc][ReportAgingDo][Details]  :  Report Name" + reportName + ",   [dto]" + dto.toString());
			}
		} catch (NoResultFault e) {
			e.printStackTrace();
		}
		return reportAgingDto;

	}

	public List<ReportAgingDto> getAllReportConfiguration() {
		List<ReportAgingDto> reportAgingDto = null;
		try {
			reportAgingDto = getAllReportAegingResults("ReportAgingDo");
		} catch (NoResultFault e) {
			e.printStackTrace();
		}
		return reportAgingDto;
	}

	@SuppressWarnings("unchecked")
	public List<ReportAgingDto> getAllReportAegingResults(String doName, Object... parameters) throws NoResultFault {
		String queryName = "SELECT p FROM " + doName + " p order by p.lowerSegment";
		Query query = getSession().createQuery(queryName);
		List<ReportAgingDo> returnList = query.list();
		if (ServicesUtil.isEmpty(returnList)) {
			throw new NoResultFault(ServicesUtil.buildNoRecordMessage(queryName, parameters));
		}
		logger.error("returnList: " + returnList);
		return exportDtoList(returnList);
	}

	@SuppressWarnings("unchecked")
	public List<ReportAgingDto> getSpecificConfigResults(String doName, String columnName, String value,
			Object... params) throws NoResultFault {
		String queryName = "SELECT p FROM " + doName + " p" + " WHERE p." + columnName
				+ " =:value order by p.lowerSegment";
		logger.error("queryName: " + queryName);
		logger.error("[pmc][ReportAging][details] :" + doName + ",   " + columnName + ",   " + value);
		Query query = getSession().createQuery(queryName);
		query.setParameter("value", value.trim());
		List<ReportAgingDo> returnList = query.list();
		if (ServicesUtil.isEmpty(returnList)) {
			throw new NoResultFault(ServicesUtil.buildNoRecordMessage(queryName, params));
		}
		logger.error("return List Size : " + returnList.size());
		return exportDtoList(returnList);
	}

	protected List<ReportAgingDto> exportDtoList(List<ReportAgingDo> listDo) {
		List<ReportAgingDto> returnDtos = null;
		if (!ServicesUtil.isEmpty(listDo)) {
			returnDtos = new ArrayList<ReportAgingDto>(listDo.size());
			for (Iterator<ReportAgingDo> iterator = listDo.iterator(); iterator.hasNext();) {
				returnDtos.add(exportDto(iterator.next()));
			}
		}
		return returnDtos;
	}

}
