package com.deu.football_love.service;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.AddCompanyResponse;
import com.deu.football_love.dto.CompanyDto;
import com.deu.football_love.dto.JoinRequest;
import com.deu.football_love.dto.MemberResponse;
import com.deu.football_love.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    @Test
    public void addAndFindCompanyTest()
    {
        JoinRequest memberADto = new JoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(),new Address("busan","guemgangro","46233"),"pjh_jn@naver.com","01012341234", MemberType.NORMAL);
        MemberResponse memberA = memberService.join(memberADto);
        AddCompanyResponse newCompany= companyService.addCompany("companyA",memberA.getId(), new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");

        CompanyDto findCompany = companyService.findCompany(newCompany.getCompanyId());
        Assertions.assertEquals(newCompany.getCompanyId(), findCompany.getCompanyId());

    }

    @Test
    public void withdrawalCompanyTest()
    {
        JoinRequest memberADto = new JoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(),new Address("busan","guemgangro","46233"),"pjh_jn@naver.com","01012341234", MemberType.NORMAL);
        MemberResponse memberA = memberService.join(memberADto);
        AddCompanyResponse newCompany= companyService.addCompany("companyA",memberA.getId(), new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");

        companyService.withdrawalCompany(newCompany.getCompanyId());

        List<CompanyDto> findCompanies = companyService.findCompaniesByName("companyA");
        CompanyDto findCompany = companyService.findCompany(newCompany.getCompanyId());
        Assertions.assertEquals(0, findCompanies.size());
        Assertions.assertNull(findCompany);
    }

    /**
     * 전국 각지의 중복된 이름의 컴퍼니를 조회 한다.
     */
    @Test
    public void findCompaniesByNameTest()
    {
        JoinRequest memberADto = new JoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(),new Address("busan","guemgangro","46233"),"pjh_jn@naver.com","01012341234", MemberType.NORMAL);
        JoinRequest memberBDto = new JoinRequest("memberB", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(),new Address("busan","guemgangro","46233"),"pjh_jn@naver.com","01012341234", MemberType.NORMAL);
        JoinRequest memberCDto = new JoinRequest("memberC", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(),new Address("busan","guemgangro","46233"),"pjh_jn@naver.com","01012341234", MemberType.NORMAL);
        JoinRequest memberDDto = new JoinRequest("memberD", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(),new Address("busan","guemgangro","46233"),"pjh_jn@naver.com","01012341234", MemberType.NORMAL);
        MemberResponse memberA = memberService.join(memberADto);
        MemberResponse memberB = memberService.join(memberADto);
        MemberResponse memberC = memberService.join(memberADto);
        MemberResponse memberD = memberService.join(memberADto);

        AddCompanyResponse companyA= companyService.addCompany("companyA", memberA.getId(), new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");
        AddCompanyResponse companyB = companyService.addCompany("companyA", memberB.getId(), new Address("seoul", "hongdaero", "12456"), "01012341234", "서울 홍대로에 위치한 풋살장");
        AddCompanyResponse companyC = companyService.addCompany("companyA", memberC.getId(), new Address("daegu", "donseungro", "45678"), "01012341234", "대구 동성로에 위치한 풋살장");
        AddCompanyResponse companyD = companyService.addCompany("companyA", memberD.getId(), new Address("ulsan", "sansanro", "56789"), "01012341234", "울산 삼산로에 위치한 풋살장");

        List<CompanyDto> result = companyService.findCompaniesByName("companyA");

        Assertions.assertEquals(4,result.size());
        for (CompanyDto c : result) {
            Assertions.assertEquals("companyA",c.getCompanyName());
        }
    }

}