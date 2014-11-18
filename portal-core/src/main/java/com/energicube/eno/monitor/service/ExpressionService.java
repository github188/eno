package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.Expression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 对表达式操作
 *
 * @author zhan
 */
public interface ExpressionService {

    /**
     * 获取所有表达式
     *
     * @return
     */
    List<Expression> findAllExp();

    /**
     * 根据编号查找表达式
     *
     * @param expindex
     * @return
     */
    Expression findOneExpression(long expindex);

    /**
     * 批量保存表达式
     *
     * @param expressions
     */
    void saveExpressions(List<Expression> expressions);

    /**
     * 保存一个
     *
     * @param expressions
     */
    void saveOne(Expression expression) throws Exception;

    /**
     * 删除所有表达式
     */
    void deleteAll();

    /**
     * 分页查找表达式
     *
     * @param pageable
     * @return
     */
    Page<Expression> findByPage(Pageable pageable);

    /**
     * 删除操作
     *
     * @param expindex
     */
    void deleteOne(long expindex);
}
