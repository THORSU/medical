package com.medical.service.impl;

import com.medical.mapper.UserMapper;
import com.medical.pojo.Admin;
import com.medical.pojo.Doctor;
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

    @Override
    public int addDoctor(Doctor doctor) {
        return userMapper.addDoctor(doctor);
    }

    @Override
    public User userLogin(User user) {
        return userMapper.userLogin(user);
    }

    @Override
    public Doctor doctorLogin(Doctor doctor) {
        return userMapper.doctorLogin(doctor);
    }

    @Override
    public Admin adminLogin(Admin admin) {
        return userMapper.adminLogin(admin);
    }

    @Override
    public User getUserById(String userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public Doctor getDoctorById(String doctorId) {
        return userMapper.getDoctorById(doctorId);
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    @Override
    public Doctor getDoctorByName(String name) {
        return userMapper.getDoctorByName(name);
    }
}
