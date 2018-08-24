package com.agco.workbox.services.response.dto;

import java.util.List;

import com.agco.workbox.services.dto.ResponseMessage;
import com.agco.workbox.services.dto.WorkloadRangeDto;

public class WorkloadRangeResponse {

	private List<WorkloadRangeDto> workloadRangeDtos;
	private ResponseMessage responseMessage;

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

	public List<WorkloadRangeDto> getWorkloadRangeDtos() {
		return workloadRangeDtos;
	}

	public void setWorkloadRangeDtos(List<WorkloadRangeDto> workloadRangeDtos) {
		this.workloadRangeDtos = workloadRangeDtos;
	}

	@Override
	public String toString() {
		return "WorkloadRangeResponse [workloadRangeDtos=" + workloadRangeDtos + "]";
	}

}
