package com.examsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ExamSystemApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ExamSystemApplication.class, args);
        System.out.println("项目启动成功...");
    }

    //部署到服务器时所需
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ExamSystemApplication.class);
    }
}
