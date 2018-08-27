package com.agco.workbox.util.common.dto;

import java.util.List;

import com.agco.workbox.util.dto.MapResponseListDto;

public class AgingResponseDto {
	private List<AgingTableDto> tupleDtos;

	private MapResponseListDto headerMap;

	public void setHeaderMap(MapResponseListDto headerMap) {
		this.headerMap = headerMap;
	}

	public MapResponseListDto getHeaderMap() {
		return headerMap;
	}

	private String status;

	public List<AgingTableDto> getTupleDtos() {
		return tupleDtos;
	}

	public void setTupleDtos(List<AgingTableDto> tupleDtos) {
		this.tupleDtos = tupleDtos;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AgingResponseDto [tupleDtos=" + tupleDtos + ", status=" + status + "]";
	}
}
