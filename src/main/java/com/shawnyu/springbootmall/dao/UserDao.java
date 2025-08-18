package com.shawnyu.springbootmall.dao;

import com.shawnyu.springbootmall.dto.UserRegisterRequest;
import com.shawnyu.springbootmall.model.User;

public interface UserDao {

    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserbyId(Integer userId);
}
