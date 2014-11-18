package com.energicube.eno.common.procedure;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.object.StoredProcedure;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LiuGuanglu
 * @date 2014/5/30.
 */
public class SQLStoredProcedure extends StoredProcedure {
    private Map<String, Object> parameters = new HashMap<String, Object>();

    public SQLStoredProcedure(DataSource ds, String spName) {
        super(ds, spName);
    }

    public SQLStoredProcedure(JdbcTemplate jdbcTemplate, String spName) {
        super(jdbcTemplate, spName);
    }

    public void addParameter(String name, String value) {
        super.declareParameter(new SqlParameter(name, Types.VARCHAR));
        parameters.put(name, value);
    }

    public void addParameter(String name, int value) {
        super.declareParameter(new SqlParameter(name, Types.INTEGER));
        parameters.put(name, value);
    }

    public void addParameter(String name, double value) {
        super.declareParameter(new SqlParameter(name, Types.DOUBLE));
        parameters.put(name, value);
    }

    public void addParameter(String name, Date value) {
        super.declareParameter(new SqlParameter(name, Types.TIMESTAMP));
        parameters.put(name, value);
    }

    public void addInOutParameter(String name, String value) {
        super.declareParameter(new SqlInOutParameter(name, Types.VARCHAR));
        parameters.put(name, value);
    }

    public void addInOutParameter(String name, int value) {
        super.declareParameter(new SqlInOutParameter(name, Types.INTEGER));
        parameters.put(name, value);
    }

    public void addInOutParameter(String name, double value) {
        super.declareParameter(new SqlInOutParameter(name, Types.DOUBLE));
        parameters.put(name, value);
    }

    public void addInOutParameter(String name, Date value) {
        super.declareParameter(new SqlInOutParameter(name, Types.TIMESTAMP));
        parameters.put(name, value);
    }

    public void setReturnParam(String name, Object entityTypeClass) {
        super.declareParameter(new SqlReturnResultSet(name, ParameterizedBeanPropertyRowMapper.newInstance(entityTypeClass.getClass())));
    }

    public void addOutStringParameter(String name) {
        super.declareParameter(new SqlOutParameter(name, Types.VARCHAR));
    }

    public void addOutIntParameter(String name) {
        super.declareParameter(new SqlOutParameter(name, Types.INTEGER));
    }

    public void addOutDoubleParameter(String name) {
        super.declareParameter(new SqlOutParameter(name, Types.DOUBLE));
    }

    public void addOutDateParameter(String name) {
        super.declareParameter(new SqlOutParameter(name, Types.TIMESTAMP));
    }

    public void addResultSetParameter(String name, Class<?> entityTypeClass) {
        super.declareParameter(new SqlReturnResultSet(name,
                ParameterizedBeanPropertyRowMapper.newInstance(entityTypeClass)));
    }

    public void setReturnParam(String param, RowMapper rowMapper) {
        this.declareParameter(new SqlReturnResultSet(param, rowMapper));
    }

    public Map<String, Object> execute() {
        this.compile();
        return super.execute(parameters);
    }

    @Override
    public Map<String, Object> execute(Map inParams) throws DataAccessException {
        Map allParams = new HashMap();
        allParams.putAll(parameters);
        allParams.putAll(inParams);
        return super.execute(allParams);
    }

    @Override
    public Map<String, Object> execute(final ParameterMapper inParamMapper)
            throws DataAccessException {
        return super.execute(new ParameterMapper() {
            public Map createMap(Connection con) throws SQLException {
                Map allParams = new HashMap();
                allParams.putAll(parameters);
                allParams.putAll(inParamMapper.createMap(con));
                return allParams;
            }
        });
    }
}