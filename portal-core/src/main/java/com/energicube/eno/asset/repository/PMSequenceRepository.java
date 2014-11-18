package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.PMSequence;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * PM作业计划序列数据操作接口
 *
 * @author CHENPING
 * @version 1.0
 */
public interface PMSequenceRepository extends JpaRepository<PMSequence, Long> {

    /**
     * 获取指定PM编号的作业计划序列
     *
     * @param pmnum    PM编号
     * @param siteid   站点
     * @param orgid    组织机构
     * @param pageable 分页请求对象 {@link pageable}
     * @return {@link Object} array list
     */
    @Query("select s,jp from PMSequence s,JobPlan jp where s.jpnum=jp.jpnum and "
            + "((s.orgid=?3 and s.siteid=?2) or (s.orgid=?3 and s.siteid is null) "
            + "or (s.orgid is null and s.siteid is null)) and s.pmnum=?1")
    Page<Object[]> findPMSequences(String pmnum, String siteid, String orgid,
                                   Pageable pageable) throws DataAccessException;

    /**
     * 获取指定作业计划编号的PM
     *
     * @param jpnum  {@link JobPlan} num
     * @param pmnum  {@link PM}
     * @param siteid 地点
     * @return PM list
     */
    List<PMSequence> findByJpnumAndPmnumAndSiteid(String jpnum, String pmnum, String siteid) throws DataAccessException;

}
