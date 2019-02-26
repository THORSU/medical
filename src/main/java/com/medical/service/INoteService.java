package com.medical.service;

import com.medical.pojo.Note;
import com.medical.pojo.Note_Comment;

import java.util.List;

public interface INoteService {
    //根据文章类型获取文章集合
    List<Note> getNoteByType(String note_type);

    //根据文章id获取文章
    Note getNoteById(String note_id);

    //更新文章
    int updateNote(Note note);

    //根据文章id获取文章评论内容
    List<Note_Comment> getNoteCommentsByNote_id(String note_id);
}
