package com.agco.workbox.services.dto;

import java.math.BigDecimal;

import com.agco.workbox.util.dto.MapResponseListDto;

public class TaskCountDto {
	
	private String userId;
	private MapResponseListDto taskCountDetail;
	private BigDecimal maxCount = new BigDecimal(5);

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(BigDecimal maxCount) {
		this.maxCount = maxCount;
	}

	public MapResponseListDto getTaskCountDetail() {
		return taskCountDetail;
	}

	@Override
	public String toString() {
		return "TaskCountDto [userId=" + userId + ", taskCountDetail=" + taskCountDetail + ", maxCount=" + maxCount
				+ "]";
	}

	public void setTaskCountDetail(MapResponseListDto taskCountDetail) {
		this.taskCountDetail = taskCountDetail;
	}
}
