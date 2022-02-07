package com.deu.football_love.service;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.company.AddCompanyResponse;
import com.deu.football_love.dto.company.QueryCompanyDto;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.repository.CompanyRepository;
import com.deu.football_love.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
class CompanyServiceTest {


  @Autowired
  EntityManager em;
  @Autowired
  CompanyService companyService;
  @Autowired
  CompanyRepository companyRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private MemberService memberService;

  @Autowired
  private MemberRepository memberRepository;

  @Test
  public void addAndFindCompanyTest() {
    MemberJoinRequest memberADto = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.NORMAL).build();
    QueryMemberDto memberA = memberService.join(memberADto);
    AddCompanyResponse newCompany = companyService.addCompany("companyA", memberA.getNumber(),
        new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");

    QueryCompanyDto findCompany = companyService.findCompany(newCompany.getCompanyId());
    Assertions.assertEquals(newCompany.getCompanyId(), findCompany.getCompanyId());

  }

  @Test
  public void withdrawalCompanyTest() {
    MemberJoinRequest memberADto = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.NORMAL).build();
    QueryMemberDto memberA = memberService.join(memberADto);
    AddCompanyResponse newCompany = companyService.addCompany("companyA", memberA.getNumber(),
        new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");

    companyService.withdrawalCompany(newCompany.getCompanyId());

    List<QueryCompanyDto> findCompanies = companyService.findCompaniesByName("companyA");

    Assertions.assertEquals(0, findCompanies.size());
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> companyService.findCompany(newCompany.getCompanyId()));
  }

  @Test
  public void withdrawalCascadeTest() {
    MemberJoinRequest memberADto = MemberJoinRequest.memberJoinRequestBuilder().id("memberA")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.BUSINESS).build();
    QueryMemberDto memberA = memberService.join(memberADto);
    AddCompanyResponse newCompany = companyService.addCompany("companyA", memberA.getNumber(),
        new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");

    memberService.withdraw("memberA");
    Assertions.assertEquals(1, memberRepository.chkWithdraw("memberA"));
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> companyService.findCompany(newCompany.getCompanyId()));
  }

  /**
   * 전국 각지의 중복된 이름의 컴퍼니를 조회 한다.
   */
  @Test
  public void findCompaniesByNameTest() {
    MemberJoinRequest memberADto = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.NORMAL).build();
    MemberJoinRequest memberBDto = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns2")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp2@naver.com").phone("010-1111-2222")
        .type(MemberType.NORMAL).build();
    MemberJoinRequest memberCDto = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns3")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp3@naver.com").phone("010-1111-2222")
        .type(MemberType.NORMAL).build();
    MemberJoinRequest memberDDto = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns4")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp4@naver.com").phone("010-1111-2222")
        .type(MemberType.NORMAL).build();

    QueryMemberDto memberA = memberService.join(memberADto);
    QueryMemberDto memberB = memberService.join(memberBDto);
    QueryMemberDto memberC = memberService.join(memberCDto);
    QueryMemberDto memberD = memberService.join(memberDDto);

    companyService.addCompany("companyA", memberA.getNumber(),
        new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");
    companyService.addCompany("companyA", memberB.getNumber(),
        new Address("seoul", "hongdaero", "12456"), "01012341234", "서울 홍대로에 위치한 풋살장");
    companyService.addCompany("companyA", memberC.getNumber(),
        new Address("daegu", "donseungro", "45678"), "01012341234", "대구 동성로에 위치한 풋살장");
    companyService.addCompany("companyA", memberD.getNumber(),
        new Address("ulsan", "sansanro", "56789"), "01012341234", "울산 삼산로에 위치한 풋살장");

    List<QueryCompanyDto> result = companyService.findCompaniesByName("companyA");

    Assertions.assertEquals(4, result.size());
    for (QueryCompanyDto c : result) {
      Assertions.assertEquals("companyA", c.getCompanyName());
    }
  }

}
