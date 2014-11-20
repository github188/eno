package com.energicube.eno.asset.service.impl;

import com.energicube.eno.asset.model.Meter;
import com.energicube.eno.asset.model.MeterGroup;
import com.energicube.eno.asset.model.MeterInGroup;
import com.energicube.eno.asset.repository.MeterGroupRepository;
import com.energicube.eno.asset.repository.MeterInGroupRepository;
import com.energicube.eno.asset.repository.MeterRepository;
import com.energicube.eno.asset.repository.jpa.JpaMeterGroupRepository;
import com.energicube.eno.asset.service.MeterGroupService;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MeterGroupServiceImpl implements MeterGroupService {

    private MeterRepository meterRepository;
    private MeterGroupRepository meterGroupRepository;
    private JpaMeterGroupRepository jpaMeterGroupRepository;
    private MeterInGroupRepository meterInGroupRepository;

    @Autowired
    public MeterGroupServiceImpl(MeterRepository meterRepository,
                                 MeterGroupRepository meterGroupRepository,
                                 MeterInGroupRepository meterInGroupRepository,
                                 JpaMeterGroupRepository jpaMeterGroupRepository) {
        this.meterRepository = meterRepository;
        this.meterGroupRepository = meterGroupRepository;
        this.meterInGroupRepository = meterInGroupRepository;
        this.jpaMeterGroupRepository = jpaMeterGroupRepository;
    }


    @Transactional(readOnly = true)
    public Page<MeterGroup> findAllMeterGroup(Pageable pageable) {
        return meterGroupRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public DataSet<MeterGroup> findMeterGroupsWithDatatablesCriterias(
            DatatablesCriterias criterias) {

        List<MeterGroup> meterGroups = jpaMeterGroupRepository.findMeterGroupWithDatatablesCriterias(criterias);
        Long count = jpaMeterGroupRepository.getTotalCount();
        Long countFiltered = jpaMeterGroupRepository.getFilteredCount(criterias);

        return new DataSet<MeterGroup>(meterGroups, count, countFiltered);
    }


    @Transactional(readOnly = true)
    public boolean existMeterGroup(String groupname) {
        List<MeterGroup> meterGroups = meterGroupRepository.findByGroupname(groupname.toUpperCase());
        if (meterGroups.size() > 0) {
            return true;
        }
        return false;
    }

    @Transactional
    public MeterGroup saveMeterGroup(MeterGroup meterGroup) {
        meterGroup.setGroupname(meterGroup.getGroupname().toUpperCase());
        return meterGroupRepository.save(meterGroup);
    }

    @Transactional(readOnly = true)
    public MeterGroup findOne(Long id) {
        return meterGroupRepository.findOne(id);
    }

    @Transactional
    public void deleteMeterGroup(Long metergroupid) {

        MeterGroup meterGroup = meterGroupRepository.findOne(metergroupid);
        List<MeterInGroup> meterInGroups = meterInGroupRepository.findByGroupnameOrderBySequenceAsc(meterGroup.getGroupname());
        for (MeterInGroup mig : meterInGroups) {
            meterInGroupRepository.delete(mig);
        }
        meterGroupRepository.delete(metergroupid);
    }

    @Transactional(readOnly = true)
    public List<MeterInGroup> findMeterInGroup(String groupname) {
        return meterInGroupRepository.findByGroupnameOrderBySequenceAsc(groupname);
    }

    @Transactional(readOnly = true)
    public boolean existMeternameInGroup(String groupname, String metername) {
        //判断计量仪名称在计量仪表中是否存在
        boolean existMeter = false;
        groupname = groupname.toUpperCase();
        metername = metername.toUpperCase();
        List<Meter> meters = meterRepository.findByMetername(metername);

        if (meters != null && meters.size() > 0) {
            existMeter = true;
        }
        //如果存在则查询对应表中是否存在
        if (existMeter) {
            List<MeterInGroup> list = meterInGroupRepository.findByGroupnameAndMetername(groupname, metername);
            if (list != null && list.size() > 0) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public MeterInGroup saveMeterInGroup(MeterInGroup meterInGroup) {
        return meterInGroupRepository.save(meterInGroup);
    }

    @Transactional
    public void deleteMeterInGroup(long meteringroupid) {
        meterInGroupRepository.delete(meteringroupid);
    }


}
