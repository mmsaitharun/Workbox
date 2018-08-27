package com.agco.workbox.services.response.dto;

import java.util.List;

import com.agco.workbox.services.dto.CollaborationNotificationDto;
import com.agco.workbox.util.common.dto.ResponseMessage;

public class NotificationResponseDto {

	private ResponseMessage responseMessage;
	private List<CollaborationNotificationDto> responseDto;
	
	public List<CollaborationNotificationDto> getResponseDto() {
		return responseDto;
	}

	public void setResponseDto(List<CollaborationNotificationDto> responseDto) {
		this.responseDto = responseDto;
	}
	
	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	@Override
	public String toString() {
		return "NotificationResponseDto [responseMessage=" + responseMessage + "]";
	}
}
