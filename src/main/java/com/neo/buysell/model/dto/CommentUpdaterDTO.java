package com.neo.buysell.model.dto;

import com.neo.buysell.model.entity.Comment;

public class CommentUpdaterDTO {
    public String text;

    public CommentUpdaterDTO() {

    }

    public static Comment toEntity(CommentUpdaterDTO commentUpdaterDTO) {
        Comment comment = new Comment();
        comment.setText(commentUpdaterDTO.text);
        return comment;
    }

    public static CommentUpdaterDTO toDto(Comment comment) {
        CommentUpdaterDTO commentUpdaterDTO = new CommentUpdaterDTO();
        commentUpdaterDTO.text = comment.getText();
        return commentUpdaterDTO;
    }
}
