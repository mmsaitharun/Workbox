package com.workbox.services.response.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.workbox.services.dto.TasksCountDTO;
import com.workbox.util.common.dto.ResponseMessage;

@XmlRootElement
public class TrackingResponse {

	private List<TasksCountDTO> tasksCount;
	private ResponseMessage responseMessage;

	public List<TasksCountDTO> getTasksCount() {
		return tasksCount;
	}

	public void setTasksCount(List<TasksCountDTO> tasksCount) {
		this.tasksCount = tasksCount;
	}

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

}
