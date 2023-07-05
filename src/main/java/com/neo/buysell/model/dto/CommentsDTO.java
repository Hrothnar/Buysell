package com.neo.buysell.model.dto;

import java.util.List;

public class CommentsDTO {
    public int count;
    public List<CommentDTO> commentDTOS;

    public CommentsDTO() {

    }

    public CommentsDTO(int count, List<CommentDTO> commentDTOS) {
        this.count = count;
        this.commentDTOS = commentDTOS;
    }
}
