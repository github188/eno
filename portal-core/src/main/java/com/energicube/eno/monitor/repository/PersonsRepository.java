package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Persons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonsRepository extends JpaRepository<Persons, Long> {

    public Persons findByUserid(String userid);

    public List<Persons> findByFirstnameContaining(String name);

    @Query("select a from Persons a where a.userid in (select b.userid from Users b where b.status = '0' or b.status = '1') order by a.personid desc")
    public List<Persons> findPersons();

    @Query("select a from Persons a where a.userid in (select b.userid from Users b where b.status = ?1) order by a.personid desc")
    public List<Persons> findPersonsBySta(String status);
}