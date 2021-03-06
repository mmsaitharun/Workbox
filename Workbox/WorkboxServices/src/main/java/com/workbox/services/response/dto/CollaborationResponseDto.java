package com.workbox.services.response.dto;

import java.util.List;

import com.workbox.services.dto.CollaborationMessagesDto;
import com.workbox.util.common.dto.ResponseMessage;

public class CollaborationResponseDto {
	
	private ResponseMessage responseMessage;
	private List<CollaborationMessagesDto> responseDtos;
	
	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	public List<CollaborationMessagesDto> getResponseDtos() {
		return responseDtos;
	}
	public void setResponseDtos(List<CollaborationMessagesDto> responseDtos) {
		this.responseDtos = responseDtos;
	}
	@Override
	public String toString() {
		return "CollaborationMessageDto [responseMessage=" + responseMessage + ", responseDtos=" + responseDtos + "]";
	}
	
	
	
	
	
}
