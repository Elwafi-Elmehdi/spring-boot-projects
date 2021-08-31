package com.example.blog.bean;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String summary;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}