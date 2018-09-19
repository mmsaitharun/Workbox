package com.workbox.services.response.dto;

import java.util.List;

import com.workbox.util.common.dto.ResponseMessage;
import com.workbox.util.dto.RoleInfoDto;

public class RoleInfoResponse {

	private List<RoleInfoDto> roleInfoDtos;
	private ResponseMessage responseMessage;

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}


	public List<RoleInfoDto> getRoleInfoDtos() {
		return roleInfoDtos;
	}

	public void setRoleInfoDtos(List<RoleInfoDto> roleInfoDtos) {
		this.roleInfoDtos = roleInfoDtos;
	}

	@Override
	public String toString() {
		return "RoleInfoResponse [roleInfoDtos=" + roleInfoDtos + ", responseMessage=" + responseMessage + "]";
	}

}
