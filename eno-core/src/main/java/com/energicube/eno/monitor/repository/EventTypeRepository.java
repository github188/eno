package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {


}
