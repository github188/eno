package com.energicube.eno.common.jsonquery.jpa.util;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.BooleanPath;
import com.mysema.query.types.path.DatePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathBuilder;
import com.mysema.query.types.path.StringPath;

public class PathUtil {
	public static Path<?> getPath(Class<?> clazz, String variable, String field) {

		@SuppressWarnings({ "unchecked", "rawtypes" })
		PathBuilder<?> entityPath = new PathBuilder(clazz, variable);

		if (ClassUtil.getType(clazz, field) == String.class) {
			return entityPath.get(new StringPath(field));

		} else if (ClassUtil.getType(clazz, field) == Boolean.class) {
			return entityPath.get(new BooleanPath(field));

		} else if (ClassUtil.getType(clazz, field) == Integer.class) {
			return entityPath
					.get(new NumberPath<Integer>(Integer.class, field));

		} else if (ClassUtil.getType(clazz, field) == Long.class) {
			return entityPath.get(new NumberPath<Long>(Long.class, field));

		} else if (ClassUtil.getType(clazz, field) == Double.class) {
			return entityPath.get(new NumberPath<Double>(Double.class, field));

		} else if (ClassUtil.getType(clazz, field) == DateTime.class) {
			return entityPath
					.get(new DatePath<DateTime>(DateTime.class, field));

		} else if (ClassUtil.getType(clazz, field) == Date.class) {
			return entityPath.get(new DatePath<Date>(Date.class, field));

		} else if (ClassUtil.getType(clazz, field) == Serializable.class) {
			// For references on another objects, we assume that we reference by
			// id
			// And the type is Long and Serializable
			return entityPath.get(new NumberPath<Long>(Long.class, field));

		} else if (ClassUtil.getType(clazz, field) == List.class) {
			// For references on another objects, we assume that we reference by
			// id
			// And the type is Long and Serializable
			return entityPath.get(new NumberPath<Long>(Long.class, field));
		}

		throw new RuntimeException("No matching path for " + field);
	}
}
