package com.energicube.eno.common.jsonquery.jpa.builder;

import com.energicube.eno.common.jsonquery.jpa.enumeration.JunctionEnum;
import com.energicube.eno.common.jsonquery.jpa.filter.JsonFilter;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.expr.BooleanExpression;

public class JunctionBuilder {

	public static BooleanBuilder getBuilder(
			BooleanExpression booleanExpression, BooleanBuilder builder,
			JsonFilter.Rule rule) {
		if (rule.getJunction() == null) {
			return builder.and(booleanExpression);
		}

		if (JunctionEnum.getEnum(rule.getJunction()) == JunctionEnum.OR) {
			return builder.or(booleanExpression);
		}

		if (JunctionEnum.getEnum(rule.getJunction()) == JunctionEnum.AND) {
			return builder.and(booleanExpression);
		}

		return builder.and(booleanExpression);
	}
}
