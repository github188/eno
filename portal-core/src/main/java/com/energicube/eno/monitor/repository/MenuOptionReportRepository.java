package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.MenuOption;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuOptionReportRepository extends JpaRepository<MenuOption, Long> {
    @Query("select a from  MenuOption a where a.okcmenuid = ?1 and  a.groupname = ?2")
    public List<MenuOption> findByOkcmenuidAndGroupId(String okcmenuId, String groupId) throws DataAccessException;

//	@Query("select a from  MenuOption a where a.okcmenuid = ?1 and  a.groupname = ?2 and a.optionname = ?3") 
//	public List<MenuOption> findByOkcmenuidAndGroupIdAndOptionname(String okcmenuId, String groupId, String optionname) throws DataAccessException;

    public List<MenuOption> findByGroupname(String groupname) throws DataAccessException;
}