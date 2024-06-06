package com.fastcampus.loggingconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoggingConsumerApplication {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SpringApplication.run(LoggingConsumerApplication.class, args);
    }
}
