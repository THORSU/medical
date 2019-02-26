package com.medical.mapper;

import com.medical.pojo.Admin;
import com.medical.pojo.Doctor;
import com.medical.pojo.User;
import org.apache.ibatis.annotations.Param;

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

    //根据用户的id获取发起人的姓名
    User getUserById(@Param("userId") String userId);

    //根据医生的id获取发起人的姓名
    Doctor getDoctorById(@Param("doctorId") String doctorId);
}
