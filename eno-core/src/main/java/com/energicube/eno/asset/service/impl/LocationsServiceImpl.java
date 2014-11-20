package com.energicube.eno.asset.service.impl;

import com.energicube.eno.asset.model.*;
import com.energicube.eno.asset.repository.LocHierarchyRepository;
import com.energicube.eno.asset.repository.LocSystemRepository;
import com.energicube.eno.asset.repository.LocationMeterRepository;
import com.energicube.eno.asset.repository.LocationsRepository;
import com.energicube.eno.asset.repository.jpa.JpaLocationsRepository;
import com.energicube.eno.asset.service.LocationsService;
import com.energicube.eno.common.model.State;
import com.energicube.eno.common.model.Tree;
import com.energicube.eno.common.util.TreeUtil;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationsServiceImpl implements LocationsService {

    private static final Log logger = LogFactory.getLog(LocationsServiceImpl.class);
    private static final int DIALOG_PAGE_SIZE = 10;

    private LocationsRepository locationsRepository;
    private LocSystemRepository locSystemRepository;
    private LocationMeterRepository locationMeterRepository;
    private LocHierarchyRepository locHierarchyRepository;
    private JpaLocationsRepository jpaLocationsRepository;

    @Autowired
    public LocationsServiceImpl(LocationsRepository locationsRepository,
                                LocSystemRepository locSystemRepository,
                                LocationMeterRepository locationMeterRepository,
                                LocHierarchyRepository locHierarchyRepository,
                                JpaLocationsRepository jpaLocationsRepository) {
        this.locationsRepository = locationsRepository;
        this.locSystemRepository = locSystemRepository;
        this.locationMeterRepository = locationMeterRepository;
        this.locHierarchyRepository = locHierarchyRepository;
        this.jpaLocationsRepository = jpaLocationsRepository;
    }

    @Transactional(readOnly = true)
    public Page<Locations> findAllLocations(Integer pageNumber) {
        PageRequest request = new PageRequest(pageNumber - 1, DIALOG_PAGE_SIZE);
        return locationsRepository.findAll(request);
    }

    @Transactional(readOnly = true)
    public Page<Locations> findAllLocations(Pageable pageable) {
        return locationsRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public DataSet<Locations> findLocationsWithDatatablesCriterias(
            DatatablesCriterias criterias) {
        List<Locations> locations = jpaLocationsRepository.findLocationsWithDatatablesCriterias(criterias);
        Long count = jpaLocationsRepository.getTotalCount();
        Long countFiltered = jpaLocationsRepository.getFilteredCount(criterias);

        return new DataSet<Locations>(locations, count, countFiltered);
    }

    @Transactional(readOnly = true)
    public Locations findOne(long locationsid) {
        if (locationsid == 0)
            return null;

        try {
            return locationsRepository.findOne(locationsid);
        } catch (Exception e) {
            logger.error(e);
            return null;

        }

    }

    @Transactional(readOnly = true)
    public boolean existLocation(String location) {
        List<Locations> list = locationsRepository.findByLocation(location.toUpperCase());
        if (list == null)
            return false;
        if (list.size() > 0)
            return true;
        return false;
    }

    @Transactional
    public Locations saveLocations(Locations locations) {

        return locationsRepository.save(locations);
    }

    @Transactional(readOnly = true)
    public Page<LocSystem> findAllLocSystem(Integer pageNumber) {
        PageRequest request = new PageRequest(pageNumber - 1, DIALOG_PAGE_SIZE);
        return locSystemRepository.findAll(request);
    }

    @Transactional
    public LocSystem saveLocSystem(LocSystem locSystem) {
        locSystem.setSystemid(locSystem.getSystemid().toUpperCase());
        return locSystemRepository.save(locSystem);
    }

    @Transactional(readOnly = true)
    public boolean existLocSystem(String systemid) {
        List<LocSystem> list = locSystemRepository.findBySystemid(systemid);
        if (list == null)
            return false;
        if (list.size() > 0)
            return true;
        return false;
    }

    @Transactional(readOnly = true)
    public List<LocationMeter> findMeterByLocation(String location) {
        if (!StringUtils.hasLength(location)) {
            return null;
        }
        return locationMeterRepository.findByLocation(location);
    }

    @Transactional(readOnly = true)
    public List<LocationMeter> findByLocationAndMetername(String location,
                                                          String metername) {
        return locationMeterRepository.findByLocationAndMetername(location, metername);
    }

    @Transactional(readOnly = true)
    public boolean existMeterInLocationMeter(String location, String metername) {
        List<LocationMeter> list = locationMeterRepository.findByLocationAndMetername(location, metername);
        if (list == null)
            return false;
        if (list.size() > 0)
            return true;
        return false;
    }

    @Transactional
    public LocationMeter saveLocationMeter(LocationMeter locationMeter) {
        return locationMeterRepository.save(locationMeter);
    }

    @Transactional(readOnly = true)
    public List<LocHierarchySet> findLocHierarchyList(String siteid) {
        List<LocHierarchySet> list = new ArrayList<LocHierarchySet>();
        for (Object[] arrObj : locHierarchyRepository.findLocHierarchyList(siteid)) {
            LocHierarchySet locHierarchySet = new LocHierarchySet();
            for (Object obj : arrObj) {
                if (obj instanceof LocHierarchy) {
                    locHierarchySet.setLocHierarchy((LocHierarchy) obj);
                }
                if (obj instanceof Locations) {
                    locHierarchySet.setLocations((Locations) obj);
                }
            }
            list.add(locHierarchySet);
        }
        return list;
    }

    @Transactional(readOnly = true)
    public List<Tree> getLocHierarchyTree(String siteid) {
        List<Tree> treeList = new ArrayList<Tree>();
        List<LocHierarchySet> locHierarchyList = findLocHierarchyList(siteid);
        for (LocHierarchySet locHierarchySet : locHierarchyList) {
            Tree tree = transformTree(locHierarchySet);
            treeList.add(tree);
        }
        return TreeUtil.build(treeList);
    }

    private Tree transformTree(LocHierarchySet locHierarchySet) {
        Tree tree = new Tree();
        tree.setId(locHierarchySet.getLocations().getLocation());
        String text = locHierarchySet.getLocations().getDescription();
        if (!StringUtils.hasLength(text)) {
            text = locHierarchySet.getLocations().getLocation();
        }
        tree.setText(text);
        tree.setParentid(locHierarchySet.getLocHierarchy().getParent());
        tree.setState(State.colsed);
        tree.setNodetype("LOCATION");
        return tree;
    }


}

