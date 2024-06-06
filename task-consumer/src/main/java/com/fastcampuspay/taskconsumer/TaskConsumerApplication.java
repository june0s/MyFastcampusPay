package com.fastcampuspay.taskconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskConsumerApplication {
    public static void main(String[] args) {
        System.out.println("TaskConsumerApplication start!");
        SpringApplication.run(TaskConsumerApplication.class, args);
    }
}
