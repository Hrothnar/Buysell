package com.neo.buysell.model.dto;

import com.neo.buysell.model.entity.Comment;

public class CommentUpdaterDTO {
    public String text;

    public CommentUpdaterDTO() {

    }

    public CommentUpdaterDTO(String text) {
        this.text = text;
    }

    public static CommentUpdaterDTO toDto(Comment comment) {
        CommentUpdaterDTO commentUpdaterDTO = new CommentUpdaterDTO();
        commentUpdaterDTO.text = comment.getText();
        return commentUpdaterDTO;
    }
}
