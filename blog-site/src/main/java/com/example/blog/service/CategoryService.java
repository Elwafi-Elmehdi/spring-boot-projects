package com.example.blog.service;

import com.example.blog.bean.Category;
import com.example.blog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public int addCategory(Category category){
        if(categoryRepository.findByTitleAndContent(category.getTitle(),category.getContent()) != null){
            return -1;
        }
        categoryRepository.save(category);
        return 1;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

}
