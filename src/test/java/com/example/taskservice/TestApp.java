package com.example.taskservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TestApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(TestApp.class)
                .profiles("local")
                .run(args);
    }

}
