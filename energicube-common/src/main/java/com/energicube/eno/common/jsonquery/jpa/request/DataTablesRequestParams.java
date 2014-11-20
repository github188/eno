package com.energicube.eno.common.jsonquery.jpa.request;

public class DataTablesRequestParams {
	private String sEcho;

	private int iDisplayLength;

	private int iDisplayStart;

	private String iSortingCols;

	private int iSortCol_0;

	private String sSortDir_0;

	private String sSearch;

	private String iColumns;

	private String sColumns;

	public int getiSortCol_0() {
		return iSortCol_0;
	}

	public void setiSortCol_0(int iSortCol_0) {
		this.iSortCol_0 = iSortCol_0;
	}

	public int getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public int getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public String getsSearch() {
		return sSearch;
	}

	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}

	public String getiColumns() {
		return iColumns;
	}

	public void setiColumns(String iColumns) {
		this.iColumns = iColumns;
	}

	public String getiSortingCols() {
		return iSortingCols;
	}

	public void setiSortingCols(String iSortingCols) {
		this.iSortingCols = iSortingCols;
	}

	public String getsSortDir_0() {
		return sSortDir_0;
	}

	public void setsSortDir_0(String sSortDir_0) {
		this.sSortDir_0 = sSortDir_0;
	}

	public String getsColumns() {
		return sColumns;
	}

	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}
	
	@Override
	public String toString() {
		return "DataTablesRequestParams [sEcho=" + sEcho + ", iDisplayLength="
				+ iDisplayLength + ", iDisplayStart=" + iDisplayStart
				+ ", iSortingCols=" + iSortingCols + ", iSortCol_0="
				+ iSortCol_0 + ", sSortDir_0=" + sSortDir_0 + ", sSearch="
				+ sSearch + ", iColumns=" + iColumns + ", sColumns=" + sColumns
				+ "]";
	}

	

}
