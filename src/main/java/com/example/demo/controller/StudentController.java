package com.example.demo.controller;


import com.example.demo.mvc.WithApiResult;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolong7713
 */
//@WithApiResult
@Slf4j
@RequestMapping("/student")
@RestController
public class StudentController {

    @Value("${spring.profiles.active}")
    private String env;

    @Value("${greeting.message}")
    private String message;

    @GetMapping("/info")
    public Student info(String param) {
        log.info("env :{}", env);
        log.info("message :{}", message);

        if (StringUtils.hasLength(param)) {
            throw new RuntimeException("param is blank");
        }
        return Student.builder().name("张三").age(29).build();
    }

    @Builder
    @Data
    static class Student {
        private String name;
        private int age;
    }
}
