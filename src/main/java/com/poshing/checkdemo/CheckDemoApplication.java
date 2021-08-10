package com.poshing.checkdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author litianyi
 */
@SpringBootApplication
@MapperScan("com.poshing.checkdemo.dao")
public class CheckDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckDemoApplication.class, args);
    }

}
