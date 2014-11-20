package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.Expression;
import com.energicube.eno.monitor.repository.ExpressionRepository;
import com.energicube.eno.monitor.service.ExpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExpressionServiceImpl implements ExpressionService {

    @Autowired
    private ExpressionRepository expressionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Expression> findAllExp() {
        //按名称排序
        Sort sort = new Sort(Direction.ASC, "expname");
        return expressionRepository.findAll(sort);
    }

    @Override
    @Transactional(readOnly = true)
    public Expression findOneExpression(long expindex) {
        return expressionRepository.findOne(expindex);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveExpressions(List<Expression> expressions) {
        expressionRepository.save(expressions);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll() {
        expressionRepository.deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Expression> findByPage(Pageable pageable) {
        return expressionRepository.findAll(pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(Expression expression) throws Exception {
        expressionRepository.saveAndFlush(expression);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOne(long expindex) {
        expressionRepository.delete(expindex);
    }


}
