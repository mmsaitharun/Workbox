package com.workbox.services.dto;

import com.workbox.util.component.EnOperation;
import com.workbox.util.component.InvalidInputFault;

public class RequestIdListDto extends BaseDto{

	private String eventId;
	private String requestId;

	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	@Override
	public String toString() {
		return "RequestIdListDto [eventId=" + eventId + ", requestId=" + requestId + "]";
	}
	@Override
	public Boolean getValidForUsage() {
		return null;
	}
	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		
	}
	
	
}
