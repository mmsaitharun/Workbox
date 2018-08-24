package com.agco.workbox.services.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agco.workbox.services.dao.ProcessEventsDao;
import com.agco.workbox.services.dto.PMCReportBaseDto;
import com.agco.workbox.services.dto.ReportPayload;
import com.agco.workbox.services.dto.UserProcessDetailRequestDto;
import com.agco.workbox.util.component.ServicesUtil;

/**
 * @author Saurabh
 *
 */
public class UserWorkloadReport implements Report {
	
	private static final Logger logger=LoggerFactory.getLogger(UserWorkloadReport.class);
	

	
	
	
	@Override
	public PMCReportBaseDto getData(ReportPayload payload,Object dao){
		logger.error("[PMC] REPORT - UserWorkloadReport  - getData() - Started with ReportPayload - " + payload);
		if (!ServicesUtil.isEmpty(payload)){
			
			ProcessEventsDao processEventsDao=(ProcessEventsDao)dao;		
			UserProcessDetailRequestDto requestDto = this.exportToUserProcessDetailRequestDto(payload);			
			return processEventsDao.getProcessesByTaskOwner(requestDto);
		}
		return null;
	}

	private UserProcessDetailRequestDto exportToUserProcessDetailRequestDto(ReportPayload payload) {
		logger.error("[PMC] REPORT - UserWorkloadReport  - exportToUserProcessDetailRequestDto() - Started with ReportPayload - " + payload);
		UserProcessDetailRequestDto dto = new UserProcessDetailRequestDto();
		dto.setGraphType(ServicesUtil.isEmpty(payload.getGraphType()) ? null : payload.getGraphType());
		dto.setLabelValue(ServicesUtil.isEmpty(payload.getLabelValue()) ? null : payload.getLabelValue());
		dto.setProcessName(ServicesUtil.isEmpty(payload.getProcessName()) ? null : payload.getProcessName());
		dto.setRequestId(ServicesUtil.isEmpty(payload.getRequestId()) ? null : payload.getRequestId());
		dto.setStatus(ServicesUtil.isEmpty(payload.getStatus()) ? null : payload.getStatus());
		dto.setUserId(ServicesUtil.isEmpty(payload.getUserId()) ? null : payload.getUserId());
		dto.setPage(ServicesUtil.isEmpty(payload.getPage()) ? null : payload.getPage());
		logger.error("[PMC] REPORT - UserWorkloadReport  - exportToUserProcessDetailRequestDto() - Ended with UserProcessDetailRequestDto - " + dto);
		return dto;
	}

}
