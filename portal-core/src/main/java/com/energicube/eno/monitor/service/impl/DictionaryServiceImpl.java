package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.OkcDMAlphaNum;
import com.energicube.eno.monitor.repository.OkcDMAlphaNumRepository;
import com.energicube.eno.monitor.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private OkcDMAlphaNumRepository okcDMAlphaNumRepository;

    @Transactional(readOnly = true)
    public Page<OkcDMAlphaNum> findALNDictionaryByDictid(String dictid,
                                                         Pageable pageable) {
        return okcDMAlphaNumRepository.findByDictid(dictid, pageable);
    }


    @Cacheable(value = "okcdmalphanum", key = "#dictid + 'list'")
    @Transactional(readOnly = true)
    public List<OkcDMAlphaNum> findALNDictionaryByDictid(String dictid) {
        return okcDMAlphaNumRepository.findByDictid(dictid);
    }

}
