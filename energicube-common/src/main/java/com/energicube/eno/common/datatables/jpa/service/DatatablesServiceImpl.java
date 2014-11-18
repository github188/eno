package com.energicube.eno.common.datatables.jpa.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.energicube.eno.common.datatables.jpa.util.DaoUtils;

@Service
@Transactional(readOnly = true)
public class DatatablesServiceImpl<T extends Serializable> implements DatatablesService<T> {

	private static final Log logger = LogFactory.getLog(DatatablesServiceImpl.class);
	
	@Autowired
	private EntityManagerFactory emf;
	
	public DataSet<T> findObjectWithDatatablesCriterias(
			DatatablesCriterias criterias,Class<T> clazz) {
		
		List<T> list = findEntityWithDatatablesCriterias(criterias,clazz);
		Long count = getTotalCount(clazz);
		Long countFiltered = getFilteredCount(criterias,clazz);

		return new DataSet<T>(list, count, countFiltered);
	}
	
	private List<T> findEntityWithDatatablesCriterias(DatatablesCriterias criterias,Class<T> clazz)  {
		StringBuilder queryBuilder = new StringBuilder("SELECT p FROM "+ clazz.getName() +" p");

		logger.info(queryBuilder);
		
		/**
		 * 1st step : global and individual column filtering
		 */
		queryBuilder.append(DaoUtils.getFilterQuery(criterias));
		
		/**
		 * 3rd step : sorting
		 */
		if (criterias.hasOneSortedColumn()) {

			List<String> orderParams = new ArrayList<String>();
			queryBuilder.append(" ORDER BY ");
			for (ColumnDef columnDef : criterias.getSortingColumnDefs()) {
				orderParams.add("p." + columnDef.getName() + " " + columnDef.getSortDirection());
			}

			Iterator<String> itr2 = orderParams.iterator();
			while (itr2.hasNext()) {
				queryBuilder.append(itr2.next());
				if (itr2.hasNext()) {
					queryBuilder.append(" , ");
				}
			}
		}
		
		EntityManager entityManager = emf.createEntityManager();
		TypedQuery<T> query = entityManager.createQuery(queryBuilder.toString(), clazz);

		/**
		 * 4th step : paging
		 */
		query.setFirstResult(criterias.getDisplayStart());
		query.setMaxResults(criterias.getDisplaySize());

		return query.getResultList();
	}
	
	
	/**
	 * <p>
	 * Query used to return the number of filtered Assets.
	 * 
	 * @param criterias
	 *            The DataTables criterias used to filter the Assets.
	 *            (maxResult, filtering, paging, ...)
	 * @return the number of filtered Assets.
	 */
	public Long getFilteredCount(DatatablesCriterias criterias,Class<T> clazz) {

		StringBuilder queryBuilder = new StringBuilder("SELECT p FROM "+ clazz.getName() +" p");

		queryBuilder.append(DaoUtils.getFilterQuery(criterias));
		EntityManager entityManager = emf.createEntityManager();
		Query query = entityManager.createQuery(queryBuilder.toString());
		return Long.parseLong(String.valueOf(query.getResultList().size()));
	}

	/**
	 * @return the total count of Assets.
	 */
	public Long getTotalCount(Class<T> clazz) {
		EntityManager entityManager = emf.createEntityManager();
		Query query = entityManager.createQuery("SELECT COUNT(p) FROM "+ clazz.getName() +" p");
		return (Long) query.getSingleResult();
	}

	
}
