package com.energicube.eno.monitor.repository.jpa;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 停车管理-根据条件查询
 *
 * @author LiHuiHui
 */
@Repository
public class SubParkingRepository {

    private Log logger = LogFactory.getLog(SubParkingRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    // 获得查询where语句
    public String getWhereSql(Map<String, String[]> condition, boolean isEncoding) {
        if (condition.size() > 0) {
            StringBuilder conditionBuilder = new StringBuilder();
            Iterator<Entry<String, String[]>> it = condition.entrySet().iterator();
            String paramValue = null;
            try {
                while (it.hasNext()) {
                    Map.Entry<String, String[]> pairs = (Map.Entry<String, String[]>) it.next();
                    if (StringUtils.hasLength(pairs.getValue()[0])) {// 参数值不为空
                        if (isEncoding) {// 查询,为GET请求，中文乱码，需要转码
                            paramValue = new String(pairs.getValue()[0].getBytes("iso-8859-1"), "utf-8");
                        }
                        if ("startInDate".equals(pairs.getKey())) {
                            conditionBuilder.append("and isnull(park.inDate,'')>='" + paramValue + "'");
                        } else if ("endInDate".equals(pairs.getKey())) {
                            conditionBuilder.append("and isnull(park.inDate,'')<='" + paramValue + " 23:59:59'");
                        } else if ("startOutDate".equals(pairs.getKey())) {
                            conditionBuilder.append("and isnull(park.outDate,'')>='" + paramValue + "'");
                        } else if ("endOutDate".equals(pairs.getKey())) {
                            conditionBuilder.append("and isnull(park.outDate,'')<='" + paramValue + " 23:59:59'");
                        } else if ("carNum".equals(pairs.getKey())) { // 车牌号
                            conditionBuilder.append("and park.carNum like '%" + paramValue + "%'");
                        } else if ("comeName".equals(pairs.getKey())) { // 进场口
                            conditionBuilder.append("and park.comeName like '%" + paramValue + "%'");
                        } else if ("goName".equals(pairs.getKey())) { // 出场口
                            conditionBuilder.append("and park.goName like '%" + paramValue + "%'");
                        }
                    }
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return conditionBuilder.toString();
        }
        return null;
    }

    /**
     * 查询操作信息
     */
    public Page<Object[]> findByCondition(Pageable pageable, Map<String, String[]> condition) {


        int total = 0;
        String conditionSql = "";
        List<Object[]> content = null;
        StringBuilder queryBuilder = new StringBuilder(" from SubParking park  where 1=1 ");

        if (pageable != null) {//query 查询,为GET请求，中文乱码，需要转码，需要返回Page<FireMonitor>。
            conditionSql = getWhereSql(condition, true);
            StringBuilder countBuilder = new StringBuilder("select count(park) from SubParking park  where 1=1 ");
            countBuilder.append(conditionSql);
            Query countQuery = entityManager.createQuery(countBuilder.toString());
            total = Integer.parseInt(countQuery.getSingleResult().toString());
            queryBuilder.append(conditionSql);
            Query query = entityManager.createQuery(queryBuilder.toString());
            int first = pageable.getPageNumber();
            logger.info("----------first--" + first);
            content = query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize()).setMaxResults(pageable.getPageSize()).getResultList();
        }
        return new PageImpl<Object[]>(content, pageable, total);
    }
}
