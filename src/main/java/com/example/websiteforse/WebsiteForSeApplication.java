package com.example.websiteforse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.websiteforse"})
@EnableJpaRepositories(basePackages = {"com.example.websiteforse.repository"})
public class WebsiteForSeApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebsiteForSeApplication.class, args);
    }

}
