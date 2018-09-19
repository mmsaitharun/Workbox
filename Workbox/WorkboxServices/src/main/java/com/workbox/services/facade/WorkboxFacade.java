package com.workbox.services.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workbox.services.dao.WorkBoxDao;
import com.workbox.services.response.dto.TrackingResponse;
import com.workbox.util.common.dto.WorkboxResponseDto;

/**
 * Session Bean implementation class WorkboxFacade
 */
@Service("WorkboxFacade")
public class WorkboxFacade implements WorkboxFacadeLocal {

	@Autowired
	private WorkBoxDao workboxdao;

//	@Autowired
//	PersistDataService persistDataService;

	public WorkboxFacade() {
	}

	@Override
	public String sayHello() {
		return "Hello From Spring";
	}

	@Override
	public WorkboxResponseDto getWorkboxFilterData(String processName, String requestId, String createdBy,
			String createdAt, String status, Integer skipCount, Integer maxCount, Integer page, String orderBy,
			String orderType) {
		return workboxdao.getWorkboxFilterData(processName, requestId, createdBy, createdAt, skipCount, maxCount, page,
				orderType, orderBy, status);
	}

	@Override
	public WorkboxResponseDto getWorkboxCompletedFilterData(String processName, String requestId, String createdBy,
			String createdAt, String completedAt, String period, Integer skipCount, Integer maxCount, Integer page) {
		return workboxdao.getWorkboxCompletedFilterData(processName, requestId, createdBy, createdAt, completedAt,
				period, skipCount, maxCount, page);
	}

	@Override
	public TrackingResponse getTrackingResults() {
		return workboxdao.getTrackingResponse();
	}

}
