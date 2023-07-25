package com.neo.buysell.model.service;

import com.neo.buysell.model.entity.Comment;
import com.neo.buysell.model.exception.particular.EntityNotFound;
import com.neo.buysell.model.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment getComment(long id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityNotFound(Comment.class, HttpStatus.NOT_FOUND));
    }

    public void removeComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
