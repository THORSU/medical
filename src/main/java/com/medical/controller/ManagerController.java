package com.medical.controller;

import com.alibaba.fastjson.JSON;
import com.medical.pojo.Doctor;
import com.medical.pojo.Note;
import com.medical.pojo.User;
import com.medical.service.INoteService;
import com.medical.service.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/medical/manager")
public class ManagerController {
    private static Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private INoteService noteService;

    /**
     * 获取用户列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getUserList.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Object getUserList(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = userService.getUserList();
        return JSON.toJSONString(users);
    }

    /**
     * 获取医生列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getDoctorList.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Object getDoctorList(HttpServletRequest request, HttpServletResponse response) {
        List<Doctor> doctors = userService.getDoctorList();
        return JSON.toJSONString(doctors);
    }

    /**
     * 获取帖子列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getNoteList.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Object getNoteList(HttpServletRequest request, HttpServletResponse response) {
        List<Note> notes = noteService.getNoteList();
        return JSON.toJSONString(notes);
    }
}
