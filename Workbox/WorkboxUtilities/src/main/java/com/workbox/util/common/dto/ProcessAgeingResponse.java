package com.workbox.util.common.dto;

import java.util.List;

public class ProcessAgeingResponse extends PMCReportBaseDto {

	private List<AgingGraphDto> ageingGraph;

	private AgingResponseDto ageingTable;

	private ResponseMessage responseMessage;

	public List<AgingGraphDto> getAgeingGraph() {
		return ageingGraph;
	}

	public void setAgeingGraph(List<AgingGraphDto> ageingGraph) {
		this.ageingGraph = ageingGraph;
	}

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

	public AgingResponseDto getAgeingTable() {
		return ageingTable;
	}

	public void setAgeingTable(AgingResponseDto ageingTable) {
		this.ageingTable = ageingTable;
	}

}
