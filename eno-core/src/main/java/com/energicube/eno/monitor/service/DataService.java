package com.energicube.eno.monitor.service;

import org.springframework.transaction.annotation.Transactional;

import com.energicube.eno.common.DataModel;

import java.util.Map;

/**
 * All rights Reserved, Designed By ZCLF Copyright: Copyright(C) 2013-2014
 * Company ZCLF Energy Technologies Inc.
 *
 * @author 邹智祥
 * @version 1.0
 * @date 2014/12/04 15:08
 * @Description ENO数据接口类
 */
public interface DataService {

	/**
	 * 根据以下参数，返回图表需要的数据
	 *
	 * @return
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> findRequestData(DataModel dm);

	/**
	 * 返回图标需要的数据
	 * dm中的参数，除了format为单一字段外，其他属性，均按照多字段处理，逗号分隔
	 * 
	 * @param dm
	 * @return
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> getDataAndCataList(DataModel dm);

	/**
	 * 返回饼图所需要的数据
	 * dm中的参数，除了pointname按多字段（逗号分隔）处理外，其他属性，均按照单一字段处理
	 * 
	 * @param dm
	 * @return
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> getPieDataList(DataModel dm);
}
