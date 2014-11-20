package com.energicube.eno.common.jsonquery.jpa.specifier;

import java.util.Date;

import org.joda.time.DateTime;

import com.energicube.eno.common.jsonquery.jpa.enumeration.OrderEnum;
import com.energicube.eno.common.jsonquery.jpa.util.ClassUtil;
import com.energicube.eno.common.jsonquery.jpa.util.PathUtil;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.path.DatePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

/**
 * Retrieves an {@OrderSpecifier} for specifying the order of
 * records.
 * 
 * @param clazz
 *            the domain class
 * @param variable
 *            the literal name of the class
 * @param field
 *            the literal name of the field
 * @param orderEnum
 *            an {@link OrderEnum} value
 * 
 */
public class Order {
	
	private Class<?> clazz;
	private String variable;

	public Order(Class<?> clazz) {
		super();
		this.clazz = clazz;
		this.variable = ClassUtil.getVariableName(clazz);
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public OrderSpecifier<?> by(String field, OrderEnum orderEnum) {
		if (orderEnum == OrderEnum.ASC) {
			if (ClassUtil.getType(clazz, field) == String.class) {
				StringPath path = (StringPath) PathUtil.getPath(clazz,
						variable, field);
				return path.asc();
			}

			if (ClassUtil.getType(clazz, field) == Integer.class) {
				NumberPath<?> path = (NumberPath<?>) PathUtil.getPath(clazz,
						variable, field);
				return path.asc();
			}

			if (ClassUtil.getType(clazz, field) == Long.class) {
				NumberPath<?> path = (NumberPath<?>) PathUtil.getPath(clazz,
						variable, field);
				return path.asc();
			}

			if (ClassUtil.getType(clazz, field) == Double.class) {
				NumberPath<?> path = (NumberPath<?>) PathUtil.getPath(clazz,
						variable, field);
				return path.asc();
			}

			if (ClassUtil.getType(clazz, field) == DateTime.class) {
				DatePath<?> path = (DatePath<?>) PathUtil.getPath(clazz,
						variable, field);
				return path.asc();
			}

			if (ClassUtil.getType(clazz, field) == Date.class) {
				DatePath<?> path = (DatePath<?>) PathUtil.getPath(clazz,
						variable, field);
				return path.asc();
			}

			// Check for custom objects by id
			if (ClassUtil.getType(clazz, field) != null) {
				NumberPath<?> path = (NumberPath<?>) PathUtil.getPath(clazz,
						variable, field);
				return path.asc();
			}

		} else if (orderEnum == OrderEnum.DESC) {
			if (ClassUtil.getType(clazz, field) == String.class) {
				StringPath path = (StringPath) PathUtil.getPath(clazz,
						variable, field);
				return path.desc();
			}

			if (ClassUtil.getType(clazz, field) == Integer.class) {
				NumberPath<?> path = (NumberPath<?>) PathUtil.getPath(clazz,
						variable, field);
				return path.desc();
			}

			if (ClassUtil.getType(clazz, field) == Long.class) {
				NumberPath<?> path = (NumberPath<?>) PathUtil.getPath(clazz,
						variable, field);
				return path.desc();
			}

			if (ClassUtil.getType(clazz, field) == Double.class) {
				NumberPath<?> path = (NumberPath<?>) PathUtil.getPath(clazz,
						variable, field);
				return path.desc();
			}

			if (ClassUtil.getType(clazz, field) == DateTime.class) {
				DatePath<?> path = (DatePath<?>) PathUtil.getPath(clazz,
						variable, field);
				return path.desc();
			}

			if (ClassUtil.getType(clazz, field) == Date.class) {
				DatePath<?> path = (DatePath<?>) PathUtil.getPath(clazz,
						variable, field);
				return path.desc();
			}

			// Check for custom objects by id
			if (ClassUtil.getType(clazz, field) != null) {
				NumberPath<?> path = (NumberPath<?>) PathUtil.getPath(clazz,
						variable, field);
				return path.desc();
			}
		}

		return null;
	}
}
