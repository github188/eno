package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.UcTag;
import com.energicube.eno.monitor.model.UclcCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UclcConditionRepository extends
        JpaRepository<UclcCondition, Integer> {

    @Query("select u.tagname from UcTag u")
    public List<UcTag> findAllUcTagNames();

}
