package com.agco.workbox.services.reports;

import org.springframework.stereotype.Component;

import com.agco.workbox.services.dto.DownloadReportResponseDto;
import com.agco.workbox.services.dto.ReportFormattedDto;

/**
 * @author Saurabh
 *
 */
@Component("PDF")
public class PDF extends File{

	@Override
	public DownloadReportResponseDto pushData(ReportFormattedDto reportFormattedDto) {
		return null;
	}
}
