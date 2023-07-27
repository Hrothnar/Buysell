package com.neo.buysell.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CommentsDTO {
    public int count;
    @JsonProperty(value = "results")
    public List<CommentDTO> commentDTOS;

    public CommentsDTO(int count, List<CommentDTO> commentDTOS) {
        this.count = count;
        this.commentDTOS = commentDTOS;
    }
}
