package com.energicube.eno.common.jsonquery.jpa.builder.operator;

import java.util.Date;

import org.joda.time.DateTime;

import com.energicube.eno.common.jsonquery.jpa.builder.JunctionBuilder;
import com.energicube.eno.common.jsonquery.jpa.enumeration.OperatorEnum;
import com.energicube.eno.common.jsonquery.jpa.filter.JsonFilter;
import com.energicube.eno.common.jsonquery.jpa.util.ClassUtil;
import com.energicube.eno.common.jsonquery.jpa.util.PathUtil;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.path.StringPath;

/**
 * Aggregates an existing {@link com.mysema.query.BooleanBuilder} with operator-type {@link Predicate}
 * 
 * @param clazz the domain class
 * @param variable the literal name of the class
 * @param builder an existing {@link com.mysema.query.BooleanBuilder}
 * @param rule a {@link JsonFilter.Rule} containing a field query
 * 
 */
public class BeginsWithBuilder {
	
	public static BooleanBuilder get(Class<?> clazz, String variable,
			BooleanBuilder builder, JsonFilter.Rule rule) {

		if (OperatorEnum.getEnum(rule.getOp()) == OperatorEnum.BEGINS_WITH) {
			if (ClassUtil.getType(clazz, rule.getField()) == String.class) {
				StringPath path = (StringPath) PathUtil.getPath(clazz,
						variable, rule.getField());
				return JunctionBuilder.getBuilder(
						path.startsWith(rule.getData()), builder, rule);
			}

			if (ClassUtil.getType(clazz, rule.getField()) == Boolean.class) {
				throw new RuntimeException("Operator not supported: "
						+ rule.getOp());
			}

			if (ClassUtil.getType(clazz, rule.getField()) == Integer.class) {
				throw new RuntimeException("Operator not supported: "
						+ rule.getOp());
			}

			if (ClassUtil.getType(clazz, rule.getField()) == Long.class) {
				throw new RuntimeException("Operator not supported: "
						+ rule.getOp());
			}

			if (ClassUtil.getType(clazz, rule.getField()) == Double.class) {
				throw new RuntimeException("Operator not supported: "
						+ rule.getOp());
			}

			if (ClassUtil.getType(clazz, rule.getField()) == DateTime.class) {
				throw new RuntimeException("Operator not supported: "
						+ rule.getOp());
			}

			if (ClassUtil.getType(clazz, rule.getField()) == Date.class) {
				throw new RuntimeException("Operator not supported: "
						+ rule.getOp());
			}

			// Check for custom objects by id
			if (ClassUtil.getType(clazz, rule.getField()) != null) {
				throw new RuntimeException("Operator not supported: "
						+ rule.getOp());
			}
		}

		return null;
	}
}
