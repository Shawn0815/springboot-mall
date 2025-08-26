package com.shawnyu.springbootmall.service.impl;

import com.shawnyu.springbootmall.dao.UserDao;
import com.shawnyu.springbootmall.dto.UserLoginRequest;
import com.shawnyu.springbootmall.dto.UserRegisterRequest;
import com.shawnyu.springbootmall.model.User;
import com.shawnyu.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    // 創建一個 Log 出來（括號內填入這個 class 的名稱）
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserbyId(Integer userId) {
        return userDao.getUserbyId(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        // 檢查註冊的 email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        // email 已經被註冊了
        if (user != null) {
            log.warn("該 email {} 已經被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); // 請求被迫停止，噴出錯誤碼
        }

        // 創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        // Email 尚未註冊
        if (user == null) {
            log.warn("該 email {} 尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 如果登入密碼不符合資料庫中的密碼（比較字串一定要用 equals 方法）
        if (!user.getPassword().equals(userLoginRequest.getPassword())) {
            log.warn("email {} 的密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return user;
    }
}
