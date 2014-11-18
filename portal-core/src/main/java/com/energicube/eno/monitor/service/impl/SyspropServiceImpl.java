package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.SysInfo;
import com.energicube.eno.monitor.model.Sysprop;
import com.energicube.eno.monitor.repository.SysInfoRepository;
import com.energicube.eno.monitor.repository.SyspropRepository;
import com.energicube.eno.monitor.service.SyspropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SyspropServiceImpl implements SyspropService {

    private SyspropRepository syspropRepository;

    @Autowired
    public SysInfoRepository sysInfoRepository;

    @Autowired
    public SyspropServiceImpl(SyspropRepository syspropRepository) {
        this.syspropRepository = syspropRepository;
    }

    @Transactional(readOnly = true)
    public List<Sysprop> findAllSysprops() {
        return syspropRepository.findAll();
    }

    @Transactional(readOnly = true)
    public boolean existSysprop(String propname) {
        return syspropRepository.findByPropname(propname).size() > 0;
    }

    @Transactional(readOnly = true)
    public Sysprop findSyspropById(int propid) {

        return this.syspropRepository.findOne(propid);
    }

    @Transactional(readOnly = true)
    public Sysprop findSyspropByName(String propname) {
        List<Sysprop> findByPropname = this.syspropRepository.findByPropname(propname);
        return findByPropname.get(0);
    }

    @Transactional
    public Sysprop saveSysprop(Sysprop sysprop) {

        return this.syspropRepository.save(sysprop);
    }

    @Transactional
    public List<Sysprop> saveSysprops(List<Sysprop> sysprops) {

        return this.syspropRepository.save(sysprops);
    }

    @Transactional
    public void deleteSysprop(Sysprop sysprop) {
        this.syspropRepository.delete(sysprop);
    }

    @Transactional
    public void deleteSysprop(int propid) {
        this.syspropRepository.delete(propid);

    }

    @Override
    public SysInfo findSysInfo() {
        List<SysInfo> sysInfos = sysInfoRepository.findAll();
        if (sysInfos != null && sysInfos.size() > 0) {
            return sysInfos.get(0);
        } else {
            return null;
        }
    }


    @Override
    @Transactional
    public void updateSysInfo(SysInfo sysInfo) {

        if (sysInfo == null) return;
        if (sysInfo != null) {
            String sysname = sysInfo.getSysname();
            String title = sysInfo.getTitle();
            sysInfoRepository.updateSysInfo(sysname, title);
        }
    }
}
