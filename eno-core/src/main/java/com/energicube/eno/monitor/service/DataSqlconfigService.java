package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.DataSql;
import org.springframework.data.domain.Page;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * SQL配置server接口
 */
public interface DataSqlconfigService {
    /**
     * 分页查询
     */
    Page<DataSql> findByPage(int page, int size);

    /**
     * 添加
     */
    void saveDataSqlconfig(DataSql dataSql);

    /**
     * 删除
     */
    void deleteDataSqlconfig(int id);

    /**
     * 根据主键查找一个
     */
    DataSql findOne(int id);

    /**
     * 查找所有
     */
    List<DataSql> findAll();

    /**
     * 根据外键查找一个
     */
    DataSql findOneByFk(String fk);

    /**
     * 根据应用项查询对应数据
     */
    List<Map<String, String>> getConfigSqlList(Connection conn, String sql, Object[] objs);

}
