package com.energicube.eno.common.jsonquery.jpa.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Retrieves the class type
 * */
public class ClassUtil {


	protected static Log logger = LogFactory.getLog("jsonquery");

	public static Class<?> getMatchingType(Field f, String field) {
		if (f.getName().equals(StringUtil.getLastField(field))) {
			return f.getType();
		}

		return null;
	}

	public static Class<?> getType(Class<?> clazz, String field) {
		for (Field f : clazz.getDeclaredFields()) {
			for (Field g : f.getType().getDeclaredFields()) {
				for (Field h : g.getType().getDeclaredFields()) {

					Class<?> type = getMatchingType(h, field);
					if (type != null) {
						return type;
					}
				}

				Class<?> type = getMatchingType(g, field);
				if (type != null) {
					return type;
				}
			}

			Class<?> type = getMatchingType(f, field);
			if (type != null) {
				return type;
			}
		}

		Class<?> type = getTypeByParent(clazz, field);
		if (type != null) {
			return type;
		}

		throw new RuntimeException("No matching field for " + field);
	}

	// Check by inheritance if no match
	public static Class<?> getTypeByParent(Class<?> clazz, String field) {
		Class<?> parentClazz = clazz.getSuperclass();
		while (parentClazz != null) {
			for (Field f : parentClazz.getDeclaredFields()) {
				for (Field g : f.getType().getDeclaredFields()) {
					for (Field h : g.getType().getDeclaredFields()) {

						Class<?> type = getMatchingType(h, field);
						if (type != null) {
							return type;
						}
					}

					Class<?> type = getMatchingType(g, field);
					if (type != null) {
						return type;
					}
				}

				Class<?> type = getMatchingType(f, field);
				if (type != null) {
					return type;
				}
			}

			parentClazz = parentClazz.getSuperclass();
		}

		return null;
	}

	public static String getId(Class<?> clazz) {
		for (Field f : clazz.getDeclaredFields()) {
			for (Annotation an : f.getAnnotations()) {
				if (an instanceof javax.persistence.Id) {
					return f.getName();
				}
			}
		}

		String id = getIdByParent(clazz);
		if (id != null) {
			return id;
		}

		throw new RuntimeException("No fk id found for field " + clazz);
	}

	public static String getIdByParent(Class<?> clazz) {
		Class<?> parentClazz = clazz.getSuperclass();
		while (parentClazz != null) {
			for (Field f : parentClazz.getDeclaredFields()) {
				for (Annotation an : f.getAnnotations()) {
					if (an instanceof javax.persistence.Id) {
						return f.getName();
					}
				}
			}

			parentClazz = parentClazz.getSuperclass();
		}

		return null;
	}

	public static String getVariableName(Class<?> clazz) {
		return clazz.getSimpleName().substring(0, 1).toLowerCase()
				+ clazz.getSimpleName().substring(1);
	}
}
