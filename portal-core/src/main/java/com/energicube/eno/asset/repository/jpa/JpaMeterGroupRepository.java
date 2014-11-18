package com.energicube.eno.asset.repository.jpa;

import com.energicube.eno.asset.model.MeterGroup;
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
 * 计量器组数据JPA操作接口
 *
 * @author CHENPING
 */
@Repository
public class JpaMeterGroupRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public List<MeterGroup> findMeterGroupWithDatatablesCriterias(DatatablesCriterias criterias) {

        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM MeterGroup p");

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

        TypedQuery<MeterGroup> query = entityManager.createQuery(queryBuilder.toString(), MeterGroup.class);

        /**
         * 4th step : paging
         */
        query.setFirstResult(criterias.getDisplayStart());
        query.setMaxResults(criterias.getDisplaySize());

        return query.getResultList();
    }

    /**
     * <p/>
     * Query used to return the number of filtered MeterGroups.
     *
     * @param criterias The DataTables criterias used to filter the MeterGroups.
     *                  (maxResult, filtering, paging, ...)
     * @return the number of filtered MeasurePoints.
     */
    public Long getFilteredCount(DatatablesCriterias criterias) {

        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM MeterGroup p");

        queryBuilder.append(DaoUtils.getFilterQuery(criterias));

        Query query = entityManager.createQuery(queryBuilder.toString());
        return Long.parseLong(String.valueOf(query.getResultList().size()));
    }

    /**
     * @return the total count of MeterGroups
     */
    public Long getTotalCount() {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM MeterGroup p");
        return (Long) query.getSingleResult();
    }

}
