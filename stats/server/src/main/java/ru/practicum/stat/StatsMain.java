package ru.practicum.stat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("ru.practicum.")
@SpringBootApplication
public class StatsMain {
    public static void main(String[] args) {
        SpringApplication.run(StatsMain.class, args);
    }
}