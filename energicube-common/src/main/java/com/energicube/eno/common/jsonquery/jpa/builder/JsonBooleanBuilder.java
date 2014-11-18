package com.energicube.eno.common.jsonquery.jpa.builder;


import com.energicube.eno.common.jsonquery.jpa.builder.operator.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.energicube.eno.common.jsonquery.jpa.filter.JsonFilter;
import com.energicube.eno.common.jsonquery.jpa.mapper.JsonObjectMapper;
import com.energicube.eno.common.jsonquery.jpa.util.ClassUtil;
import com.energicube.eno.common.jsonquery.jpa.util.QueryUtil;
import com.mysema.query.BooleanBuilder;

/**
 * Produces a {@link com.mysema.query.BooleanBuilder} artifact from a JSON query {@link String}
 * 
 * @param <T>
 *            the domain class
 * @param clazz
 *            the domain class
 * @param variable
 *            the literal name of the domain class
 * 
 */
public class JsonBooleanBuilder {

	protected static Log logger = LogFactory.getLog("jsonquery");

	private Class<?> clazz;
	private String variable;

	public JsonBooleanBuilder(Class<?> clazz) {
		super();
		this.clazz = clazz;
		this.variable = ClassUtil.getVariableName(clazz);
	}

	public BooleanBuilder build(JsonFilter jsonFilter) {
		if (jsonFilter.getSource() == null) {
			jsonFilter.setSource(QueryUtil.init());
			// throw new RuntimeException("Source filter is null!");
		}

		JsonFilter filter = JsonObjectMapper.map(jsonFilter.getSource());

		BooleanBuilder builder = new BooleanBuilder();
		for (JsonFilter.Rule rule : filter.getRules()) {
			builder = build(builder, rule);
		}
		return builder;
	}

	public BooleanBuilder build(BooleanBuilder builder, JsonFilter.Rule rule) {
		BooleanBuilder tempBuilder = EqualBuilder.get(clazz, variable, builder,
				rule);
		if (tempBuilder != null) {
			return tempBuilder;
		}

		tempBuilder = NotEqualBuilder.get(clazz, variable, builder, rule);
		if (tempBuilder != null) {
			return tempBuilder;
		}

		tempBuilder = LessThanBuilder.get(clazz, variable, builder, rule);
		if (tempBuilder != null) {
			return tempBuilder;
		}

		tempBuilder = GreaterThanBuilder.get(clazz, variable, builder, rule);
		if (tempBuilder != null) {
			return tempBuilder;
		}

		tempBuilder = LesserEqualBuilder.get(clazz, variable, builder, rule);
		if (tempBuilder != null) {
			return tempBuilder;
		}

		tempBuilder = GreaterEqualBuilder.get(clazz, variable, builder, rule);
		if (tempBuilder != null) {
			return tempBuilder;
		}

		tempBuilder = EndsWithBuilder.get(clazz, variable, builder, rule);
		if (tempBuilder != null) {
			return tempBuilder;
		}

		tempBuilder = BeginsWithBuilder.get(clazz, variable, builder, rule);
		if (tempBuilder != null) {
			return tempBuilder;
		}

		tempBuilder = ContainsBuilder.get(clazz, variable, builder, rule);
		if (tempBuilder != null) {
			return tempBuilder;
		}

		throw new RuntimeException("Unexpected operator [" + rule.getOp()
				+ "] and field [" + rule.getField() + "]");
	}
}
