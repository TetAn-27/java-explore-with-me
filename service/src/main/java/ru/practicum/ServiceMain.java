package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class ServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(ServiceMain.class, args);
    }
}