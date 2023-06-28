package com.neo.buysell.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class TComment {
    @JsonProperty(value = "pk")
    private String commentId;
    @JsonProperty(value = "author")
    private long authorId;
    @JsonProperty(value = "authorFirstName")
    private String firstName;
    @JsonProperty(value = "authorImage")
    private String avatarPath;
    @JsonProperty(value = "createdAt")
    private long creationTime; //дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970
    private String text;


    public TComment() {

    }
    public TComment(String commentId, long authorId, String firstName, String avatarPath, long creationTime, String text) {
        this.commentId = commentId;
        this.authorId = authorId;
        this.firstName = firstName;
        this.avatarPath = avatarPath;
        this.creationTime = creationTime;
        this.text = text;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
