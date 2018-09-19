package com.workbox.util.dto;

import com.workbox.util.component.FaultStatus;

public class ExecutionStatusDto {

	private FaultStatus faultStatus;
	private String faultMessage;

	public FaultStatus getFaultStatus() {
		return faultStatus;
	}

	public void setFaultStatus(FaultStatus faultStatus) {
		this.faultStatus = faultStatus;
	}

	public String getFaultMessage() {
		return faultMessage;
	}

	public void setFaultMessage(String faultMessage) {
		this.faultMessage = faultMessage;
	}

}
