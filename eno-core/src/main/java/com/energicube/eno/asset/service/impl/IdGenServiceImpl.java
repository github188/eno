package com.energicube.eno.asset.service.impl;

import com.energicube.eno.asset.model.IdGen;
import com.energicube.eno.asset.repository.IdGenRepository;
import com.energicube.eno.asset.service.IdGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class IdGenServiceImpl implements IdGenService {

    @Autowired
    private IdGenRepository idGenRepository;

    @Transactional(readOnly = true)
    public IdGen findOne(String id) {
        return idGenRepository.findOne(id);
    }

    @Transactional
    public String generateNewId(String id) {
        return generateNewId(id, 0l);
    }

    @Transactional
    public String generateNewId(String id, long initValue) {
        return generateNewId(id, "", 0, initValue, "");
    }


    @Transactional
    public long updateNewId(String id) {
        IdGen idGen = idGenRepository.findOne(id);
        idGen.setGenVal(idGen.getGenVal() + 1);
        idGen = idGenRepository.saveAndFlush(idGen);
        return idGen.getGenVal();
    }

    @Transactional
    public String generateNewId(String key, String prefix, int seedlength,
                                long seedstart, String separator) {
        return generateAndUpdateNewId(key, prefix, seedlength, seedstart, separator, true);
    }

    @Transactional
    public String generateAndUpdateNewId(String key, String prefix,
                                         int seedlength, long seedstart, String separator, boolean update) {
        IdGen idGen = idGenRepository.findOne(key);
        String m_prefix = "", m_separator = "", m_aifield = "", m_aitype = "1";
        int m_seedlength = 0;
        if (key.indexOf("#") > -1) {
            m_aifield = key.split("#")[1];
            m_aitype = "2";
        }
        if (idGen == null) {
            idGen = new IdGen();
            idGen.setGenName(key);
            idGen.setPrefix(prefix);
            idGen.setSeedlength(seedlength);
            idGen.setSeedstart(seedstart);
            idGen.setSeparator(separator);
            idGen.setAifield(m_aifield);
            idGen.setAitype(m_aitype);
            if (seedstart == 0) {
                idGen.setGenVal(0L);
            } else {
                idGen.setGenVal(seedstart);
            }
            m_prefix = prefix;
            m_separator = separator;
            m_seedlength = seedlength;
            idGen = idGenRepository.saveAndFlush(idGen);
        } else {
            if (StringUtils.hasLength(idGen.getPrefix())) {
                m_prefix = idGen.getPrefix();
            }
            if (StringUtils.hasLength(idGen.getSeparator())) {
                m_separator = idGen.getSeparator();
            }
            m_seedlength = idGen.getSeedlength();
        }
        long newId = idGen.getGenVal() + 1;
        String result = "";
        if (StringUtils.hasLength(m_prefix)) {
            result += m_prefix;
        }
        if (StringUtils.hasLength(m_separator)) {
            result += m_separator;
        }
        if (m_seedlength > 0) {
            result += com.energicube.eno.common.util.StringUtils.fillString(m_seedlength, newId);
        } else {
            result += String.valueOf(newId);
        }
        idGen.setGenVal(newId);
        if (update) {
            idGen = idGenRepository.saveAndFlush(idGen);
        }
        return result;
    }


}
