package com.iswAcademy.Voucherz.Dao;

import com.iswAcademy.Voucherz.Model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractBaseDao <T extends BaseEntity> implements BaseDao<T >{

    protected JdbcTemplate jdbcTemplate;
    protected SimpleJdbcCall create, update, delete, find, findAll;

    protected final String SINGLE_RESULT = "object";
    protected final String MULTIPLE_RESULT = "list";
    protected static final String RESULT_COUNT = "count";

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public abstract void setDataSource(DataSource dataSource);


    public T create(T model) throws DataAccessException {
        SqlParameterSource in = new BeanPropertySqlParameterSource(model);
        Map<String, Object> m = create.execute(in);
//        long id = (long) m.get("id");
//        model.setLongid();
        return model;
    }

    public boolean update(T model) throws DataAccessException {
        SqlParameterSource in = new BeanPropertySqlParameterSource(model);
        update.execute(in);
        return true;
    }

    public boolean delete(T model) {
        return false;
    }

    public T Read(long id) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("id", id);
        Map<String, Object> m = find.execute(in);
        List<T> list = (List<T>) m.get(SINGLE_RESULT);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<T> ReadAll() {
        return null;
    }




}
