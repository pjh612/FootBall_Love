package com.deu.football_love.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.deu.football_love.domain.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;
    public void save(Member member)
    {
        em.persist(member);
    }
}
