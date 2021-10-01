package com.deu.football_love.membertest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;

import com.deu.football_love.domain.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberTest {
	@Test
	public void jpa_연결테스트() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_unit");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            log.info("hello");
            Member member = new Member();
            member.setId("12324");
            member.setPwd("12234");
            em.persist(member);
            tx.commit();
        }catch(Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
	}
}
