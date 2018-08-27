package com.agco.workbox.services.reports;

import com.agco.workbox.services.dto.ReportPayload;
import com.agco.workbox.util.common.dto.PMCReportBaseDto;

/**
 * @author Saurabh
 *
 */
public interface Report {

	public PMCReportBaseDto getData(ReportPayload payload,Object dao);

}
