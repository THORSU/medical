package com.medical.controller;

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

    //用户注册
    @RequestMapping(value = "/SignUp.form", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Object SignUp(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String username = new String(request.getParameter("username").getBytes("iso-8859-1"), "utf-8");
        String password = request.getParameter("password").trim();
        String password1 = request.getParameter("password1").trim();
        String mobile = request.getParameter("mobile").trim();
        if (!password1.equals(password)) {
            return "password error";
        } else {
            user.setName(username);
            user.setPassword(password);
            user.setStatus("1");
            user.setMobile(mobile);
            int res = userService.addUser(user);
            if (res==1){
                logger.info(res);
                return "SignUp success";
            }else{
                logger.error(res);
                return "SignUp fail";
            }
        }
    }
}
