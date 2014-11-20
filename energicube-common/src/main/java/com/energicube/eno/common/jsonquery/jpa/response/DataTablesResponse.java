package com.energicube.eno.common.jsonquery.jpa.response;

import java.io.Serializable;
import java.util.List;

public class DataTablesResponse<T extends Serializable> {
	
	private String sEcho;
	private Long iTotalRecords;
	private Long iTotalDisplayRecords;
	private List<T> aaData;

	public DataTablesResponse() {
	}

	public DataTablesResponse(String sEcho, Long iTotalRecords,
			Long iTotalDisplayRecords, List<T> aaData) {
		super();
		this.sEcho = sEcho;
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords;
		this.aaData = aaData;
	}

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public Long getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(Long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public Long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(Long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public List<T> getAaData() {
		return aaData;
	}

	public void setAaData(List<T> aaData) {
		this.aaData = aaData;
	}

	@Override
	public String toString() {
		return "DataTablesResponse [iTotalDisplayRecords="
				+ iTotalDisplayRecords + ", iTotalRecords=" + iTotalRecords
				+ ", aaData=" + aaData + "]";
	}
}
