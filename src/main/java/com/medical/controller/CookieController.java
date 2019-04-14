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
import java.net.URLEncoder;


@Controller
@RequestMapping("/medical/cookie")
public class CookieController {
    private static Logger logger = Logger.getLogger(CookieController.class);
    @Autowired
    private IUserService userService;

    /**
     * 获取用户名和身份
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getCookie.form", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Object getCookie(HttpServletRequest request, HttpServletResponse response) {
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
                return username + "$&" + identify;
            }
        } else {
            logger.error("---cookie失效！");
            return "0";
        }
    }

    /**
     * 验证cookie有效性
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/checkCookie.form", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Object checkCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String username = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("username")) {
                    try {
                        username = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        logger.error("-----转码失败！");
                    }
                }
            }
            if (username.equals("")) {
                logger.error("---cookie失效！");
                return "0";
            } else {
                return "1";
            }
        } else {
            logger.error("---cookie失效！");
            return "0";
        }
    }

    /**
     * 添加note_type cookie
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/selectnote.form", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Object selectnote(HttpServletRequest request, HttpServletResponse response) {
        String note_type = request.getParameter("note_type").trim();
        logger.info("SelectNoteServlet-----" + note_type + "\n");
        Cookie info = null;
        try {
            info = new Cookie("note_type", URLEncoder.encode(note_type.trim(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("转码失败！");
        }
        info.setPath("/");
        info.setMaxAge(24 * 60 * 60);
        response.addCookie(info);
        return "1";
    }

    /**
     * 获取note_type cookie
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getNoteType.form", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Object getNoteType(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String note_type = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("note_type")) {
                    try {
                        note_type = URLDecoder.decode(c.getValue(), "UTF-8");//获取帖子类型
                    } catch (UnsupportedEncodingException e) {
                        logger.error("转码失败！");
                    }
                }
            }
            logger.info("GetNoteTypeServlet----" + note_type + "\n");
            if (note_type.equals("")) {
                return "0";//TODO 会话过期，请重新操作
            } else {
                return note_type;//TODO 返回帖子类型
            }
        } else {
            return "0";//TODO 会话过期，请重新操作
        }
    }

    //TODO 查询头像和昵称
    @RequestMapping(value = "/findcookies.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Object findcookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String nickname = "";
        String jsonStr = "";
        String identify = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("username")) {
                    try {
                        nickname = URLDecoder.decode(c.getValue().trim(), "UTF-8");
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
            User user = new User();
            Doctor doctor = new Doctor();
            if (identify.equals("user")) {
                user = userService.getUserByName(nickname);
            } else if (identify.equals("doctor")) {
                doctor = userService.getDoctorByName(nickname);
            }
            if (user != null) {
                String nicknamecn = "";
                try {
                    nicknamecn = URLDecoder.decode(user.getName(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    logger.error("转码失败！");
                }
                jsonStr = "nicknamecn#$%" + nicknamecn.trim();
                logger.info(jsonStr);
                return jsonStr;
            } else if (doctor != null) {
                String nicknamecn = "";
                try {
                    nicknamecn = URLDecoder.decode(doctor.getName(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    logger.error("转码失败！");
                }
                jsonStr = "nicknamecn#$%" + nicknamecn.trim();
                logger.info(jsonStr);
                return jsonStr;
            } else {
                jsonStr = "nicknamecn#$%你还没有完善个人信息";
                return jsonStr;
            }
        } else {
            jsonStr = "nicknamecn#$%你还没有完善个人信息";
            return jsonStr;
        }
    }

    @RequestMapping(value = "/getAdminCookie.form", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Object getAdminCookie(HttpServletRequest request, HttpServletResponse response) {
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
                return username + "$&" + identify;
            }
        } else {
            logger.error("---cookie失效！");
            return "0";
        }
    }
}

