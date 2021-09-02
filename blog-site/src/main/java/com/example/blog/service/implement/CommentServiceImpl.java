package com.example.blog.service.implement;

import com.example.blog.bean.Comment;
import com.example.blog.bean.Post;
import com.example.blog.repository.CommentRepository;
import com.example.blog.service.CommentService;
import com.example.blog.service.PostService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.dnd.DropTarget;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostService postService;
    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostService postService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
    }

    @Override
    public List<Comment> findByPostRef(String ref) {
        return commentRepository.findByPostReference(ref);
    }

    @Override
    public int saveComment(String id, Comment comment) {
        Post post = postService.findByRef(id);
        if(post == null){
            return 0;
        }else {
            comment.setReference(RandomStringUtils.randomAlphanumeric(8));
            comment.setCreatedAt(new Date());
            comment.setPost(post);
            commentRepository.save(comment);
            return 1;
        }
    }
}
