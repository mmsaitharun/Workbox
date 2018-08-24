package com.agco.workbox.services.dto;

import com.agco.workbox.util.component.EnOperation;
import com.agco.workbox.util.component.InvalidInputFault;

public class ProcessStepsDto extends BaseDto {

	private Integer processId;

	private Integer taskStep;

	private String processName;

	private String activityType;

	private String subject;

	private String description;

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public Integer getTaskStep() {
		return taskStep;
	}

	public void setTaskStep(Integer taskStep) {
		this.taskStep = taskStep;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ProcessStepsDto [processId=" + processId + ", taskStep=" + taskStep + ", processName=" + processName + ", activityType=" + activityType + ", subject=" + subject + ", description="
				+ description + "]";
	}

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {

	}

}