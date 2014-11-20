package com.energicube.eno.alarm.repository;

import com.energicube.eno.alarm.model.AlarmReport;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-10-18
 * Time: 下午9:32
 * To change this template use File | Settings | File Templates.
 */
public interface AlarmReportRepository extends JpaRepository<AlarmReport, Long> {
}
