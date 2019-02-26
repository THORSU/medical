package com.medical.service.impl;

import com.medical.mapper.NoteMapper;
import com.medical.pojo.Note;
import com.medical.pojo.Note_Comment;
import com.medical.service.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements INoteService {
    @Autowired
    private NoteMapper noteMapper;

    @Override
    public List<Note> getNoteByType(String note_type) {
        return noteMapper.getNoteByType(note_type);
    }

    @Override
    public Note getNoteById(String note_id) {
        return noteMapper.getNoteById(note_id);
    }

    @Override
    public int updateNote(Note note) {
        return noteMapper.updateNote(note);
    }

    @Override
    public List<Note_Comment> getNoteCommentsByNote_id(String note_id) {
        return noteMapper.getNoteCommentsByNote_id(note_id);
    }
}
