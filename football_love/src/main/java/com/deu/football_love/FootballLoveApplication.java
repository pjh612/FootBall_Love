package com.deu.football_love;

import javax.swing.Spring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FootballLoveApplication {

    public static void main(String[] args) {
        //git hub jenkins hook test
        SpringApplication.run(FootballLoveApplication.class, args);
    }
}
