package com.workbox.services.reports;

/**
 * @author Saurabh
 *
 */
public interface ServiceFactory {

	Report getReport(String reportName);

	File getFile(String fileFormate);
}
