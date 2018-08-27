package com.agco.workbox.services.response.dto;

import java.util.List;

import com.agco.workbox.util.common.dto.ResponseMessage;
import com.agco.workbox.util.dto.GroupInfoDto;

public class GroupInfoDtoResponse {

	private List<GroupInfoDto> groupInfoDtos;
	private ResponseMessage responseMessage;

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}


	public List<GroupInfoDto> getGroupInfoDtos() {
		return groupInfoDtos;
	}

	public void setGroupInfoDtos(List<GroupInfoDto> groupInfoDtos) {
		this.groupInfoDtos = groupInfoDtos;
	}

	@Override
	public String toString() {
		return "GroupInfoDtoResponse [groupInfoDtos=" + groupInfoDtos + "]";
	}
	
}
