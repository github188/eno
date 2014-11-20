package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.repository.OrganizationRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final static Log logger = LogFactory.getLog(OrganizationServiceImpl.class);

    @Autowired
    private OrganizationRepository organizationRepository;
}
