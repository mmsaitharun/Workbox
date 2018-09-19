package com.workbox.services.response.dto;

import java.util.List;

import com.workbox.util.common.dto.ResponseMessage;
import com.workbox.util.common.dto.TaskEventsDto;

public class TaskDetailsResponse {

	
	private List<TaskEventsDto> taskEventsList;
	private ResponseMessage responseMessage;

	public List<TaskEventsDto> getTaskEventsList() {
		return taskEventsList;
	}

	public void setProcessEventsList(List<TaskEventsDto> taskEventsList) {
		this.taskEventsList = taskEventsList;
	}

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	
}
