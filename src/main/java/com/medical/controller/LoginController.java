package com.medical.controller;

import com.alibaba.fastjson.JSON;
import com.medical.pojo.Admin;
import com.medical.pojo.Doctor;
import com.medical.pojo.User;
import com.medical.service.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/medical")
public class LoginController {
    private static Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private IUserService userService;

    private User user = new User();
    private Doctor doctor = new Doctor();
    private Admin admin = new Admin();

    //用户注册
    @RequestMapping(value = "/userSignUp.form", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Object userSignUp(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String password1 = request.getParameter("password1").trim();
        String mobile = request.getParameter("mobile").trim();
        if (!password1.equals(password)) {
            return JSON.toJSONString("password error");
        } else {
            user.setName(username);
            user.setPassword(password);
            user.setStatus("1");
            user.setMobile(mobile);
            int res = userService.addUser(user);
            if (res == 1) {
                logger.info(res);
                return JSON.toJSONString("SignUp success");
            } else {
                logger.error(res);
                return JSON.toJSONString("SignUp fail");
            }
        }
    }

    //医生注册
    @RequestMapping(value = "/doctorSignUp.form", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Object doctorSignUp(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String password1 = request.getParameter("password1").trim();
        String mobile = request.getParameter("mobile").trim();
        if (!password1.equals(password)) {
            return JSON.toJSONString("password error");
        } else {
            doctor.setName(username);
            doctor.setPassword(password);
            doctor.setStatus("1");
            doctor.setMobile(mobile);
            doctor.setPhoto("111111");
            int res = userService.addDoctor(doctor);
            if (res == 1) {
                logger.info(res);
                return JSON.toJSONString("SignUp success");
            } else {
                logger.error(res);
                return JSON.toJSONString("SignUp fail");
            }
        }
    }

    //用户登录
    @RequestMapping(value = "/userLogin.form", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Object userLogin(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        user.setName(username);
        user.setPassword(password);

        User res = userService.userLogin(user);
        if (res != null) {
            if (res.getPassword().equals(password)) {
//                Cookie uname=new Cookie("username",res.getName());
//                Cookie identify=new Cookie("identify","js");
//                identify.setPath("/");
//                uname.setPath("/");
//                identify.setMaxAge(60*60*24);
//                uname.setMaxAge(60*60*24);
//                response.addCookie(uname);
//                response.addCookie(identify);
                return JSON.toJSONString("login success");
            } else {
                return JSON.toJSONString("login failure");
            }
        } else {
            return JSON.toJSONString("not register");
        }
    }

    //医生登录
    @RequestMapping(value = "/doctorLogin.form", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Object doctorLogin(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        doctor.setName(username);
        doctor.setPassword(password);

        Doctor res = userService.doctorLogin(doctor);
//        System.out.println(res.getPassword());
        if (res != null) {
            if (res.getPassword().equals(password)) {
//                Cookie uname=new Cookie("username",res.getName());
//                Cookie identify=new Cookie("identify","js");
//                identify.setPath("/");
//                uname.setPath("/");
//                identify.setMaxAge(60*60*24);
//                uname.setMaxAge(60*60*24);
//                response.addCookie(uname);
//                response.addCookie(identify);
                return JSON.toJSONString("login success");
            } else {
                return JSON.toJSONString("login failure");
            }
        } else {
            return JSON.toJSONString("not register");
        }
    }

    //管理员登录
    @RequestMapping(value = "/adminLogin.form", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Object adminLogin(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        admin.setName(username);
        admin.setPassword(password);

        Admin res = userService.adminLogin(admin);
//        System.out.println(res.getPassword());
        if (res != null) {
            if (res.getPassword().equals(password)) {
//                Cookie uname=new Cookie("username",res.getName());
//                Cookie identify=new Cookie("identify","js");
//                identify.setPath("/");
//                uname.setPath("/");
//                identify.setMaxAge(60*60*24);
//                uname.setMaxAge(60*60*24);
//                response.addCookie(uname);
//                response.addCookie(identify);
                return JSON.toJSONString("login success");
            } else {
                return JSON.toJSONString("login failure");
            }
        } else {
            return JSON.toJSONString("not register");
        }
    }
}
