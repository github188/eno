package com.energicube.eno.common.model;

import java.util.List;

public class JeasyuiGirdData<T> {

	private long total; // 总记录数

	/** The actual data */
	private List<T> rows;

	public JeasyuiGirdData(long total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public List<T> getRows() {
		return rows;
	}

}
