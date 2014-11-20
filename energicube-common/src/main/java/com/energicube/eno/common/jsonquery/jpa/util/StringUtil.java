package com.energicube.eno.common.jsonquery.jpa.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utility for retrieving a specific path in a fully-qualified {@link String}
 * class name
 * */
public class StringUtil {
	protected static Log logger = LogFactory.getLog("jsonquery");

	public static String getFieldAtIndex(String path, Integer index) {
		String[] parts = path.split("\\.");

		if (parts.length > 1) {
			return parts[index];
		}

		return path;
	}

	public static String getLastField(String path) {
		String[] parts = path.split("\\.");

		if (parts.length > 1) {
			return parts[parts.length - 1];
		}

		return path;
	}
}
