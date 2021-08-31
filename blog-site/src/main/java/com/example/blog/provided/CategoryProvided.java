package com.example.blog.provided;

import com.example.blog.bean.Category;
import com.example.blog.consts.ResponseBody;
import com.example.blog.service.CategoryService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryProvided {
    private CategoryService categoryService;
    @Autowired
    public CategoryProvided(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping("/create")
    public int addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }
    @JsonView(ResponseBody.CategoryBase.class)
    @GetMapping("/all")
    public List<Category> findAll() {
        return categoryService.findAll();
    }
    @DeleteMapping("/delete/{title}")
}
