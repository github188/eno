package com.energicube.eno.common.jsonquery.jpa.response;

import java.io.Serializable;
import java.util.List;

/**
 * Easyui Grid Response
 * 
 * @author CHENPING
 * */
public class EasyuigridResponse<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = -8913649816188324082L;

	private Long total;

	private List<T> rows;

	public EasyuigridResponse() {

	}

	public EasyuigridResponse(Long total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "EasyuigridResponse [total=" + total + ", rows=" + rows + "]";
	}

}
