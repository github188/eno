package com.energicube.eno.asset.repository.jpa;

import com.energicube.eno.asset.model.AssetMeter;
import com.energicube.eno.asset.model.AssetMeterSet;
import com.energicube.eno.asset.model.Meter;
import com.energicube.eno.common.datatables.jpa.util.DaoUtils;
import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class JpaAssetMeterRepository {

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
    public List<AssetMeterSet> findAssetWithDatatablesCriterias(DatatablesCriterias criterias, String assetnum, String siteid) {

        //@Query("select assetMeter,meter from AssetMeter assetMeter, Meter meter where assetMeter.metername=meter.metername and assetMeter.assetnum=? and assetMeter.siteid=?")
        if (siteid == null)
            siteid = "";
        StringBuilder queryBuilder = new StringBuilder("SELECT p,m FROM AssetMeter p,Meter m WHERE p.metername=m.metername AND p.assetnum='" + assetnum + "' and coalesce(p.siteid,'')='" + siteid + "'");

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

        Query query = entityManager.createQuery(queryBuilder.toString());

        /**
         * 4th step : paging
         */
        //query.setFirstResult(criterias.getDisplayStart());
        //query.setMaxResults(criterias.getDisplaySize());

        List<AssetMeterSet> assetMeterSets = new ArrayList<AssetMeterSet>();
        @SuppressWarnings("unchecked")
        List<Object[]> list = query.getResultList();
        for (Object[] obj : list) {
            AssetMeterSet assetMeterSet = new AssetMeterSet();
            if (obj[0] instanceof AssetMeter) {
                assetMeterSet.setAssetMeter((AssetMeter) obj[0]);
            }
            if (obj[1] instanceof Meter) {
                assetMeterSet.setMeter((Meter) obj[1]);
            }
            assetMeterSets.add(assetMeterSet);
        }
        return assetMeterSets;

    }

    /**
     * <p/>
     * Query used to return the number of filtered Assets.
     *
     * @param criterias The DataTables criterias used to filter the Assets.
     *                  (maxResult, filtering, paging, ...)
     * @return the number of filtered Assets.
     */
    public Long getFilteredCount(DatatablesCriterias criterias, String assetnum, String siteid) {

        StringBuilder queryBuilder = new StringBuilder("SELECT p,m FROM AssetMeter p,Meter m WHERE p.metername=m.metername AND p.assetnum='" + assetnum + "' and coalesce(p.siteid,'')='" + siteid + "'");

        queryBuilder.append(DaoUtils.getFilterQuery(criterias));

        Query query = entityManager.createQuery(queryBuilder.toString());
        return Long.parseLong(String.valueOf(query.getResultList().size()));
    }

    /**
     * @return the total count of Assets.
     */
    public Long getTotalCount(String assetnum, String siteid) {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM AssetMeter p,Meter m WHERE p.metername=m.metername AND p.assetnum='" + assetnum + "' and coalesce(p.siteid,'')='" + siteid + "'");
        return (Long) query.getSingleResult();
    }
}
