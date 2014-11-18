package com.energicube.eno.asset.repository.jpa;

import com.energicube.eno.asset.model.Companies;
import com.energicube.eno.common.datatables.jpa.util.DaoUtils;
import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 公司数据JPA操作接口
 */
@Repository
public class JpaCompaniesRepository {


    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据条件查找位置
     */
    public List<Companies> findCompaniesWithDatatablesCriterias(DatatablesCriterias criterias) {

        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM Companies p");

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

        TypedQuery<Companies> query = entityManager.createQuery(queryBuilder.toString(), Companies.class);

        /**
         * 4th step : paging
         */
        query.setFirstResult(criterias.getDisplayStart());
        query.setMaxResults(criterias.getDisplaySize());

        return query.getResultList();
    }

    /**
     * <p/>
     * Query used to return the number of filtered Companies.
     *
     * @param criterias The DataTables criterias used to filter the Companies.
     *                  (maxResult, filtering, paging, ...)
     * @return the number of filtered Companies.
     */
    public Long getFilteredCount(DatatablesCriterias criterias) {

        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM Companies p");

        queryBuilder.append(DaoUtils.getFilterQuery(criterias));

        Query query = entityManager.createQuery(queryBuilder.toString());
        return Long.parseLong(String.valueOf(query.getResultList().size()));
    }

    /**
     * @return the total count of Companies
     */
    public Long getTotalCount() {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM Companies p");
        return (Long) query.getSingleResult();
    }

}
