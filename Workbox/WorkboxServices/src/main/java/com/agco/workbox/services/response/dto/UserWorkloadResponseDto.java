package com.agco.workbox.services.response.dto;

import java.util.List;

import com.agco.workbox.services.dto.UserWorkloadDto;
import com.agco.workbox.util.common.dto.ResponseMessage;

public class UserWorkloadResponseDto {

	private List<UserWorkloadDto> userWorkloadDtos;
	private ResponseMessage message;

	public List<UserWorkloadDto> getUserWorkloadDtos() {
		return userWorkloadDtos;
	}

	public void setUserWorkloadDtos(List<UserWorkloadDto> userWorkloadDtos) {
		this.userWorkloadDtos = userWorkloadDtos;
	}

	public ResponseMessage getMessage() {
		return message;
	}

	public void setMessage(ResponseMessage message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "UserWorkloadResponseDto [userWorkloadDtos=" + userWorkloadDtos + ", message=" + message + "]";
	}

}
