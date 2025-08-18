package com.shawnyu.springbootmall.service;

import com.shawnyu.springbootmall.dto.UserRegisterRequest;
import com.shawnyu.springbootmall.model.User;

public interface UserService {

    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserbyId(Integer userId);
}
