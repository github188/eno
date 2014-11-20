package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    public List<Department> findByParent(String parent);

    public List<Department> findByDeptnumContaining(String deptnum);

    @Transactional
    @Modifying
    @Query("update Department a set a.parent = ?2 , a.description = ?3, a.deptnum = ?4  where a.cudeptid = ?1")
    public void updateDepartment(int cudeptid, String parent, String description, String deptnum);
}