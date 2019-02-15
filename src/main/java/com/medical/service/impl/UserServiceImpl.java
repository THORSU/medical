package com.medical.service.impl;

import com.medical.mapper.UserMapper;
import com.medical.pojo.User;
import com.medical.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }
}
