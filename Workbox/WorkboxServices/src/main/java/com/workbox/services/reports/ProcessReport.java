package com.workbox.services.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.workbox.services.dto.ReportPayload;
import com.workbox.util.common.dto.PMCReportBaseDto;
import com.workbox.util.component.ServicesUtil;
import com.workbox.util.dao.ProcessEventsDao;

/**
 * @author Saurabh
 *
 */
public class ProcessReport implements Report {

	
	private static final Logger logger = LoggerFactory.getLogger(ProcessReport.class);

	@Override
	public PMCReportBaseDto getData(ReportPayload payload,Object dao) {
		logger.error("[PMC] REPORT - ProcessReport  - getData() - Started with ReportPayload - " + payload);
		ProcessEventsDao processEventsDao=(ProcessEventsDao) dao;
		if (!ServicesUtil.isEmpty(payload)) {
			return processEventsDao.getProcessAgeing(payload.getGraphType(), payload.getProcessName(), payload.getReportAgingDtos());
		}
		return null;
	}

}
