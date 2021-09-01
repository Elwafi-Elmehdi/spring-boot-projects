package com.example.blog.bean;

import com.example.blog.consts.ResponseBody;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonView(ResponseBody.TagBase.class)
    @Column(unique = true)
    private String title;
    @JsonView(ResponseBody.TagBase.class)
    private String content;

    public Tag() {
    }

    public Tag(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
