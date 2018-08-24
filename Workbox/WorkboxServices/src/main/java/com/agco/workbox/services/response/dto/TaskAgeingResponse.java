package com.agco.workbox.services.response.dto;

import com.agco.workbox.services.dto.AgingResponseDto;
import com.agco.workbox.services.dto.PMCReportBaseDto;
import com.agco.workbox.services.dto.ResponseMessage;

public class TaskAgeingResponse extends PMCReportBaseDto{

	private ResponseMessage responseMessage;
	private AgingResponseDto ageingTable;

	public AgingResponseDto getAgeingTable() {
		return ageingTable;
	}

	public void setAgeingTable(AgingResponseDto ageingTable) {
		this.ageingTable = ageingTable;
	}

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

}
