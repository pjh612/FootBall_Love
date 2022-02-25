package com.deu.football_love.repository;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@Slf4j
class MatchRepositoryTest {

  @Autowired
  EntityManager em;

  @Autowired
  MatchRepository matchRepository;

}