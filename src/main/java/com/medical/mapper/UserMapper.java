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

    //根据用户名获得用户详细信息
    User getUserByName(@Param("name") String name);

    //根据医生名获得医生详细信息
    Doctor getDoctorByName(@Param("name") String name);

    //更新用户信息
    int updateInfo(User user);

    //更新医生信息
    int updateDoctor(Doctor doctor);
}
