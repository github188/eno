package com.energicube.eno.fssm.repository.jpa;

import com.energicube.eno.fssm.model.UcAlarmactiveSet;
import com.energicube.eno.alarm.model.UcAlarmactive;
import com.energicube.eno.monitor.model.UcTaginfoExt;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

@Repository
public class JpaFireMonitorRepository {

    private static final Log logger = LogFactory.getLog(JpaFireMonitorRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查找所有实时报警数据
     *
     * @author CHENPING
     */
    public List<UcAlarmactiveSet> findAlarmActivelog() {

        List<UcAlarmactiveSet> ucAlarmactiveSets = null;

        //只查找报警组为消防系统且状态为0的数据
        String sql = "SELECT active FROM UcAlarmactive active where active.groupname='消防系统' order by active.almt asc";
        Query query = entityManager.createQuery(sql, UcAlarmactive.class);
        List ret = query.getResultList();
        if (ret != null && ret.size() > 0) {
            ucAlarmactiveSets = new LinkedList<UcAlarmactiveSet>();
            for (int i = 0; i < ret.size(); i++) {
                //定义集合对象
                UcAlarmactiveSet ucAlarmactiveSet = new UcAlarmactiveSet();

                //设置实时报警数据对象
                UcAlarmactive ucAlarmactive = (UcAlarmactive) ret.get(i);
                ucAlarmactiveSet.setUcAlarmactive(ucAlarmactive);

                //查找并设置报警点扩展信息
                String extSql = "SELECT ext FROM UcTaginfoExt ext where ext.tagid=:tagid";
                Query query2 = entityManager.createQuery(extSql, UcTaginfoExt.class);
                query2.setParameter("tagid", ucAlarmactive.getTagid());
                query2.setFirstResult(0);
                query2.setMaxResults(1);
                if (query2.getResultList().size() > 0) {
                    Object ret2 = query2.getSingleResult();
                    if (ret2 != null) {
                        UcTaginfoExt ucTaginfoExt = (UcTaginfoExt) ret2;
                        ucAlarmactiveSet.setUcTaginfoExt(ucTaginfoExt);
                    }
                }

                //添加集合到LIST列表
                ucAlarmactiveSets.add(ucAlarmactiveSet);

            }

        }
        return ucAlarmactiveSets;
    }
}
