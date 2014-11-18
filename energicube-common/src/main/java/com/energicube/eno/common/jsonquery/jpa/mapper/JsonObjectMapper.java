package com.energicube.eno.common.jsonquery.jpa.mapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;


import com.energicube.eno.common.jsonquery.jpa.filter.JsonFilter;

public class JsonObjectMapper {

	protected static Log logger = LogFactory.getLog("jsonquery");

	public static JsonFilter map(String jsonString) {

		if (jsonString != null) {
			ObjectMapper mapper = new ObjectMapper();

			try {
				return mapper.readValue(jsonString, JsonFilter.class);
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
				throw new RuntimeException(e);
			}
		}

		return null;
	}
}
