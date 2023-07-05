package com.neo.buysell.model.service;

import com.neo.buysell.model.entity.Comment;
import com.neo.buysell.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment getComment(long id) {
        return commentRepository.findById(id).orElseThrow(() -> new RuntimeException());//TODO
    }

    public void remove(Comment comment) {
        commentRepository.delete(comment);
    }
}
