package com.energicube.eno.common.jsonquery.jpa.service;

import java.io.Serializable;

import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;

public interface IDataTablesService<T extends Serializable> {

	/**
	 * 读取数据至DataTables
	 * 
	 * @param params 请求参数
	 * */
	public DataTablesResponse<T> read(DataTablesRequestParams params);

}
