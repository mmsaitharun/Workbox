package com.workbox.services.reports;

import org.springframework.stereotype.Component;

import com.workbox.services.dto.DownloadReportResponseDto;
import com.workbox.services.dto.ReportFormattedDto;

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
