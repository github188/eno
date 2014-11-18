package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.OkcDMAlphaNum;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 字母和数字型数据映射数据字典
 */
public interface OkcDMAlphaNumRepository extends
        JpaRepository<OkcDMAlphaNum, Long> {

    /**
     * 查找字典项
     *
     * @param dictid   字典代码
     * @param pageable 分页请求参数
     * @return 数据字典分页数据
     */
    Page<OkcDMAlphaNum> findByDictid(String dictid, Pageable pageable) throws DataAccessException;

    /**
     * 查找字典项
     *
     * @param dictid 字典代码
     * @return 数据字典分页数据
     */
    List<OkcDMAlphaNum> findByDictid(String dictid) throws DataAccessException;

    @Query("select a from OkcDMAlphaNum a where a.dictid=?1 and a.description=?2")
    List<OkcDMAlphaNum> findByDictidAndDescription(String dictid, String description) throws DataAccessException;

    @Query("select a from OkcDMAlphaNum a where a.dictid=?1 and a.dmvalue=?2")
    List<OkcDMAlphaNum> findByDictidAndDmvalue(String dictid, String description) throws DataAccessException;
}
