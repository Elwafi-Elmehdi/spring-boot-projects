package com.example.blog.bean;

import com.example.blog.consts.ResponseBody;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonView(ResponseBody.CategoryBase.class)
    private String title;
    @JsonView(ResponseBody.CategoryBase.class)
    private String content;
}
