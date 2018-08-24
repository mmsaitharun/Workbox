package com.agco.workbox.services.reports;

/**
 * @author Saurabh
 *
 */
public class DownloadReportFactoryGenerator {

	public static ServiceFactory getReportFactory() {
		return new ReportFactory();
	}

	public static ServiceFactory getDownloadFactory() {
		return new FileFactory();
	}
}
