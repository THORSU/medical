package com.medical.controller;

import com.alibaba.fastjson.JSON;
import com.medical.pojo.Admin;
import com.medical.pojo.Doctor;
import com.medical.pojo.User;
import com.medical.service.IUserService;
import com.medical.utils.GenerateSequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/medical/login")
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
        if (!StringUtils.isEmpty(userService.getUser(username))) {
            return JSON.toJSONString("have SignUp");
        }
        if (!password1.equals(password)) {
            return JSON.toJSONString("password error");
        } else {
            user.setId("user" + GenerateSequenceUtil.generateSequenceNo());
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
        if (!StringUtils.isEmpty(userService.getDoctor(username))) {
            return JSON.toJSONString("have SignUp");
        }
        if (!password1.equals(password)) {
            return JSON.toJSONString("password error");
        } else {
            doctor.setId("doctor" + GenerateSequenceUtil.generateSequenceNo());
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
            if (res.getPassword().equals(password) && ("1").equals(res.getStatus())) {
                Cookie uname = new Cookie("username", res.getName());
                Cookie identify = new Cookie("identify", "user");
                identify.setPath("/");
                uname.setPath("/");
                identify.setMaxAge(60 * 60 * 24);
                uname.setMaxAge(60 * 60 * 24);
                response.addCookie(uname);
                response.addCookie(identify);
                return JSON.toJSONString("login success");
            } else if (("0").equals(res.getStatus())) {
                return JSON.toJSONString("have deleted");
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
            if (res.getPassword().equals(password) && ("1").equals(res.getStatus())) {
                Cookie uname = new Cookie("username", res.getName());
                Cookie identify = new Cookie("identify", "doctor");
                identify.setPath("/");
                uname.setPath("/");
                identify.setMaxAge(60 * 60 * 24);
                uname.setMaxAge(60 * 60 * 24);
                response.addCookie(uname);
                response.addCookie(identify);
                return JSON.toJSONString("login success");
            } else if (("0").equals(res.getStatus())) {
                return JSON.toJSONString("deleted");
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
                Cookie uname = new Cookie("username", res.getName());
                Cookie identify = new Cookie("identify", "admin");
                identify.setPath("/");
                uname.setPath("/");
                identify.setMaxAge(60 * 60 * 24);
                uname.setMaxAge(60 * 60 * 24);
                response.addCookie(uname);
                response.addCookie(identify);
                return JSON.toJSONString("login success");
            } else {
                return JSON.toJSONString("login failure");
            }
        } else {
            return JSON.toJSONString("not register");
        }
    }

    //忘记密码
    @RequestMapping(value = "/forgetpwd.form", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Object forgetpwd(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String username = request.getParameter("username").trim();
        String mobile = request.getParameter("mobile").trim();
        String password = request.getParameter("password").trim();
        String password1 = request.getParameter("password1").trim();
        String identify1 = request.getParameter("identify1").trim();
        if (!password.equals(password1)) {
            return JSON.toJSONString("password error");
        }
        if (identify1.equals("user")) {
            user.setName(username);
            User res = userService.userLogin(user);
            if (res == null) {
                return JSON.toJSONString("mobile or name error");
            }
            if (!res.getMobile().equals(mobile)) {
                return JSON.toJSONString("mobile or name error");
            }
            res.setPassword(password);
            userService.updateInfo(res);
            return JSON.toJSONString("change success");
        } else if (identify1.equals("doctor")) {
            doctor.setName(username);
            Doctor res = userService.doctorLogin(doctor);
            if (res == null) {
                return JSON.toJSONString("mobile or name error");
            }
            if (!res.getMobile().equals(mobile)) {
                return JSON.toJSONString("mobile or name error");
            }
            res.setPassword(password);
            userService.updateDoctor(res);
            return JSON.toJSONString("change success");
        } else {
            return JSON.toJSONString("identity error");
        }
    }


    /**
     * 上传
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Object pushErrorData(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 转型为MultipartHttpRequest(重点的所在)这个就是上面ajax中提到如果直接访问此接口而不加"/"，此转型就会报错
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        // 获得第1张图片（根据前台的name名称得到上传的文件）
        MultipartFile file = multipartRequest.getFile("errPic"); //对应  jquery $("#imageFile").get(0).files[index]
        Map<String, Object> map = new HashMap<String, Object>();
        if (null != file && !file.isEmpty()) {
            try {
                map = uploadFile(file);


            } catch (IOException e) {
            }
        }
        return JSON.toJSONString(map);
    }

    /**
     * 图片上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static Map<String, Object> uploadFile(MultipartFile file)
            throws IOException, Exception {
        String filePath = "D:\\idea\\upload\\";
        Map<String, Object> map = new HashMap<String, Object>();
        String fileName = file.getOriginalFilename();
        fileName = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
        File tempFile = new File(filePath, System.currentTimeMillis() + fileName);

        try {
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();//如果是多级文件使用mkdirs();如果就一层级的话，可以使用mkdir()
            }
            if (!tempFile.exists()) {
                boolean blag = tempFile.createNewFile();
                System.out.println(blag);
            }
            file.transferTo(tempFile);
        } catch (IllegalStateException e) {
        }

        map.put("data", filePath + System.currentTimeMillis() + tempFile.getName());

        return map;
    }
}
