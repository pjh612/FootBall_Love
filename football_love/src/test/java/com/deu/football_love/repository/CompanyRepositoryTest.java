package com.deu.football_love.repository;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.Company;
import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.type.MemberType;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CompanyRepositoryTest {
  @Autowired
  private EntityManager em;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private CompanyRepository companyRepository;

  @Test
  void existsByMemberNumber() {
    Member newMember = new Member("jh",passwordEncoder.encode("123"),"jh","박진형",LocalDate.of(2000, 1, 1),new Address("양산", "행복길", "11"),"email@email.com","010-1234-1234",MemberType.ROLE_BUSINESS);
    em.persist(newMember);
    companyRepository.save(new Company("companyA", newMember,new Address("양산", "행복길", "11"),"010-4567-8910","companyA descrption"));
    Assertions.assertTrue(companyRepository.existsByMemberNumber(newMember.getNumber()));
  }
}