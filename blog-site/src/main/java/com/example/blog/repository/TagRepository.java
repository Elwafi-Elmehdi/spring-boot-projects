package com.example.blog.repository;

import com.example.blog.bean.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    public Tag findByTitle(String title);
}
