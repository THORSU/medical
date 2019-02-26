package com.medical.controller;

import com.alibaba.fastjson.JSON;
import com.medical.pojo.Doctor;
import com.medical.pojo.Note;
import com.medical.pojo.Note_Comment;
import com.medical.pojo.User;
import com.medical.service.INoteService;
import com.medical.service.IUserService;
import com.medical.utils.GenerateSequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("/medical/note")
public class NoteController {
    private static Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private INoteService noteService;
    @Autowired
    private IUserService userService;

    //获取帖子
    @RequestMapping(value = "/getPublicNote.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Object getPublicNote(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String note_type = "";
        String topic_list = "";
        String identify = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("note_type")) {
                    try {
//                        获取帖子类型
                        note_type = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        logger.error("转码失败！");
                    }
                } else if (c.getName().trim().equals("identify")) {
                    try {
                        identify = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        logger.error("转码失败！");
                    }
                }
            }
        }
        logger.info("GetPublicNoteServlet---" + note_type + "\n");
        //获取某种类型的文章
        List<Note> ntList = noteService.getNoteByType(note_type.trim());
        int size = ntList.size();
        for (int i = 0; i < size; i++) {
            Note note = ntList.get(i);
            //获取帖子id
            String note_id = note.getNote_id().trim();
            //获取这个帖子的发起人id
            String id = note.getId();
            //获取发起时间
            String release_time = note.getRelease_time().split("\\[")[0];
            //获取帖子内容
            String note_content = note.getNote_content();
            //获取点赞数
            String note_likes = note.getNote_likes();
            //获取评论数
            String note_comment_counts = note.getNote_comment_counts();
            //获取发起人姓名
            String name = "";
            if (identify.equals("user")) {
                name = userService.getUserById(id).getName();
            } else if (identify.equals("doctor")) {
                name = userService.getDoctorById(id).getName();
            }
            topic_list += " <li class=\"topic-li\">\n" +
                    "            <div class=\"main\">\n" +
                    "                <div class=\"user title clearfix\">\n" +
//                    "                    <img class=\"user-icon float-left-box\"\n" +
//                    "                         src=\"" + headicon.trim() + "\">\n" +
                    "\n" +
                    "                    <div class=\"title\">\n" +
                    "                        <p class=\"name\">" + name.trim() + "</p>\n" +
                    "\n" +
                    "                        <p class=\"relate\"><span class=\"time\">" + release_time.trim() +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "\n" +
                    "                <div class=\"content\">" + note_content.trim() + "</div>\n" +
                    "\n" +
//                    "                <div class=\"img-div clearfix\">\n" +
//                    "\n" +
//                    "                    <div class=\"multi-pic clearfix\">" + images + "</div>\n" +
//                    "\n" +
                    "                </div>\n" +
                    "\n" +
                    "                <div class=\"read display-none\">阅读<span class=\"num\">1393</span></div>\n" +
                    "\n" +
                    "            </div>\n" +
                    "            <ul class=\"btns clearfix\">\n" +
                    "\n" +
                    "                <li id=" + note_id.trim() + "  class=\"like like-btn\"  onclick=\"like(this.id)\">\n" +
                    "                    <span class=\"icon\"></span>\n" +
                    "\n" +
                    "                    <p class=\"like-box\"><span class=\"txt\">" + note_likes.trim() + "</span></p>\n" +
                    "\n" +
                    "                    <div class=\"icon-like-box\"></div>\n" +
                    "                </li>\n" +
                    "\n" +
                    "                <li id=" + note_id.trim() + " class=\"comment\" onclick=\"comment(this.id)\">\n" +
                    "                    <span class=\"icon\"></span>\n" +
                    "\n" +
                    "                    <p>" + note_comment_counts.trim() + "</p>\n" +
                    "                </li>\n" +
                    "            </ul>\n" +
                    "\n" +
                    "        </li>";
        }
        return topic_list;
    }

    // 更新点赞数
    @RequestMapping(value = "/UpdateLikes.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Object UpdateLikes(HttpServletRequest request, HttpServletResponse response) {
        String note_likes = request.getParameter("note_likes").trim();
        String note_id = request.getParameter("note_id").trim();
        try {
            Note n = noteService.getNoteById(note_id);
            n.setNote_likes(note_likes);
            noteService.updateNote(n);
            logger.info("---点赞更新成功");
            return JSON.toJSONString("like success");
        } catch (Exception e) {
            return JSON.toJSONString("like fail");
        }
    }

    //获取单个帖子具体信息
    @RequestMapping(value = "/getSingleNote.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Object getSingleNote(HttpServletRequest request, HttpServletResponse response) {
        String res = "";
        String note_id = request.getParameter("note_id").trim();
        logger.info(note_id);
        try {
            Note note = noteService.getNoteById(note_id);
            //获取发起人姓名
            String name = "";
            if (!StringUtils.isEmpty(userService.getUserById(note.getId()))) {
                name = userService.getUserById(note.getId()).getName();
            } else if (!StringUtils.isEmpty(userService.getDoctorById(note.getId()))) {
                name = userService.getDoctorById(note.getId()).getName();
            }
            //无评论
            if (note.getNote_comment_counts().equals("0")) {
                res = "0#$" + note.getNote_id() + "#$" + note.getRelease_time() + "#$" + name + "#$"
                        + note.getNote_content() + "#$" + note.getNote_comment_counts() + "#$" + note.getNote_likes() + "";

                return res;
            }
            //有评论
            else {
                res = "2#$" + note.getNote_id() + "#$" + note.getRelease_time() + "#$" + name + "#$"
                        + note.getNote_content() + "#$" + note.getNote_comment_counts() + "#$" + note.getNote_likes() + "#$";
                List<Note_Comment> listComment = noteService.getNoteCommentsByNote_id(note_id);
                int listCommentSize = listComment.size();
                for (int i = 0; i < listCommentSize; i++) {
                    //获取评论人姓名
                    String commentName = "";
                    if (!StringUtils.isEmpty(listComment.get(i).getId())) {
                        commentName = userService.getUserById(listComment.get(i).getId()).getName();
                    } else if (!StringUtils.isEmpty(userService.getDoctorById(listComment.get(i).getId()))) {
                        commentName = userService.getDoctorById(listComment.get(i).getId()).getName();
                    }
                    res += "" + commentName + "$&" + listComment.get(i).getNote_comment_content() + "$&" + listComment.get(i).getNote_comment_release_time() + "%$";
                }
                return res;
            }
        } catch (Exception e) {
            return "101";//TODO 网络异常
        }
    }

    // 发表评论
    @RequestMapping(value = "/releaseComment.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Object releaseComment(HttpServletRequest request, HttpServletResponse response) {
        String note_comment_content = request.getParameter("note_comment_content").trim();
        String note_id = request.getParameter("note_id").trim();
        String release_time = request.getParameter("note_comment_release_time").trim();
        Cookie[] cookies1 = request.getCookies();
        String nickname1 = "";
        String identify = "";
        if (cookies1 != null) {
            for (Cookie c : cookies1) {
                if (c.getName().trim().equals("username")) {
                    try {
                        nickname1 = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        logger.error("转码失败！");
                    }
                } else if (c.getName().trim().equals("identify")) {
                    try {
                        identify = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        logger.error("转码失败！");
                    }
                }
            }
        }
        if (nickname1.equals("") && identify.equals("")) {
            return "102";//TODO 会话失效
        } else {
            try {
                Note n = noteService.getNoteById(note_id);
                Note_Comment nc = new Note_Comment();
                nc.setNote_comment_id("nc" + GenerateSequenceUtil.generateSequenceNo());
                nc.setNote_id(note_id);
                nc.setNote_comment_release_time(release_time);
                nc.setNote_comment_content(note_comment_content);
                if (identify.equals("user")) {
                    User user = userService.getUserByName(nickname1);
                    nc.setId(user.getId());
                } else if (identify.equals("doctor")) {
                    Doctor doctor = userService.getDoctorByName(nickname1);
                    nc.setId(doctor.getId());
                }
                noteService.saveComment(nc);
                n.setNote_comment_counts(String.valueOf(Integer.parseInt(n.getNote_comment_counts()) + 1));
                noteService.updateNote(n);
                return "success";
            } catch (Exception e) {
                return "fail";
            }
        }
    }

    // 发表帖子
    @RequestMapping(value = "/ReleaseNote.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Object ReleaseNote(HttpServletRequest request, HttpServletResponse response) {
        String note_type = request.getParameter("note_type").trim();
        logger.info("ReleaseNoteServlet-----" + note_type + "\n");
        String release_time = request.getParameter("release_time").trim();
        logger.info("ReleaseNoteServlet-----" + release_time + "\n");
        String note_content = request.getParameter("note_content").trim();
        logger.info("ReleaseNoteServlet-----" + note_content + "\n");
        Cookie[] cookies1 = request.getCookies();
        String nickname1 = "";
        String identify = "";
        if (cookies1 != null) {
            for (Cookie c : cookies1) {
                if (c.getName().trim().equals("username")) {
                    try {
                        nickname1 = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        logger.error("转码失败！");
                    }
                } else if (c.getName().trim().equals("identify")) {
                    try {
                        identify = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        logger.error("转码失败！");
                    }
                }
            }
            if (nickname1.equals("") && identify.equals("")) {
                return "102";//TODO 会话失效
            } else {
                User user = new User();
                Doctor doctor = new Doctor();
                String id = "";
                if (identify.equals("user")) {
                    user = userService.getUserByName(nickname1);
                    id = user.getId();
                    logger.info("用户id" + id);
                } else if (identify.equals("doctor")) {
                    doctor = userService.getDoctorByName(nickname1);
                    id = doctor.getId();
                    logger.info("用户id" + id);
                }
                if (id == "") {
                    return "103";//TODO 网络异常
                } else {
                    Note nt = new Note();
                    nt.setNote_id("note" + GenerateSequenceUtil.generateSequenceNo());
                    nt.setId(id);
                    nt.setRelease_time(release_time);
                    nt.setNote_type(note_type);
                    nt.setNote_comment_counts("0");
                    nt.setNote_likes("0");
                    nt.setNote_content(note_content);
                    noteService.saveNote(nt);
                    logger.info("帖子基本信息插入成功！");
                    return "1";//TODO 帖子发布成功
                }
            }
        } else {
            return "102";//TODO 会话失效
        }
    }
}
