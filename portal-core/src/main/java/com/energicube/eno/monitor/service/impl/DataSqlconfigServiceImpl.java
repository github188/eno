package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.DataSql;
import com.energicube.eno.monitor.repository.DataSqlconfigRepository;
import com.energicube.eno.monitor.service.DataSqlconfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SQL配置server实现类
 */
@Service
public class DataSqlconfigServiceImpl implements DataSqlconfigService {

    @Autowired
    private DataSqlconfigRepository dataSqlconfigRepository;

    /**
     * 分页查询
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DataSql> findByPage(int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        Page<DataSql> dataSql = dataSqlconfigRepository.findAll(pageable);
        return dataSql;
    }

    /**
     * 添加
     */
    @Override
    @Transactional
    public void saveDataSqlconfig(DataSql dataSql) {
        dataSqlconfigRepository.save(dataSql);

    }

    /**
     * 删除
     */
    @Override
    @Transactional
    public void deleteDataSqlconfig(int id) {
        dataSqlconfigRepository.delete(id);

    }

    /**
     * 根据configid查找一个
     */
    @Override
    @Transactional(readOnly = true)
    public DataSql findOne(int id) {
        return dataSqlconfigRepository.findOne(id);
    }

    /**
     * 查找所有
     */
    @Override
    @Transactional(readOnly = true)
    public List<DataSql> findAll() {
        return dataSqlconfigRepository.findAll();
    }

    /**
     * 根据外键查找一个
     */
    @Override
    @Transactional(readOnly = true)
    public DataSql findOneByFk(String fk) {
        List<DataSql> list = dataSqlconfigRepository.findByDatasqlid(fk);
        if (list != null)
            return list.get(0);
        return null;
    }

    /**
     * 根据应用项查询对应数据
     */
    public List<Map<String, String>> getConfigSqlList(Connection conn, String sql, Object[] objs) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            pstmt = conn.prepareStatement(sql);

            if (null != objs && objs.length > 0) {

                for (int i = 0; i < objs.length; i++) {
                    pstmt.setString(i + 1, (String) objs[i]);
                }
            }

            rs = pstmt.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount(); //列的总数

            String key = "";

            String value = ""; //构造返回的结果集

            Map<String, String> map = null;

            while (rs.next()) {
                map = new HashMap<String, String>();
                for (int i = 1; i <= columnCount; i++) {
                    key = rsmd.getColumnName(i);
                    if (rs.getString(key) != null) {
                        value = rs.getString(key);
                    } else {
                        value = "";
                    }
                    map.put(key, value);
                }
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (null != rs) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (null != pstmt) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
