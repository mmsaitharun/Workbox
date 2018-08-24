package com.agco.workbox.services.dto;

import java.util.List;

public class WorkBoxActionListDto {

	private List<WorkBoxActionDto> taskInstanceList;

	@Override
	public String toString() {
		return "WorkBoxActionListDto [taskInstanceList=" + taskInstanceList + "]";
	}

	public List<WorkBoxActionDto> getTaskInstanceList() {
		return taskInstanceList;
	}

	public void setTaskInstanceList(List<WorkBoxActionDto> taskInstanceList) {
		this.taskInstanceList = taskInstanceList;
	}	
	
}
