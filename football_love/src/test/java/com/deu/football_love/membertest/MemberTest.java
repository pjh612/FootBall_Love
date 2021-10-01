package com.deu.football_love.membertest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Assertions;
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
            member.setId("football");
            member.setPwd("12234");
            em.persist(member);
            tx.commit();
            Assertions.assertEquals(member, em.find(Member.class, "football"));
        }catch(Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
	}
}
