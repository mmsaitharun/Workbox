package com.agco.workbox.services.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agco.workbox.services.dto.ReportPayload;
import com.agco.workbox.util.common.dto.PMCReportBaseDto;
import com.agco.workbox.util.component.ServicesUtil;
import com.agco.workbox.util.dao.TaskOwnersDao;

/**
 * @author Saurabh
 *
 */
public class TaskAegingReport implements Report {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskAegingReport.class);	

	@Override
	public PMCReportBaseDto getData(ReportPayload payload,Object dao) {
		logger.error("[PMC] REPORT - TaskAegingReport  - getData() - Started with ReportPayload - " + payload);
		TaskOwnersDao taskOwnersDao=(TaskOwnersDao) dao;
		if (!ServicesUtil.isEmpty(payload)) {
			return taskOwnersDao.getTaskAgeing(payload.getProcessName(), payload.getUsersList(), payload.getStatus(),payload.getRequestId(), payload.getLabelValue());
		}
		return null;
	}
}
