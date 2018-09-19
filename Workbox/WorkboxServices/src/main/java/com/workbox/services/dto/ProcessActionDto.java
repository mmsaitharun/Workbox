package com.workbox.services.dto;

import java.util.List;

public class ProcessActionDto {

	private List<String> processInstanceList;


	@Override
	public String toString() {
		return "ProcessActionDto [processInstanceList=" + processInstanceList + "]";
	}


	public List<String> getProcessInstanceList() {
		return processInstanceList;
	}


	public void setProcessInstanceList(List<String> processInstanceList) {
		this.processInstanceList = processInstanceList;
	}
	
	
}
