package com.medical.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Note_Comment implements Serializable {
    private String note_comment_id;
    private String note_id;
    private String id;
    private String note_comment_content;
    private String note_comment_release_time;
}
