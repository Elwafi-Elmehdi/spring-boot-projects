package com.example.blog.service.implement;

import com.example.blog.bean.Comment;
import com.example.blog.bean.Post;
import com.example.blog.repository.CommentRepository;
import com.example.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> findByPostRef(String ref) {
        return commentRepository.findByPostReference(ref);
    }
}
