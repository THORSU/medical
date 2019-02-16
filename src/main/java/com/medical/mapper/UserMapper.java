package com.medical.mapper;

import com.medical.pojo.Admin;
import com.medical.pojo.Doctor;
import com.medical.pojo.User;

public interface UserMapper {
    //注册
    int addUser(User user);

    //医生注册
    int addDoctor(Doctor doctor);

    //用户登录
    User userLogin(User user);

    //医生登录
    Doctor doctorLogin(Doctor doctor);

    //管理员登录
    Admin adminLogin(Admin admin);
}
