package com.energicube.eno.common.jsonquery.jpa.util;

import java.text.SimpleDateFormat;
import org.joda.time.DateTime;

import com.energicube.eno.common.jsonquery.jpa.enumeration.OperatorEnum;
import com.energicube.eno.common.jsonquery.jpa.filter.JsonFilter;
import com.energicube.eno.common.jsonquery.jpa.mapper.JsonObjectMapper;

public class HqlFilterUtil {
	public static String where(String filter) {
		JsonFilter jsonFilter = JsonObjectMapper.map(filter);
		StringBuilder hqlQuery = new StringBuilder();

		if (jsonFilter != null && jsonFilter.getRules().size() > 0) {
			hqlQuery.append("WHERE ");
			Boolean isSecondaryCondition = false;

			for (JsonFilter.Rule rule : jsonFilter.getRules()) {
				if (isSecondaryCondition == true) {
					hqlQuery.append(" AND ");
				}

				if (OperatorEnum.getEnum(rule.getOp()) != OperatorEnum.CONTAINS
						&& OperatorEnum.getEnum(rule.getOp()) != OperatorEnum.BEGINS_WITH
						&& OperatorEnum.getEnum(rule.getOp()) != OperatorEnum.ENDS_WITH) {
					hqlQuery.append(rule.getField());
					hqlQuery.append(" ");
					hqlQuery.append(HqlFilterUtil.getOperator(rule.getOp()));
					hqlQuery.append(" ");
					hqlQuery.append(getDateOrReturnData(rule.getData()));

				} else {
					hqlQuery = HqlFilterUtil.constructLike(rule, hqlQuery);
				}

				isSecondaryCondition = true;
			}
		}

		return hqlQuery.toString();
	}

	public static StringBuilder constructLike(JsonFilter.Rule rule,
			StringBuilder hqlQuery) {

		if (OperatorEnum.getEnum(rule.getOp()) == OperatorEnum.ENDS_WITH) {
			hqlQuery.append(rule.getField());
			hqlQuery.append(" ");
			hqlQuery.append("LIKE");
			hqlQuery.append(" '");
			hqlQuery.append(rule.getData());
			hqlQuery.append("%'");

			return hqlQuery;
		}

		if (OperatorEnum.getEnum(rule.getOp()) == OperatorEnum.BEGINS_WITH) {
			hqlQuery.append(rule.getField());
			hqlQuery.append(" ");
			hqlQuery.append("LIKE");
			hqlQuery.append(" '");
			hqlQuery.append("%");
			hqlQuery.append(rule.getData());
			hqlQuery.append("'");

			return hqlQuery;
		}

		if (OperatorEnum.getEnum(rule.getOp()) == OperatorEnum.CONTAINS) {
			hqlQuery.append(rule.getField());
			hqlQuery.append(" ");
			hqlQuery.append("LIKE");
			hqlQuery.append(" ");
			hqlQuery.append("'%");
			hqlQuery.append(rule.getData());
			hqlQuery.append("%'");

			return hqlQuery;
		}

		throw new RuntimeException("Like filter does not exist!");
	}

	/**
	 * Guessing way of converting possible date type data. Otherwise return
	 * original data
	 * 
	 * @param data
	 * @return
	 */
	public static String getDateOrReturnData(String data) {
		try {
			DateTime dateTime = new DateTime(data);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return "'" + sdf.format(dateTime.toDate()) + "'";

		} catch (Exception ex) {
			return data;
		}
	}

	public static String getOperator(String oper) {
		if (OperatorEnum.getEnum(oper) == OperatorEnum.EQUAL) {
			return "=";
		}

		if (OperatorEnum.getEnum(oper) == OperatorEnum.NOT_EQUAL) {
			return "!=";
		}

		if (OperatorEnum.getEnum(oper) == OperatorEnum.LESS_THAN) {
			return "<";
		}

		if (OperatorEnum.getEnum(oper) == OperatorEnum.GREATER_THAN) {
			return ">";
		}

		if (OperatorEnum.getEnum(oper) == OperatorEnum.GREATER_EQUAL) {
			return ">=";
		}

		if (OperatorEnum.getEnum(oper) == OperatorEnum.LESSER_EQUAL) {
			return "<=";
		}

		if (OperatorEnum.getEnum(oper) == OperatorEnum.ENDS_WITH) {
			return "LIKE";
		}

		if (OperatorEnum.getEnum(oper) == OperatorEnum.BEGINS_WITH) {
			return "LIKE";
		}

		if (OperatorEnum.getEnum(oper) == OperatorEnum.CONTAINS) {
			return "LIKE";
		}

		throw new RuntimeException("No matching operator");
	}
}
