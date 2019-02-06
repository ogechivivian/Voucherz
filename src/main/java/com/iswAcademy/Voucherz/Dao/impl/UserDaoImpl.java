package com.iswAcademy.Voucherz.Dao.impl;

import com.iswAcademy.Voucherz.Dao.AbstractBaseDao;
import com.iswAcademy.Voucherz.Dao.UserDao;
import com.iswAcademy.Voucherz.Dao.util.RowCountMapper;
import com.iswAcademy.Voucherz.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.AbstractJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcCallOperations;
import org.springframework.stereotype.Repository;


import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl extends AbstractBaseDao<User> implements UserDao {
    protected SimpleJdbcCall findByEmail;

    @Autowired
    @Override
    public void setDataSource(@Qualifier(value="dataSource") DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        create = new SimpleJdbcCall(dataSource).withProcedureName("uspCreateUser2").withReturnValue();
        update = new SimpleJdbcCall(dataSource).withProcedureName("uspCreateUser2").withReturnValue();
        findById = new SimpleJdbcCall(dataSource).withProcedureName("uspFindUserbyid").returningResultSet(SINGLE_RESULT,new BeanPropertyRowMapper<>(User.class));
        find = new SimpleJdbcCall(dataSource).withProcedureName("uspFindUser").returningResultSet(SINGLE_RESULT,new BeanPropertyRowMapper<>(User.class));
        findbytoken = new SimpleJdbcCall(dataSource).withProcedureName("uspFindUserbyresetToken").returningResultSet(SINGLE_RESULT,new BeanPropertyRowMapper<>(User.class));
        findAll = new SimpleJdbcCall(dataSource).withProcedureName("uspFindAllUser").returningResultSet(MULTIPLE_RESULT, new BeanPropertyRowMapper<>(User.class));


    }

    @Override
    public User findByEmail(String Email) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("email", Email);
        Map<String,Object> m = find.execute(in);
        List<User>list = (List<User>) m.get(SINGLE_RESULT);
        if(list==null || list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public User findByResetToken(String resetToken){
        SqlParameterSource in = new MapSqlParameterSource().addValue("resetToken", resetToken);
        Map<String,Object> m = findbytoken.execute(in);
        List<User>list = (List<User>) m.get(SINGLE_RESULT);
        if(list==null || list.isEmpty()){
            return null;
        }
        return list.get(0);
    }


    @Override
    public User find(String email) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("email", email);
        Map<String,Object> m = find.execute(in);
        List<User>list = (List<User>) m.get(SINGLE_RESULT);
        if(list==null || list.isEmpty()){
            return null;
        }
        return list.get(0);

    }

    @Override
    public User findById(long id) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("id", id);
        Map<String,Object> m = findById.execute(in);
        List<User>list = (List<User>) m.get(SINGLE_RESULT);
        if(list==null || list.isEmpty()){
            return null;
        }
        return list.get(0);

    }

    @Override
    public List<User> findAll() {
        Map<String,Object> m = findAll.execute();
        List<User>list = (List<User>) m.get(MULTIPLE_RESULT);
       if(list==null || list.isEmpty()){ return null;
      }
        return list;
   }





}

