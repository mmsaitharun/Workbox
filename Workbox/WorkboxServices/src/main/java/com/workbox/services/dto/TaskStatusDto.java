package com.workbox.services.dto;

import java.math.BigDecimal;

public class TaskStatusDto {
	private BigDecimal open = new BigDecimal(0);
	private BigDecimal closed = new BigDecimal(0);

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getClosed() {
		return closed;
	}

	public void setClosed(BigDecimal closed) {
		this.closed = closed;
	}

	@Override
	public String toString() {
		return "TaskStatusDto [open=" + open + ", closed=" + closed + "]";
	}

}
