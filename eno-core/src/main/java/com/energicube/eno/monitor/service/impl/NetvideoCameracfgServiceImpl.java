package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.common.FileUtil;
import com.energicube.eno.monitor.model.NetvideoCameracfg;
import com.energicube.eno.monitor.repository.NetvideoCameracfgRepository;
import com.energicube.eno.monitor.service.NetvideoCameracfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
public class NetvideoCameracfgServiceImpl implements NetvideoCameracfgService {


    private NetvideoCameracfgRepository cameracfgRepository;

    @Autowired
    public NetvideoCameracfgServiceImpl(NetvideoCameracfgRepository cameracfgRepository) {
        this.cameracfgRepository = cameracfgRepository;
    }

    @Transactional(readOnly = true)
    public List<NetvideoCameracfg> findNetvideoCameracfgs() {
        Sort sort = new Sort(Direction.ASC, "displaysequence");
        return cameracfgRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    public NetvideoCameracfg findNetvideoCameraByCameracfgid(Integer cameracfgid) {
        return cameracfgRepository.findOne(cameracfgid);
    }

    @Transactional
    public void saveNetvideoCameracfg(NetvideoCameracfg cameracfg) {
        cameracfgRepository.save(cameracfg);
    }

    @Transactional
    public void deleteNetvideoCameracfg(NetvideoCameracfg cameracfg) {
        cameracfgRepository.delete(cameracfg);
    }

    @Transactional
    public void deleteNetvideoCameracfg(Integer cameracfgid) {
        cameracfgRepository.delete(cameracfgid);
    }

    @Override
    public String readconfig(String string, HttpServletRequest request) {
        return FileUtil.readConfig(string, request);
    }

    @Override
    public List<NetvideoCameracfg> findSortNetvideoCameracfgs(Sort sort) {
        return cameracfgRepository.findAll(sort);
    }

    @Override
    public void deleteAllCamera() {
        cameracfgRepository.deleteAll();
    }

    @Override
    public void saveGroupinfo(String group, String string, HttpServletRequest request) {
        FileUtil.saveconfigToFile(group, string, request);
    }

    @Override
    public String readGroupinfo(String string, HttpServletRequest request) {
        return FileUtil.readConfig(string, request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll() {
        cameracfgRepository.deleteAll();
    }

    @Override
    public void saveConfigs(List<NetvideoCameracfg> cameracfgs) {
        cameracfgRepository.save(cameracfgs);
    }

}
