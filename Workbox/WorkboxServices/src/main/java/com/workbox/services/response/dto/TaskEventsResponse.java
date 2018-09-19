package com.workbox.services.response.dto;

import java.util.List;

import com.workbox.util.common.dto.ResponseMessage;
import com.workbox.util.common.dto.TaskEventsDto;

public class TaskEventsResponse {
	
	private List<TaskEventsDto> taskEventDtos;
	private ResponseMessage responseMessage;

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage2) {
		this.responseMessage = responseMessage2;
	}

	public List<TaskEventsDto> getTaskEventDtos() {
		return taskEventDtos;
	}

	public void setTaskEventDtos(List<TaskEventsDto> taskEventDtos) {
		this.taskEventDtos = taskEventDtos;
	}

	@Override
	public String toString() {
		return "TaskEventsResponse [taskEventDtos=" + taskEventDtos + "]";
	}

}
