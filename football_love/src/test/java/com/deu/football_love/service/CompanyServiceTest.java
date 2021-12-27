package com.deu.football_love.service;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.company.AddCompanyResponse;
import com.deu.football_love.dto.company.QueryCompanyDto;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.repository.CompanyRepository;
import com.deu.football_love.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

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
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberA = memberService.join(memberADto);
        AddCompanyResponse newCompany = companyService.addCompany("companyA", memberA.getNumber(), new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");

        QueryCompanyDto findCompany = companyService.findCompany(newCompany.getCompanyId());
        Assertions.assertEquals(newCompany.getCompanyId(), findCompany.getCompanyId());

    }

    @Test
    public void withdrawalCompanyTest() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberA = memberService.join(memberADto);
        AddCompanyResponse newCompany = companyService.addCompany("companyA", memberA.getNumber(), new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");

        companyService.withdrawalCompany(newCompany.getCompanyId());

        List<QueryCompanyDto> findCompanies = companyService.findCompaniesByName("companyA");
        QueryCompanyDto findCompany = companyService.findCompany(newCompany.getCompanyId());
        Assertions.assertEquals(0, findCompanies.size());
        Assertions.assertNull(findCompany);
    }

    @Test
    //@Rollback(value = false)
    public void withdrawalCascadeTest()
    {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.BUSINESS);
        QueryMemberDto memberA = memberService.join(memberADto);
        AddCompanyResponse newCompany = companyService.addCompany("companyA", memberA.getNumber(), new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");

        memberService.withdraw("memberA");
        Assertions.assertEquals(1, memberRepository.chkWithdraw("memberA"));
        Assertions.assertNull(companyService.findCompany(newCompany.getCompanyId()));
    }

    /**
     * 전국 각지의 중복된 이름의 컴퍼니를 조회 한다.
     */
    @Test
    public void findCompaniesByNameTest() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn1@naver.com", "01012341234", MemberType.NORMAL);
        MemberJoinRequest memberBDto = new MemberJoinRequest("memberB", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn2@naver.com", "01012341234", MemberType.NORMAL);
        MemberJoinRequest memberCDto = new MemberJoinRequest("memberC", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn3@naver.com", "01012341234", MemberType.NORMAL);
        MemberJoinRequest memberDDto = new MemberJoinRequest("memberD", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn4@naver.com", "01012341234", MemberType.NORMAL);
        QueryMemberDto memberA = memberService.join(memberADto);
        QueryMemberDto memberB = memberService.join(memberBDto);
        QueryMemberDto memberC = memberService.join(memberCDto);
        QueryMemberDto memberD = memberService.join(memberDDto);

        AddCompanyResponse companyA = companyService.addCompany("companyA", memberA.getNumber(), new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");
        AddCompanyResponse companyB = companyService.addCompany("companyA", memberB.getNumber(), new Address("seoul", "hongdaero", "12456"), "01012341234", "서울 홍대로에 위치한 풋살장");
        AddCompanyResponse companyC = companyService.addCompany("companyA", memberC.getNumber(), new Address("daegu", "donseungro", "45678"), "01012341234", "대구 동성로에 위치한 풋살장");
        AddCompanyResponse companyD = companyService.addCompany("companyA", memberD.getNumber(), new Address("ulsan", "sansanro", "56789"), "01012341234", "울산 삼산로에 위치한 풋살장");

        List<QueryCompanyDto> result = companyService.findCompaniesByName("companyA");

        Assertions.assertEquals(4, result.size());
        for (QueryCompanyDto c : result) {
            Assertions.assertEquals("companyA", c.getCompanyName());
        }
    }

}