package com.example.blog.repository;

import com.example.blog.bean.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    public Post findByReference(String reference);
    public Post deleteByReference(String reference);
}
