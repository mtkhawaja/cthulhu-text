package com.cthulhutext.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class CthulhuTextApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CthulhuTextApiApplication.class, args);
    }

}