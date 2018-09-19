package com.workbox.services.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.workbox.services.dto.ReportPayload;
import com.workbox.util.common.dto.ManageTasksRequestDto;
import com.workbox.util.common.dto.PMCReportBaseDto;
import com.workbox.util.component.ServicesUtil;
import com.workbox.util.dao.TaskEventsDao;

/**
 * @author Saurabh
 *
 */
public class TaskManagerReport implements Report {


	private static final Logger logger=LoggerFactory.getLogger(TaskManagerReport.class);

	@Override
	public PMCReportBaseDto getData(ReportPayload payload,Object dao) {
		logger.error("[PMC] REPORT - TaskManagerReport  - getData() - Started with ReportPayload - " + payload);

		TaskEventsDao taskEventsDao=(TaskEventsDao) dao;
		if (!ServicesUtil.isEmpty(payload)) {
			return taskEventsDao.getTasksByUserAndDuration(new ManageTasksRequestDto(payload.getUserId(), payload.getStatus(), payload.getProcessName(), payload.getStartDayFrom(), payload.getStartDayTo(),
					payload.getPage(), payload.getRequestId(), payload.getLabelValue()));
		}
		return null;
	}

}
