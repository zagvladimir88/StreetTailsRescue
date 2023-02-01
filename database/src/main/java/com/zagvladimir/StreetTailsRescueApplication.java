package com.zagvladimir;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.zagvladimir")
public class StreetTailsRescueApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreetTailsRescueApplication.class, args);

    }
}
