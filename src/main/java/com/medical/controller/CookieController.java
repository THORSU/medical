package com.medical.controller;

import org.apache.log4j.Logger;
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
}

