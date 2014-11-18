package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.Shifts;
import com.energicube.eno.monitor.repository.ShiftsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiftsServiceImpl implements ShiftsService {

    @Autowired
    private ShiftsRepository shiftsRepository;

    public void shiftsSave(Shifts shifts) {
        shiftsRepository.save(shifts);
    }
}
