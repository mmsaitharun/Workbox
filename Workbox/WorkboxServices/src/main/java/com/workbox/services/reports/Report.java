package com.workbox.services.reports;

import com.workbox.services.dto.ReportPayload;
import com.workbox.util.common.dto.PMCReportBaseDto;

/**
 * @author Saurabh
 *
 */
public interface Report {

	public PMCReportBaseDto getData(ReportPayload payload,Object dao);

}
