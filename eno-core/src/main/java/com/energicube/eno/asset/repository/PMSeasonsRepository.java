package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.PMSeasons;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * PM季节性日期数据操作接口
 *
 * @author CHENPING 2013-09-04
 * @version 1.0
 */
public interface PMSeasonsRepository extends JpaRepository<PMSeasons, Long> {

    /**
     * 查找指定PM编号和地点的季节性日期设置项
     *
     * @param pmnum    PM编号
     * @param siteid   地点
     * @param pageable 分页请求参数
     * @return a paging list of {@link PMSeasons} object
     */
    Page<PMSeasons> findByPmnumAndSiteid(String pmnum, String siteid, Pageable pageable) throws DataAccessException;

    /**
     * 查找指定PM相同日期的季节性日期设置
     *
     * @param pmnum      PM编号
     * @param siteid     地点
     * @param endday     标识PM的季节结束日，位于结束月中的哪天
     * @param endmonth   确定PM的季节结束月份，可选值从一月到十二月
     * @param startday   标识PM的季节开始日，位于开始月中的哪天
     * @param startmonth 标识PM的季节开始月份，可选值从一月到十二月
     */
    @Query("select s from PMSeasons s where s.pmnum=?1 and s.siteid=?2 and s.endday=?3 and s.endmonth=?4 and s.startday=?5 and s.startmonth=?6")
    List<PMSeasons> findByDays(String pmnum, String siteid, int endday, String endmonth, int startday, String startmonth) throws DataAccessException;
}
