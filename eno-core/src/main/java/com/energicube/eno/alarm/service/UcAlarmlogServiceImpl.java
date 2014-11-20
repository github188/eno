package com.energicube.eno.alarm.service;

import com.energicube.eno.alarm.model.UcAlarmlog;
import com.energicube.eno.alarm.repository.UcAlarmlogRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Service
public class UcAlarmlogServiceImpl implements UcAlarmlogService {

    private static final Log logger = LogFactory.getLog(UcAlarmlogServiceImpl.class);

    @Autowired
    private UcAlarmlogRepository ucAlarmlogRepository;

    @Transactional
    public void batchSaveUcAlarmlog(int almlogid) {
        if (almlogid > 0) {

            logger.info("update history alarm msg in a data table,almlogid:" + almlogid);

            //设置默认时区，确保时间的正确性
            DateTimeZone.setDefault(DateTimeZone.forID("Asia/Shanghai"));

            //查找实时报警对应的报警历史条目，更新应答时间
            List<UcAlarmlog> list = ucAlarmlogRepository.findByAlmlogid(almlogid);
            if (list == null || list.size() == 0) {
                return;
            }
            for (UcAlarmlog ucAlarmlog : list) {

                ucAlarmlog.setAckt(DateTime.now(DateTimeZone.getDefault()));
                ucAlarmlog.setRett(DateTime.now(DateTimeZone.getDefault()));
                ucAlarmlog.setReviewt(DateTime.now(DateTimeZone.getDefault()).toDate());
                ucAlarmlogRepository.save(ucAlarmlog);
            }
        }
    }
    /**
     * 根据子系统,报警级别，分页查询某段时间的报警记录
     * @param startDate
     * @param endDate
     * @param groupName
     * @param almpriority
     * @return
     */
    public Page<UcAlarmlog> searchUcAlarmlogList(String groupName, int almpriority, Date startDate, Date endDate, Pageable pageable){
        return ucAlarmlogRepository.findAll(Specifications.where(getAlarmlogWhereClause(groupName, almpriority, startDate, endDate)),pageable);
    }

    /**
     * 根据子系统,报警级别，查询某段时间的报警记录
     * @param startDate
     * @param endDate
     * @param groupName
     * @param almpriority
     * @return
     */
    public List<UcAlarmlog> searchUcAlarmlogList(String groupName, int almpriority, Date startDate, Date endDate){
        return ucAlarmlogRepository.findAll(Specifications.where(getAlarmlogWhereClause(groupName, almpriority, startDate, endDate)), new Sort(Sort.Direction.DESC, "almt"));
    }
    /**
     * 动态组装查询条件
     * @param groupName
     * @param almpriority
     * @param startDate
     * @param endDate
     * @return
     */
    private Specification<UcAlarmlog> getAlarmlogWhereClause(final String groupName, final int almpriority, final Date startDate, final Date endDate) {
        return new Specification<UcAlarmlog>() {
            @Override
            public Predicate toPredicate(Root<UcAlarmlog> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtils.isNotBlank(groupName)) {
                    predicate.getExpressions().add(
                            cb.equal(r.<String>get("groupname"), groupName)
                    );
                }
                if (StringUtils.isNotBlank(String.valueOf(almpriority)) && almpriority != 0) {
                    predicate.getExpressions().add(
                            cb.equal(r.<Integer>get("almpriority"), almpriority)
                    );
                }
                if (startDate != null && endDate == null){
                    predicate.getExpressions().add(
                            cb.greaterThanOrEqualTo(r.<Date>get("almt"), startDate)
                    );
                }else if (startDate == null && endDate != null){
                    predicate.getExpressions().add(
                            cb.lessThan(r.<Date>get("almt"), endDate)
                    );
                }else if (startDate != null && endDate != null){
                    predicate.getExpressions().add(
                            cb.greaterThanOrEqualTo(r.<Date>get("almt"), startDate)
                    );
                    predicate.getExpressions().add(
                            cb.lessThan(r.<Date>get("almt"), endDate)
                    );
                }else {
                    logger.info("没有输入日期查询条件!");
                }
                return predicate;
            }
        };
    }
}
