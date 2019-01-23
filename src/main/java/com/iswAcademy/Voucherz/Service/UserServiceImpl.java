package com.iswAcademy.Voucherz.Service;


import com.iswAcademy.Voucherz.Dao.UserDao;
import com.iswAcademy.Voucherz.Model.User;
import com.iswAcademy.Voucherz.exception.RequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
     UserDao userDao;

    @Override
    public User createUser(User user){
        if(userDao.findByEmail(user.getEmail())== null){
            return  userDao.create(user);
        }

        return null;
    }
    @Override
    public boolean updateUser(Long id, User user){
        User existingUser = userDao.findById(id);
        if(existingUser == null)
            throw new RequestException("User not found");
        user.setId(id);
        return userDao.update(user);


    }

    @Override
    public User findUser(String Email) {
        return userDao.find(Email);
    }


}
