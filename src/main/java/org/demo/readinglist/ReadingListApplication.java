package org.demo.readinglist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReadingListApplication {
    public static void main(final String[] args) {
        SpringApplication.run(ReadingListApplication.class, args);
    }
}

