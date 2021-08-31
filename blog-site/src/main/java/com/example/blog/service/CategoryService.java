package com.example.blog.service;

import com.example.blog.bean.Category;
import com.example.blog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public int addCategory(Category category){
        if(categoryRepository.findByTitle(category.getTitle()) != null){
            return -2;
        }else if(categoryRepository.findByTitleAndContent(category.getTitle(),category.getContent()) != null){
            return -1;
        }
        categoryRepository.save(category);
        return 1;
    }
    @Transactional
    public int deleteCategory(String title){
        if(categoryRepository.findByTitle(title) != null){
            categoryRepository.delete(categoryRepository.findByTitle(title));
            return 1;
        }
        return 0;
    }
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

}
