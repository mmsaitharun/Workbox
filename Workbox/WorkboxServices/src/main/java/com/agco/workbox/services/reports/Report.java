package com.agco.workbox.services.reports;

import com.agco.workbox.services.dto.PMCReportBaseDto;
import com.agco.workbox.services.dto.ReportPayload;

/**
 * @author Saurabh
 *
 */
public interface Report {

	public PMCReportBaseDto getData(ReportPayload payload,Object dao);

}
