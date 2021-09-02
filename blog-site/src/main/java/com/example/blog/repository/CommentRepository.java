package com.example.blog.repository;

import com.example.blog.bean.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    public Comment findByReference(String ref);
    public List<Comment> findByPostReference(String ref);
}
