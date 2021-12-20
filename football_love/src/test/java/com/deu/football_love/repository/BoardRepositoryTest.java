package com.deu.football_love.repository;

import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Post;
import com.deu.football_love.domain.Team;
import com.deu.football_love.domain.type.BoardType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class BoardRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
   // @Rollback(value = false)
    public void deleteTest() {
        Member member = new Member();
        member.setId("memberA");
        member.setPwd(passwordEncoder.encode("123"));
        member.setName("박진형");
        em.persist(member);

    }
}
