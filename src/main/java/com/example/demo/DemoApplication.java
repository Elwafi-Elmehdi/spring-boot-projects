package com.example.demo;

import com.example.demo.consts.FileConsts;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        new File(FileConsts.USER_FOLDER).mkdirs();
    }

}
