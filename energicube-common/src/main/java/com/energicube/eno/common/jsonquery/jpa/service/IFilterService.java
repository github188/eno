package com.energicube.eno.common.jsonquery.jpa.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.energicube.eno.common.jsonquery.jpa.builder.JsonBooleanBuilder;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.OrderSpecifier;

public interface IFilterService<T extends Serializable> {
	T find(BooleanBuilder builder, Class<T> clazz);

	Page<T> readAndCount(BooleanBuilder builder, Pageable page, Class<T> clazz,
						 OrderSpecifier order);

	List<T> read(BooleanBuilder builder, Pageable page, Class<T> clazz,
				 OrderSpecifier order);

	Long count(BooleanBuilder builder, Class<T> clazz, OrderSpecifier order);

	Page<T> read(String filter, Class<T> clazz, Pageable page,
				 OrderSpecifier order);

	JsonBooleanBuilder getJsonBooleanBuilder(Class<T> clazz);

	List<T> read(BooleanBuilder builder, Pageable page, Class<T> clazz,
				 OrderSpecifier order, BooleanBuilder joinChildBuilder,
				 String joinChildField, Class<?> joinChildClass);

	Long count(BooleanBuilder builder, Class<T> clazz, OrderSpecifier order,
			   BooleanBuilder joinChildBuilder, String joinChildField,
			   Class<?> joinChildClass);

	Page<T> readAndCount(BooleanBuilder builder, Pageable page, Class<T> clazz,
						 OrderSpecifier order, BooleanBuilder joinChildBuilder,
						 String joinChildField, Class<?> joinChildClass);

	T findOne(BooleanBuilder builder, Class<T> clazz);

	Page<T> readAndCount(String filter, Pageable page, Class<T> clazz,
						 OrderSpecifier order, String joinChildField,
						 Class<?> joinChildClass, List<String> childFields);
}
