package com.medical.mapper;

import com.medical.pojo.Doctor;
import com.medical.pojo.User;

public interface UserMapper {
    //注册
    int addUser(User user);

    //医生注册
    int addDoctor(Doctor doctor);
}
