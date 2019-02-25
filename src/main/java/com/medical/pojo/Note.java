package com.medical.pojo;

import lombok.Data;

import java.io.Serializable;

//帖子
@Data
public class Note implements Serializable {
    //贴子id
    private String note_id;
    //用户id
    private String id;
    //发布时间
    private String release_time;
    //帖子类型
    private String note_type;
    //帖子内容
    private String note_content;
    //点赞数
    private String note_likes;

    private String note_comment_counts;
}
