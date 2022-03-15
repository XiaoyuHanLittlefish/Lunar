package com.lunar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lunar.mapper")
public class LunarBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LunarBlogApplication.class,args);
    }
}
