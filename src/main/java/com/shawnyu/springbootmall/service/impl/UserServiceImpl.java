package com.shawnyu.springbootmall.service.impl;

import com.shawnyu.springbootmall.dao.UserDao;
import com.shawnyu.springbootmall.dto.UserRegisterRequest;
import com.shawnyu.springbootmall.model.User;
import com.shawnyu.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserbyId(Integer userId) {
        return userDao.getUserbyId(userId);
    }
}
