package com.example.blog.repository;

import com.example.blog.bean.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    public Category findByTitleAndContent(String title,String content);
    public Category findByTitle(String title);
}
