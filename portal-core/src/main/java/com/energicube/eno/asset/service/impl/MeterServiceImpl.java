/**
 *
 */
package com.energicube.eno.asset.service.impl;

import com.energicube.eno.asset.model.Meter;
import com.energicube.eno.asset.model.Metertype;
import com.energicube.eno.asset.model.Readingtype;
import com.energicube.eno.asset.repository.MeterRepository;
import com.energicube.eno.asset.repository.jpa.JpaMeterRepository;
import com.energicube.eno.asset.service.MeterService;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CHENPING
 */
@Service
public class MeterServiceImpl implements MeterService {

    private MeterRepository meterRepository;
    private JpaMeterRepository jpaMeterRepository;

    @Autowired
    public MeterServiceImpl(MeterRepository meterRepository,
                            JpaMeterRepository jpaMeterRepository) {
        this.meterRepository = meterRepository;
        this.jpaMeterRepository = jpaMeterRepository;
    }


    @Transactional(readOnly = true)
    public Page<Meter> findAllMeters(Pageable pageable) {
        return meterRepository.findAll(pageable);
    }


    @Transactional(readOnly = true)
    public boolean existMetername(String metername) {
        List<Meter> meters = meterRepository.findByMetername(metername.toUpperCase());
        if (meters != null && meters.size() > 0) {
            return true;
        }
        return false;
    }

    @Transactional
    public Meter saveMeter(Meter meter) {
        meter.setMetername(meter.getMetername().toUpperCase());
        return meterRepository.save(meter);
    }

    @Transactional(readOnly = true)
    public Meter findOne(Long id) {
        return meterRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<Metertype> findMetertypes() {
        List<Metertype> metertypes = new ArrayList<Metertype>();
        metertypes.add(new Metertype("CHARACTERISTIC", "特殊型"));
        metertypes.add(new Metertype("CONTINUOUS", "连续型"));
        metertypes.add(new Metertype("GAUGE", "计量型"));
        return metertypes;
    }

    @Transactional(readOnly = true)
    public List<Readingtype> findReadingtypes() {
        List<Readingtype> readingtypes = new ArrayList<Readingtype>();
        readingtypes.add(new Readingtype("ACTUAL", "Cumulative usage"));
        readingtypes.add(new Readingtype("DELTA", "Incremental usage"));
        return readingtypes;
    }

    @Transactional(readOnly = true)
    public Metertype findMetertypeById(String id) {
        List<Metertype> metertypes = findMetertypes();
        for (Metertype type : metertypes) {
            if (type.getMetertype().equals(id)) {
                return type;
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Readingtype findReadingtypeById(String id) {
        List<Readingtype> readingtypes = findReadingtypes();
        for (Readingtype type : readingtypes) {
            if (type.getReadingtype().equals(id)) {
                return type;
            }
        }
        return null;
    }


    @Transactional(readOnly = true)
    public DataSet<Meter> findMetersWithDatatablesCriterias(
            DatatablesCriterias criterias) {

        List<Meter> meters = jpaMeterRepository.findMeterWithDatatablesCriterias(criterias);
        Long count = jpaMeterRepository.getTotalCount();
        Long countFiltered = jpaMeterRepository.getFilteredCount(criterias);

        return new DataSet<Meter>(meters, count, countFiltered);
    }


}
