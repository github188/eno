package com.energicube.eno.common.datatables.jpa.service;

import java.io.Serializable;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

/**
 * 数据表格式服务接口
 * */
public interface DatatablesService<T extends Serializable> {

	/**
	 * 查找指定条件的对象
	 * */
	public DataSet<T> findObjectWithDatatablesCriterias(DatatablesCriterias criterias, Class<T> clazz);
	
}
