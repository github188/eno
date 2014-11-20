package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.DeviceConfig;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeviceConfigRepository extends JpaRepository<DeviceConfig, Integer> {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     * @throws DataAccessException
     */
    @Query("select dc from DeviceConfig dc where dc.id=?1 order by dc.id asc")
    public DeviceConfig findById(int id) throws DataAccessException;

    /**
     * 根据system和classid找到配置信息
     *
     * @param system
     * @param classid
     * @return
     */
    public DeviceConfig findBySystemAndClassid(String system, String classid);

}
