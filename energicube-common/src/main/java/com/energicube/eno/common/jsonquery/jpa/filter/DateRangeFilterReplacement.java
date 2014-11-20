package com.energicube.eno.common.jsonquery.jpa.filter;

import java.util.Map;

import com.energicube.eno.common.jsonquery.jpa.util.FieldReplacementUtil;
import com.energicube.eno.common.jsonquery.jpa.util.QueryUtil;

public abstract  class DateRangeFilterReplacement implements FilterReplacement {

	protected String datefrom;
	protected String dateto;

	public DateRangeFilterReplacement(String datefrom, String dateto) {
		super();
		this.datefrom = datefrom;
		this.dateto = dateto;
	}

	public String replace(String filter) {
		if (filter == null) {
			filter = QueryUtil.init();
		}

		filter = preReplace(filter);
		filter = FieldReplacementUtil.forQuery(filter, getReplacementMap());
		filter = postReplace(filter);

		return filter;
	}

	/**
	 * Field by field replacements should be declared here
	 * 
	 * @return a map
	 */
	public abstract Map<String, String> getReplacementMap();

	/**
	 * A pre hook before fields will be replaced
	 * 
	 * @param filter
	 * @return the filter
	 */
	public String preReplace(String filter) {
		return filter;
	}

	/**
	 * A post hook after the fields have replaced
	 * 
	 * @param filter
	 * @return the filter
	 */
	public String postReplace(String filter) {
		return filter;
	}

	public String getDatefrom() {
		return datefrom;
	}

	public void setDatefrom(String datefrom) {
		this.datefrom = datefrom;
	}

	public String getDateto() {
		return dateto;
	}

	public void setDateto(String dateto) {
		this.dateto = dateto;
	}

}
