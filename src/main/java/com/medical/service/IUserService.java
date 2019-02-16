package com.medical.service;

import com.medical.pojo.Doctor;
import com.medical.pojo.User;

public interface IUserService {
    //用户注册
    int addUser(User user);

    //医生注册
    int addDoctor(Doctor doctor);
}
