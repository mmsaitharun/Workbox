package com.workbox.util.dto;

import java.util.List;

public class MapResponseTaskListDto {

	private List<MapResponseTaskDto> entry;

	public List<MapResponseTaskDto> getEntry() {
		return entry;
	}

	public void setEntry(List<MapResponseTaskDto> entry) {
		this.entry = entry;
	}
	
	@Override
	public String toString() {
		return "MapResponseTaskListDto [entry=" + entry + "]";
	}
}