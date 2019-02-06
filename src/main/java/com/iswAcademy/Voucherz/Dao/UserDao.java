package com.iswAcademy.Voucherz.Dao;

import com.iswAcademy.Voucherz.Model.User;

import java.util.List;

public interface UserDao extends BaseDao<User>{
    User findByEmail(String Email);
    User findByResetToken(String resetToken);


}
