package com.agco.workbox.services.response.dto;

import java.util.List;

import com.agco.workbox.util.common.dto.ManageTasksDto;
import com.agco.workbox.util.common.dto.PMCReportBaseDto;
import com.agco.workbox.util.common.dto.ResponseMessage;

public class ManageTasksResponseDto extends PMCReportBaseDto {

	private List<ManageTasksDto> tasks;
	private ResponseMessage message;

	public List<ManageTasksDto> getTasks() {
		return tasks;
	}

	public void setTasks(List<ManageTasksDto> tasks) {
		this.tasks = tasks;
	}

	public ResponseMessage getMessage() {
		return message;
	}

	public void setMessage(ResponseMessage message) {
		this.message = message;
	}

}
