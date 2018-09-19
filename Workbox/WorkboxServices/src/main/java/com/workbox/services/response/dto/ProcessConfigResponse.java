package com.workbox.services.response.dto;

import java.util.List;

import com.workbox.services.dto.ProcessConfigDto;
import com.workbox.util.common.dto.ResponseMessage;

public class ProcessConfigResponse {
	
	private List<ProcessConfigDto> processConfigDtos;
	private ResponseMessage responseMessage;

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

	public List<ProcessConfigDto> getProcessConfigDtos() {
		return processConfigDtos;
	}

	public void setProcessConfigDtos(List<ProcessConfigDto> processConfigDtos) {
		this.processConfigDtos = processConfigDtos;
	}

	@Override
	public String toString() {
		return "ProcessConfigResponse [processConfigDtos=" + processConfigDtos + "]";
	}

}
