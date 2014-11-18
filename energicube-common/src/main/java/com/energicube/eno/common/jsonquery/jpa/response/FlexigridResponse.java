package com.energicube.eno.common.jsonquery.jpa.response;

import java.io.Serializable;
import java.util.List;

public class FlexigridResponse<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = -8761926499846638213L;
	private Integer page;
	private Integer total;
	private List<FlexigridCell<T>> rows;

	public FlexigridResponse() {
	}

	public FlexigridResponse(Integer page, Integer total,
			List<FlexigridCell<T>> rows) {
		super();
		this.page = page;
		this.total = total;
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<FlexigridCell<T>> getRows() {
		return rows;
	}

	public void setRows(List<FlexigridCell<T>> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "DataTablesResponse [page=" + page + ", total=" + total
				+ ", rows=" + rows + "]";
	}

	public static class FlexigridCell<T extends Serializable> {

		private Long id;
		private T cell;

		public FlexigridCell() {
		}

		public FlexigridCell(Long id, T cell) {
			super();
			this.id = id;
			this.cell = cell;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public T getCell() {
			return cell;
		}

		public void setCell(T cell) {
			this.cell = cell;
		}

		@Override
		public String toString() {
			return "FlexigridCellResponse [id=" + id + ", cell=" + cell + "]";
		}
	}
}
