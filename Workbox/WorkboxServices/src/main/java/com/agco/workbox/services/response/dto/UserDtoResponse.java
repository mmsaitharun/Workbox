package com.agco.workbox.services.response.dto;

import java.util.List;

import com.agco.workbox.services.dto.ResponseMessage;
import com.agco.workbox.services.dto.UserDto;

public class UserDtoResponse {

	private List<UserDto> userDtos;
	private ResponseMessage responseMessage;

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

	public List<UserDto> getUserDtos() {
		return userDtos;
	}

	public void setUserDtos(List<UserDto> userDtos) {
		this.userDtos = userDtos;
	}

	@Override
	public String toString() {
		return "UserDtoResponse [userDtos=" + userDtos + ", responseMessage=" + responseMessage + "]";
	}
	
}
