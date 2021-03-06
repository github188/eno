package com.energicube.eno.common.jsonquery.jpa.builder.operator;


import java.text.ParseException;
import java.util.Date;

import org.joda.time.DateTime;

import com.energicube.eno.common.jsonquery.jpa.builder.JunctionBuilder;
import com.energicube.eno.common.jsonquery.jpa.enumeration.OperatorEnum;
import com.energicube.eno.common.jsonquery.jpa.filter.JsonFilter;
import com.energicube.eno.common.jsonquery.jpa.util.ClassUtil;
import com.energicube.eno.common.jsonquery.jpa.util.DateTimeUtil;
import com.energicube.eno.common.jsonquery.jpa.util.PathUtil;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.path.DatePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;


/**
 * Aggregates an existing {@link com.mysema.query.BooleanBuilder} with operator-type
 * {@link Predicate}
 * 
 * @param clazz
 *            the domain class
 * @param variable
 *            the literal name of the class
 * @param builder
 *            an existing {@link com.mysema.query.BooleanBuilder}
 * @param rule
 *            a {@link JsonFilter.Rule} containing a field query
 * 
 */
public class GreaterThanBuilder {

	@SuppressWarnings("unchecked")
	public static BooleanBuilder get(Class<?> clazz, String variable,
			BooleanBuilder builder, JsonFilter.Rule rule) {

		if (OperatorEnum.getEnum(rule.getOp()) == OperatorEnum.GREATER_THAN) {
			if (ClassUtil.getType(clazz, rule.getField()) == String.class) {
				StringPath path = (StringPath) PathUtil.getPath(clazz,
						variable, rule.getField());
				return JunctionBuilder.getBuilder(path.gt(rule.getData()),
						builder, rule);
			}

			if (ClassUtil.getType(clazz, rule.getField()) == Boolean.class) {
				throw new RuntimeException("Operator not supported: "
						+ rule.getOp());
			}

			if (ClassUtil.getType(clazz, rule.getField()) == Integer.class) {
				NumberPath<Integer> path = (NumberPath<Integer>) PathUtil
						.getPath(clazz, variable, rule.getField());
				return JunctionBuilder
						.getBuilder(path.gt(Integer.valueOf(rule.getData())),
								builder, rule);
			}

			if (ClassUtil.getType(clazz, rule.getField()) == Long.class) {
				NumberPath<Long> path = (NumberPath<Long>) PathUtil.getPath(
						clazz, variable, rule.getField());
				return JunctionBuilder.getBuilder(
						path.gt(Long.valueOf(rule.getData())), builder, rule);
			}

			if (ClassUtil.getType(clazz, rule.getField()) == Double.class) {
				NumberPath<Double> path = (NumberPath<Double>) PathUtil
						.getPath(clazz, variable, rule.getField());
				return JunctionBuilder.getBuilder(
						path.gt(Double.valueOf(rule.getData())), builder, rule);
			}

			if (ClassUtil.getType(clazz, rule.getField()) == DateTime.class) {
				DatePath<DateTime> path = (DatePath<DateTime>) PathUtil
						.getPath(clazz, variable, rule.getField());
				DateTime dt = new DateTime(rule.getData());
				dt = DateTimeUtil.getDateTimeWithOffset(dt);
				return JunctionBuilder.getBuilder(path.gt(dt), builder, rule);
			}

			if (ClassUtil.getType(clazz, rule.getField()) == Date.class) {
				DatePath<Date> path = (DatePath<Date>) PathUtil.getPath(clazz,
						variable, rule.getField());
				Date dt;
				try {
					dt = DateTimeUtil.SIMPLE_DATE_FORMAT.parse(rule.getData());
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				return JunctionBuilder.getBuilder(path.gt(dt), builder, rule);
			}

			// Check for custom objects by id
			if (ClassUtil.getType(clazz, rule.getField()) != null) {
				NumberPath<Long> path = (NumberPath<Long>) PathUtil.getPath(
						clazz, variable, rule.getField());
				return JunctionBuilder.getBuilder(
						path.gt(Long.valueOf(rule.getData())), builder, rule);
			}
		}

		return null;
	}
}
