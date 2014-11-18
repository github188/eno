package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.Syscontrol;
import com.energicube.eno.monitor.repository.PagetagRepository;
import com.energicube.eno.monitor.repository.SyscontrolRepository;
import com.energicube.eno.monitor.service.SyscontrolService;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;


@Service
public class SyscontrolServiceImpl implements SyscontrolService {

    @Autowired
    private SyscontrolRepository syscontrolRepository;

    @Autowired
    private PagetagRepository pagetagRepository;


    @CacheEvict(value = "syscontrols", allEntries = true)
    @Transactional
    public boolean deleteSyscontrol(int controluid) {
        Syscontrol syscontrol = syscontrolRepository.findOne(controluid);
        if (syscontrol != null) {
            long count = pagetagRepository.countByControlids(syscontrol.getControlid());
            if (count > 0) {
                return false;
            } else {
                syscontrolRepository.delete(controluid);
                return true;
            }
        }
        return false;

    }

    @CacheEvict(value = "syscontrols", allEntries = true)
    @Transactional
    public Syscontrol saveSyscontrol(Syscontrol syscontrol) {
        syscontrol.setControlid(syscontrol.getControlid().toUpperCase());
        if (!StringUtils.hasLength(syscontrol.getSettting())) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                syscontrol.setSettting(mapper.writeValueAsString(syscontrol));
            } catch (JsonGenerationException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return syscontrolRepository.save(syscontrol);
    }

    @Cacheable(value = "syscontrols")
    @Transactional(readOnly = true)
    public List<Syscontrol> findAll() {
        return syscontrolRepository.findAll(new Sort(Direction.ASC, "controluid"));
    }

    @Transactional(readOnly = true)
    public Syscontrol findOne(int controluid) {
        if (controluid <= 0)
            return null;
        return syscontrolRepository.findOne(controluid);
    }

}
