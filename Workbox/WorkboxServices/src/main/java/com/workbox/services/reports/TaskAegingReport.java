package com.workbox.services.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.workbox.services.dto.ReportPayload;
import com.workbox.util.common.dto.PMCReportBaseDto;
import com.workbox.util.component.ServicesUtil;
import com.workbox.util.dao.TaskOwnersDao;

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
