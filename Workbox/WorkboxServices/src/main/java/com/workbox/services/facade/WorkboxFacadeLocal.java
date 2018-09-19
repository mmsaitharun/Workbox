package com.workbox.services.facade;

import com.workbox.services.response.dto.TrackingResponse;
import com.workbox.util.common.dto.WorkboxResponseDto;

public interface WorkboxFacadeLocal {

	public WorkboxResponseDto getWorkboxFilterData(String processName, String requestId, String createdBy,
			String createdAt, String status, Integer skipCount, Integer maxCount, Integer page, String orderBy,
			String orderType);

	public WorkboxResponseDto getWorkboxCompletedFilterData(String processName, String requestId, String createdBy,
			String createdAt, String completedAt, String period, Integer skipCount, Integer maxCount, Integer page);

	public TrackingResponse getTrackingResults();

	public String sayHello();

}
