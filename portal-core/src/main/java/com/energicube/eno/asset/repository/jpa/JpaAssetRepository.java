package com.energicube.eno.asset.repository.jpa;

import com.energicube.eno.asset.model.Asset;
import com.energicube.eno.common.datatables.jpa.util.DaoUtils;
import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Repository
public class JpaAssetRepository {

    private Log logger = LogFactory.getLog(JpaAssetRepository.class);

    @PersistenceContext
    private EntityManager entityManager;


    public List<Asset> findAssetsByCondtion(Map<String, String> customCondition) {
        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM Asset p");

        if (customCondition.size() > 0) {

            queryBuilder.append(" WHERE ");

            List<String> paramList = new ArrayList<String>();
            String assetnumCondition = "";
            Iterator<Entry<String, String>> it = customCondition.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();

                if (pairs.getKey() == "assetnum") {
                    assetnumCondition = " LOWER(p." + pairs.getKey()
                            + ") LIKE '%?%'".replace("?", pairs.getValue() == null ? "" : pairs.getValue());
                } else {
                    if (StringUtils.hasLength(pairs.getValue())) {
                        paramList.add(" LOWER(p." + pairs.getKey()
                                + ") LIKE '?%'".replace("?", pairs.getValue()));
                    }
                }

            }

            Iterator<String> itr = paramList.iterator();
            while (itr.hasNext()) {
                queryBuilder.append(itr.next());
                if (itr.hasNext()) {
                    queryBuilder.append(" AND ");
                }
            }
            if (queryBuilder.length() > 0 && assetnumCondition.length() > 0) {
                queryBuilder.append(" OR ");
            }
            queryBuilder.append(assetnumCondition);
        }
        TypedQuery<Asset> query = entityManager.createQuery(queryBuilder.toString(), Asset.class);

        return query.getResultList();
    }


    /**
     * <p/>
     * Query used to populate the DataTables that display the list of Assets.
     *
     * @param criterias The DataTables criterias used to filter the Assets.
     *                  (maxResult, filtering, paging, ...)
     * @return a filtered list of Assets.
     */
    public List<Asset> findAssetWithDatatablesCriterias(DatatablesCriterias criterias, Map<String, String> customCondition) {

        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM Asset p");

        /**
         * 1st step : global and individual column filtering
         */
        StringBuilder whereBuilder = DaoUtils.getFilterQuery(criterias);
        queryBuilder.append(whereBuilder);


        logger.info("criterias.getSearch():" + whereBuilder.toString());
        logger.info("criterias.hasOneFilterableColumn():" + criterias.hasOneFilterableColumn());
        logger.info("customCondition:" + customCondition.size());

        //判断是否已经有查询条件，如果没有则
        if (whereBuilder.length() == 0 && customCondition.size() > 0) {

            if (!(StringUtils.hasLength(criterias.getSearch()) && criterias.hasOneFilterableColumn())) {
                queryBuilder.append(" WHERE ");
            }

            List<String> paramList = new ArrayList<String>();
            Iterator<Entry<String, String>> it = customCondition.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();

                paramList.add(" LOWER(p." + pairs.getKey()
                        + ") LIKE '%?%'".replace("?", pairs.getValue()));
            }

            Iterator<String> itr = paramList.iterator();
            while (itr.hasNext()) {
                queryBuilder.append(itr.next());
                if (itr.hasNext()) {
                    queryBuilder.append(" OR ");
                }
            }
        }


        logger.debug("查询SQL：" + queryBuilder.toString());

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

        TypedQuery<Asset> query = entityManager.createQuery(queryBuilder.toString(), Asset.class);

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
    public Long getFilteredCount(DatatablesCriterias criterias, Map<String, String> customCondition) {

        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM Asset p");

        StringBuilder whereBuilder = DaoUtils.getFilterQuery(criterias);
        queryBuilder.append(whereBuilder);

        if (whereBuilder.length() == 0 && customCondition.size() > 0) {

            if (!(StringUtils.hasLength(criterias.getSearch()) && criterias.hasOneFilterableColumn())) {
                queryBuilder.append(" WHERE ");
            }

            List<String> paramList = new ArrayList<String>();
            Iterator<Entry<String, String>> it = customCondition.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();

                paramList.add(" LOWER(p." + pairs.getKey()
                        + ") LIKE '%?%'".replace("?", pairs.getValue()));
            }

            Iterator<String> itr = paramList.iterator();
            while (itr.hasNext()) {
                queryBuilder.append(itr.next());
                if (itr.hasNext()) {
                    queryBuilder.append(" OR ");
                }
            }
        }

        Query query = entityManager.createQuery(queryBuilder.toString());
        return Long.parseLong(String.valueOf(query.getResultList().size()));
    }

    /**
     * @return the total count of Assets.
     */
    public Long getTotalCount() {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM Asset p");
        return (Long) query.getSingleResult();
    }

}
