package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Shifttypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShifttypesRepository extends JpaRepository<Shifttypes, Integer> {

    public List<Shifttypes> findByShifttypeContaining(String shifttype);

    @Query("select a from Shifttypes a order by a.season desc")
    public List<Shifttypes> selectOrderByseasonDesc();

    public List<Shifttypes> findBySeason(String season);

}