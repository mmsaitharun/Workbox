package com.workbox.util.common.dto;

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
