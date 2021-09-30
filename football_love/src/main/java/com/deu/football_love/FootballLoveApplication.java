package com.deu.football_love;

import com.deu.football_love.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@SpringBootApplication
public class FootballLoveApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootballLoveApplication.class, args);

	}


}
