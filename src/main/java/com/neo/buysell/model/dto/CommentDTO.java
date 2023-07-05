package com.neo.buysell.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.buysell.model.entity.Comment;
import com.neo.buysell.model.entity.User;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class CommentDTO {
    @JsonProperty(value = "pk")
    public long id;
    @JsonProperty(value = "author")
    public long authorId;
    @JsonProperty(value = "authorFirstName")
    public String firstName;
    @JsonProperty(value = "authorImage")
    public String avatarPath;
    @JsonProperty(value = "createdAt")
    public long creationTime; //дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970
    public String text;


    public CommentDTO() {

    }

    public static CommentDTO toDto(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        User user = comment.getAd().getUser();
        commentDTO.id = comment.getId();
        commentDTO.authorId = user.getId();
        commentDTO.firstName = user.getFirstName();
        commentDTO.avatarPath = user.getAvatarPath();
        commentDTO.text = comment.getText();
        commentDTO.creationTime = comment.getCreationTime();
        return commentDTO;
    }


}
