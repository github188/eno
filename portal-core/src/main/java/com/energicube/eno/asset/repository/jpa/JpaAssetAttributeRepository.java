package com.energicube.eno.asset.repository.jpa;

import com.energicube.eno.asset.model.AssetAttribute;
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
 * 资产属性数据JPA操作接口
 */
@Repository
public class JpaAssetAttributeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * <p/>
     * Query used to populate the DataTables that display the list of Assets.
     *
     * @param criterias The DataTables criterias used to filter the Assets.
     *                  (maxResult, filtering, paging, ...)
     * @return a filtered list of Assets.
     */
    public List<AssetAttribute> findAssetWithDatatablesCriterias(DatatablesCriterias criterias) {

        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM AssetAttribute p");

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

        TypedQuery<AssetAttribute> query = entityManager.createQuery(queryBuilder.toString(), AssetAttribute.class);

        /**
         * 4th step : paging
         */
        query.setFirstResult(criterias.getDisplayStart());
        query.setMaxResults(criterias.getDisplaySize());

        return query.getResultList();
    }

    /**
     * <p/>
     * Query used to return the number of filtered Assets.
     *
     * @param criterias The DataTables criterias used to filter the Assets.
     *                  (maxResult, filtering, paging, ...)
     * @return the number of filtered Assets.
     */
    public Long getFilteredCount(DatatablesCriterias criterias) {

        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM AssetAttribute p");

        queryBuilder.append(DaoUtils.getFilterQuery(criterias));

        Query query = entityManager.createQuery(queryBuilder.toString());
        return Long.parseLong(String.valueOf(query.getResultList().size()));
    }

    /**
     * @return the total count of Assets.
     */
    public Long getTotalCount() {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM AssetAttribute p");
        return (Long) query.getSingleResult();
    }


}
