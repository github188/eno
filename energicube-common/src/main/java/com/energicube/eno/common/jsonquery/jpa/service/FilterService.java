package com.energicube.eno.common.jsonquery.jpa.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.energicube.eno.common.jsonquery.jpa.builder.JsonBooleanBuilder;
import com.energicube.eno.common.jsonquery.jpa.filter.JsonFilter;
import com.energicube.eno.common.jsonquery.jpa.util.QueryUtil;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.path.PathBuilder;

@Service
public class FilterService<T extends Serializable> implements IFilterService<T> {

	@Autowired
	private EntityManagerFactory emf;

	public T findOne(BooleanBuilder builder, Class<T> clazz) {
		String variable = clazz.getSimpleName().substring(0, 1).toLowerCase()
				+ clazz.getSimpleName().substring(1);
		PathBuilder<T> entityPath = new PathBuilder<T>(clazz, variable);
		EntityManager em = emf.createEntityManager();
		EntityPath<T> path = entityPath;

		JPQLQuery result = new JPAQuery(em).from(path).where(builder);

		return result.uniqueResult(entityPath);
	}

	public T find(BooleanBuilder builder, Class<T> clazz) {
		String variable = clazz.getSimpleName().substring(0, 1).toLowerCase()
				+ clazz.getSimpleName().substring(1);
		PathBuilder<T> entityPath = new PathBuilder<T>(clazz, variable);
		EntityManager em = emf.createEntityManager();
		EntityPath<T> path = entityPath;

		JPQLQuery result = new JPAQuery(em).from(path).where(builder);

		return result.uniqueResult(entityPath);
	}

	public Page<T> readAndCount(BooleanBuilder builder, Pageable page,
			Class<T> clazz, OrderSpecifier order) {
		Page<T> pageImpl = new PageImpl<T>(read(builder, page, clazz, order),
				page, count(builder, clazz, order));
		return pageImpl;
	}

	public Page<T> readAndCount(BooleanBuilder builder, Pageable page,
			Class<T> clazz, OrderSpecifier order,
			BooleanBuilder joinChildBuilder, String joinChildField,
			Class<?> joinChildClass) {
		Page<T> pageImpl = new PageImpl<T>(read(builder, page, clazz, order,
				joinChildBuilder, joinChildField, joinChildClass), page, count(
				builder, clazz, order, joinChildBuilder, joinChildField,
				joinChildClass));
		return pageImpl;
	}

	public Page<T> readAndCount(String filter, Pageable page, Class<T> clazz,
			OrderSpecifier order, String joinChildField,
			Class<?> joinChildClass, List<String> childFields) {
		Map<String, String> filters = QueryUtil.createParentAndChildFilter(
				filter, childFields);
		BooleanBuilder parentBuilder = new JsonBooleanBuilder(clazz)
				.build(new JsonFilter(filters.get("parentFilter").toString()));
		BooleanBuilder joinChildBuilder = new JsonBooleanBuilder(joinChildClass)
				.build(new JsonFilter(filters.get("childFilter").toString()));

		Page<T> pageImpl = new PageImpl<T>(read(parentBuilder, page, clazz,
				order, joinChildBuilder, joinChildField, joinChildClass), page,
				count(parentBuilder, clazz, order, joinChildBuilder,
						joinChildField, joinChildClass));
		return pageImpl;
	}

	public List<T> read(BooleanBuilder builder, Pageable page, Class<T> clazz,
			OrderSpecifier order) {
		String variable = clazz.getSimpleName().substring(0, 1).toLowerCase()
				+ clazz.getSimpleName().substring(1);
		PathBuilder<T> entityPath = new PathBuilder<T>(clazz, variable);
		EntityManager em = emf.createEntityManager();
		EntityPath<T> path = entityPath;

		JPQLQuery result = new JPAQuery(em).from(path).where(builder)
				.orderBy(order);

		if (page != null) {
			result.offset(page.getOffset());
			result.limit(page.getPageSize());
		}

		return result.list(entityPath);
	}

	public List<T> read(BooleanBuilder builder, Pageable page, Class<T> clazz,
			OrderSpecifier order, BooleanBuilder joinChildBuilder,
			String joinChildField, Class<?> joinChildClass) {
		String variable = clazz.getSimpleName().substring(0, 1).toLowerCase()
				+ clazz.getSimpleName().substring(1);
		PathBuilder<T> entityPath = new PathBuilder<T>(clazz, variable);
		EntityManager em = emf.createEntityManager();
		EntityPath<T> path = entityPath;

		if (joinChildBuilder.getValue() != null) {
			String childVariable = joinChildClass.getSimpleName()
					.substring(0, 1).toLowerCase()
					+ joinChildClass.getSimpleName().substring(1);
			PathBuilder<T> joinPath = new PathBuilder<T>(clazz, variable);
			PathBuilder<Object> joinAlias = new PathBuilder<Object>(
					joinChildClass, childVariable);

			EntityPath<Object> jPath = joinPath.get(joinChildField);
			EntityPath<Object> jAlias = joinAlias;

			JPQLQuery result = new JPAQuery(em).from(path).join(jPath, jAlias)
					.with(joinChildBuilder).where(builder).orderBy(order);

			if (page != null) {
				result.offset(page.getOffset());
				result.limit(page.getPageSize());
			}

			return result.list(entityPath);
		} else {
			return read(builder, page, clazz, order);
		}
	}

	public Long count(BooleanBuilder builder, Class<T> clazz,
			OrderSpecifier order) {
		String variable = clazz.getSimpleName().substring(0, 1).toLowerCase()
				+ clazz.getSimpleName().substring(1);
		PathBuilder<T> entityPath = new PathBuilder<T>(clazz, variable);
		EntityManager em = emf.createEntityManager();
		EntityPath<T> path = entityPath;

		JPQLQuery result = new JPAQuery(em).from(path).where(builder)
				.orderBy(order);

		return result.count();
	}

	public Long count(BooleanBuilder builder, Class<T> clazz,
			OrderSpecifier order, BooleanBuilder joinChildBuilder,
			String joinChildField, Class<?> joinChildClass) {
		String variable = clazz.getSimpleName().substring(0, 1).toLowerCase()
				+ clazz.getSimpleName().substring(1);
		PathBuilder<T> entityPath = new PathBuilder<T>(clazz, variable);
		EntityManager em = emf.createEntityManager();
		EntityPath<T> path = entityPath;

		if (joinChildBuilder.getValue() != null) {
			String childVariable = joinChildClass.getSimpleName()
					.substring(0, 1).toLowerCase()
					+ joinChildClass.getSimpleName().substring(1);
			PathBuilder<T> joinPath = new PathBuilder<T>(clazz, variable);
			PathBuilder<Object> joinAlias = new PathBuilder<Object>(
					joinChildClass, childVariable);

			EntityPath<Object> jPath = joinPath.get(joinChildField);
			EntityPath<Object> jAlias = joinAlias;

			JPQLQuery result = new JPAQuery(em).from(path).join(jPath, jAlias)
					.with(joinChildBuilder).where(builder).orderBy(order);

			return result.count();
		} else {
			return count(builder, clazz, order);
		}
	}

	public Page<T> read(String filter, Class<T> clazz, Pageable page,
			OrderSpecifier order) {
		return readAndCount(
				getJsonBooleanBuilder(clazz).build(new JsonFilter(filter)),
				page, clazz, order);
	}

	public JsonBooleanBuilder getJsonBooleanBuilder(Class<T> clazz) {
		return new JsonBooleanBuilder(clazz);
	}

}
