package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.OkcDMAlphaNum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 数据字典业务操作类
 */
public interface DictionaryService {


    /**
     * 获取数字型数据映射字典
     *
     * @param dictid   字典ID
     * @param pageable 分页参数
     * @return 数字型数据映射字典分页列表
     */
    public Page<OkcDMAlphaNum> findALNDictionaryByDictid(String dictid, Pageable pageable);

    /**
     * 获取字母和数字型数据映射
     *
     * @param dictid 字典ID
     * @return 数字型数据映射字典分页列表
     */
    public List<OkcDMAlphaNum> findALNDictionaryByDictid(String dictid);


}
