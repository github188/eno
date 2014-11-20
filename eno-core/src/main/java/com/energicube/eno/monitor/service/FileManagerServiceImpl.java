package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.repository.DocinfoRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileManagerServiceImpl implements FileManagerService {
    private final static Log logger = LogFactory.getLog(FileManagerServiceImpl.class);

    @Autowired
    private DocinfoRepository docinfoRepository;
}
