package com.energicube.eno.common.jsonquery.jpa.response;

import java.io.Serializable;
import java.util.List;

public class SigmaResponse<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = 2026592188084872218L;
	
	private List<T> data;
	private PageInfo pageInfo;
	private String exception;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public static class PageInfo {
		private Integer pageSize;
		private Integer pageNum;
		private Integer totalRowNum;
		private Integer totalPageNum;
		private Integer startRowNum;
		private Integer endRowNum;

		public Integer getPageSize() {
			return pageSize;
		}

		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}

		public Integer getPageNum() {
			return pageNum;
		}

		public void setPageNum(Integer pageNum) {
			this.pageNum = pageNum;
		}

		public Integer getTotalRowNum() {
			return totalRowNum;
		}

		public void setTotalRowNum(Integer totalRowNum) {
			this.totalRowNum = totalRowNum;
		}

		public Integer getTotalPageNum() {
			return totalPageNum;
		}

		public void setTotalPageNum(Integer totalPageNum) {
			this.totalPageNum = totalPageNum;
		}

		public Integer getStartRowNum() {
			return startRowNum;
		}

		public void setStartRowNum(Integer startRowNum) {
			this.startRowNum = startRowNum;
		}

		public Integer getEndRowNum() {
			return endRowNum;
		}

		public void setEndRowNum(Integer endRowNum) {
			this.endRowNum = endRowNum;
		}
	}
}
