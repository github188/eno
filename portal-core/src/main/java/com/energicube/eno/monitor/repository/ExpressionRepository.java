package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Expression;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 表达式
 *
 * @author zhangtianle
 */
public interface ExpressionRepository extends JpaRepository<Expression, Long> {

}
