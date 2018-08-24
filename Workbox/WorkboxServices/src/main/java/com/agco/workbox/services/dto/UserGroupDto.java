package com.agco.workbox.services.dto;

public class UserGroupDto {
	
	private String groupName;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString() {
		return "UserGroupDto [groupName=" + groupName + "]";
	}
	
	
}
