package com.agco.workbox.services.response.dto;

import java.util.List;

import com.agco.workbox.util.common.dto.ResponseMessage;
import com.agco.workbox.util.common.dto.UserDetailsDto;

public class UserDetailsResponse {

	private List<UserDetailsDto> userDetailsDtos;
	private ResponseMessage responseMessage;

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

	public List<UserDetailsDto> getUserDetailsDtos() {
		return userDetailsDtos;
	}

	public void setUserDetailsDtos(List<UserDetailsDto> userDetailsDtos) {
		this.userDetailsDtos = userDetailsDtos;
	}

	@Override
	public String toString() {
		return "UserDetailsResponse [userDetailsDtos=" + userDetailsDtos + ", responseMessage=" + responseMessage + "]";
	}

}
