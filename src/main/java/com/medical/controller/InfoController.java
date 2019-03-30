package com.medical.controller;

import com.medical.pojo.Doctor;
import com.medical.pojo.User;
import com.medical.service.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
@RequestMapping("/medical/Info")
public class InfoController {
    private static Logger logger = Logger.getLogger(CookieController.class);

    @Autowired
    private IUserService userService;

    /**
     * 获取用户信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getUserInfo.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Object getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        Doctor doctor = new Doctor();
        Cookie[] cookies = request.getCookies();
        String username = "";
        String identify = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("username")) {
                    try {
                        username = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        logger.error("-----转码失败！");
                    }
                } else if (c.getName().trim().equals("identify")) {
                    try {
                        identify = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        logger.error("-----转码失败！");
                    }
                }
            }
            if (username.equals("") || identify.equals("")) {
                logger.error("---cookie失效！");
                return "0";
            } else {
                if (identify.equals("user")) {
                    user = userService.getUserByName(username);
                    return "" + user.getNickname() + "#$%" + user.getMobile() + "";
                } else if (identify.equals("doctor")) {
                    doctor = userService.getDoctorByName(username);
                    return "" + doctor.getNickname() + "#$%" + doctor.getMobile() + "";
                } else {
                    return "0";
                }
            }
        } else {
            logger.error("---cookie失效！");
            return "0";
        }
    }

    /**
     * 更新用户信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/updateInfo.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Object updateInfo(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        Doctor doctor = new Doctor();
        String nicknamecn = "";
        String username = "";
        String mobile = "";
        String identify = "";
        Cookie[] cookies = request.getCookies();
        try {
            nicknamecn = request.getParameter("nicknamecn");
            mobile = request.getParameter("mobile");
        } catch (Exception e) {
            logger.error("---获取前台值出错！", e);
        }
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("username")) {
                    try {
                        username = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        logger.error("-----转码失败！");
                    }
                } else if (c.getName().trim().equals("identify")) {
                    try {
                        identify = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        logger.error("-----转码失败！");
                    }
                }
            }
            if (username.equals("") || identify.equals("")) {
                return "104";//TODO cookie失效
            } else {
                if (identify.equals("user")) {
                    user = userService.getUserByName(username);
                    user.setNickname(nicknamecn);
                    user.setMobile(mobile);
                    int res = userService.updateInfo(user);
                    if (res == 1) {
                        logger.info("用户更新成功！");
                        return "1";//TODO 更新成功
                    } else {
                        return "102";//TODO 网络异常
                    }
                } else if (identify.equals("doctor")) {
                    doctor = userService.getDoctorByName(username);
                    doctor.setNickname(nicknamecn);
                    doctor.setMobile(mobile);
                    int res = userService.updateDoctor(doctor);
                    if (res == 1) {
                        logger.info("医生更新成功！");
                        return "1";//TODO 更新成功
                    } else {
                        return "102";//TODO 网络异常
                    }
                } else {
                    return "0";
                }
            }
        } else {
            return "101";//TODO cookie失效
        }
    }

    /**
     * 更改密码
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/changePwd.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Object changePwd(HttpServletRequest request, HttpServletResponse response) {
        String oldpwd = request.getParameter("oldpwd").trim();
        String newpwd1 = request.getParameter("newpwd1").trim();
        String newpwd2 = request.getParameter("newpwd2").trim();
        if (newpwd1.equals(newpwd2)) {
            Cookie[] cookies = request.getCookies();
            String nickname = "";
            String identify = "";
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().trim().equals("username")) {
                        try {
                            nickname = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            logger.error("转换失败---", e);
                        }
                    } else if (c.getName().trim().equals("identify")) {
                        try {
                            identify = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            logger.error("-----转码失败！");
                        }
                    }
                }
                if (nickname.equals("") || identify.equals("")) {
                    return "102";//TODO cookie失效
                } else {
                    if (identify.equals("user")) {
                        User st1 = userService.getUserByName(nickname);
                        if (st1 != null) {
                            if (st1.getPassword().equals(oldpwd.trim())) {
                                st1.setPassword(newpwd1.trim());
                                int res = userService.updateInfo(st1);
                                if (res == 1) {
                                    //TODO 清除浏览器cookie
                                    for (Cookie cookie : cookies) {
                                        cookie.setMaxAge(0);
                                        cookie.setPath("/");
                                        response.addCookie(cookie);
                                    }
                                    return "1";
                                } else {
                                    return "104";//TODO 网络异常
                                }
                            } else {
                                return "103";//TODO 旧密码输入错误！
                            }
                        } else {
                            return "104";//TODO 网络异常
                        }
                    } else if (identify.equals("doctor")) {
                        Doctor doctor = userService.getDoctorByName(nickname);
                        if (doctor != null) {
                            if (doctor.getPassword().equals(oldpwd.trim())) {
                                doctor.setPassword(newpwd1.trim());
                                int res = userService.updateDoctor(doctor);
                                if (res == 1) {
                                    //TODO 清除浏览器cookie
                                    for (Cookie cookie : cookies) {
                                        cookie.setMaxAge(0);
                                        cookie.setPath("/");
                                        response.addCookie(cookie);
                                    }
                                    return "1";
                                } else {
                                    return "104";//TODO 网络异常
                                }
                            } else {
                                return "103";//TODO 旧密码输入错误！
                            }
                        } else {
                            return "104";//TODO 网络异常
                        }
                    } else {
                        return "104";//TODO 网络异常
                    }
                }
            } else {
                return "102";//TODO COOKIE 失效
            }
        } else {
            return "100";//TODO 两次密码不相同
        }
    }
}