package com.agco.workbox.services.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agco.workbox.services.dao.ProcessEventsDao;
import com.agco.workbox.services.dao.TaskEventsDao;
import com.agco.workbox.services.dao.TaskOwnersDao;
import com.agco.workbox.util.component.PMCConstant;
import com.agco.workbox.util.component.ServicesUtil;

@Component("ReportObjectFactory")
public class ReportObjectFactory {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ReportObjectFactory.class);
	
	@Autowired
	private ProcessEventsDao processEventsDao;
 
	@Autowired
	private TaskEventsDao taskEventsDao;
	
	@Autowired
	private TaskOwnersDao taskOwnersDao;
	
	public Object getReportObject(String reportName) {
		
		logger.error("[PMC] REPORT - ReportObjectFactory  - getReportObject() - Started with reportName - " + reportName);
		
		if (!ServicesUtil.isEmpty(reportName)) {
			if (PMCConstant.USER_TASK_REPORT.equalsIgnoreCase(reportName.trim())) {
				return processEventsDao;
				
			} else if (PMCConstant.PROCESS_TRACKER.equalsIgnoreCase(reportName.trim())) {
				return processEventsDao;
				
			} else if (PMCConstant.TASK_AEGING.equalsIgnoreCase(reportName.trim())) {
				return taskOwnersDao;
				
			} else if (PMCConstant.PROCESS_BY_DURATION.equalsIgnoreCase(reportName.trim())) {
				return processEventsDao;
				
			} else if (PMCConstant.TASK_MANAGER.equalsIgnoreCase(reportName.trim())) {
				return taskEventsDao;
			}
		}
		logger.error("No Report Found");
		return null;
	}

}
