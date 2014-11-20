package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.FailureCode;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FailureCodeRepository extends JpaRepository<FailureCode, Long> {

    public List<FailureCode> findByFailurecode(String failurecode) throws DataAccessException;

    @Query("select code from FailureCode code,FailureList list where code.failurecode=list.failurecode and (list.parent is null or list.parent=0)")
    public Page<FailureCode> findByList(Pageable pageable) throws DataAccessException;

}
