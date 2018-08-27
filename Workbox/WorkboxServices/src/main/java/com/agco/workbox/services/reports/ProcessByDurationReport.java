package com.agco.workbox.services.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agco.workbox.services.dto.ReportPayload;
import com.agco.workbox.util.common.dto.PMCReportBaseDto;
import com.agco.workbox.util.common.dto.ProcessDetailsDto;
import com.agco.workbox.util.component.ServicesUtil;
import com.agco.workbox.util.dao.ProcessEventsDao;

/**
 * @author Saurabh
 *
 */

public class ProcessByDurationReport implements Report {

	private static final Logger logger = LoggerFactory.getLogger(ProcessByDurationReport.class);


	@Override
	public PMCReportBaseDto getData(ReportPayload payload,Object dao) {
		logger.error("[PMC] REPORT - ProcessByDurationReport  - getData() - Started with ReportPayload - " + payload);
		ProcessEventsDao processEventsDao=(ProcessEventsDao) dao;
		if (!ServicesUtil.isEmpty(payload)) {
			return processEventsDao.getProcessByDuration(new ProcessDetailsDto(payload.getProcessName(), payload.getStartDayFrom(),
					payload.getStartDayTo(), payload.getPage()));
		}
		return null;
	}

}
