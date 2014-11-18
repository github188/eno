package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Example;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExampleRepository extends JpaRepository<Example, Long> {

    /**
     * 查找FirstName相同的数据
     *
     * @param firstName
     * @return 列表
     */
    public List<Example> findByFirstName(String firstName) throws DataAccessException;


}
