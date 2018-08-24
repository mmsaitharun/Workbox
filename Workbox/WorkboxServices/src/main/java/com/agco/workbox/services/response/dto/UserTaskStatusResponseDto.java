package com.agco.workbox.services.response.dto;

import com.agco.workbox.services.dto.ResponseMessage;
import com.agco.workbox.util.dto.MapResponseTaskListDto;

public class UserTaskStatusResponseDto {
	
	private MapResponseTaskListDto taskCountDetail;
	private ResponseMessage responseMessage;


	public MapResponseTaskListDto getTaskCountDetail() {
		return taskCountDetail;
	}

	public void setTaskCountDetail(MapResponseTaskListDto taskCountDetail) {
		this.taskCountDetail = taskCountDetail;
	}

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

	@Override
	public String toString() {
		return "UserTaskStatusResponseDto [taskCountDetail=" + taskCountDetail + ", responseMessage=" + responseMessage
				+ "]";
	}
}