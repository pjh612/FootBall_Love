package com.deu.football_love.service;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.Company;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.*;
import com.deu.football_love.repository.CompanyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
@SpringBootTest
class StadiumServiceTest {

    @Autowired
    private StadiumService stadiumService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    void addStadium() {
        JoinRequest memberADto = new JoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(),new Address("busan","guemgangro","46233"),"pjh_jn@naver.com","01012341234", MemberType.NORMAL);
        MemberResponse memberA = memberService.join(memberADto);
        AddCompanyResponse companyA = companyService.addCompany("companyA",memberA.getId(), new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");
        AddStadiumResponse newStadium = stadiumService.addStadium(companyA.getCompanyId(), "인조잔디", "최대 4 : 4", 55000L);


        Company company = companyRepository.selectCompany(companyA.getCompanyId());

        Assertions.assertNotNull(stadiumService.findStadium(newStadium.getId()));
        Assertions.assertEquals(newStadium.getId(), company.getStadiums().get(0).getId());
    }

    @Test
    void findStadium() {
        JoinRequest memberADto = new JoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(),new Address("busan","guemgangro","46233"),"pjh_jn@naver.com","01012341234", MemberType.NORMAL);
        MemberResponse memberA = memberService.join(memberADto);
        AddCompanyResponse companyA = companyService.addCompany("companyA",memberA.getId(), new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");
        AddStadiumResponse newStadium = stadiumService.addStadium(companyA.getCompanyId(), "인조잔디", "최대 4 : 4", 55000L);

        StadiumDto findStadium = stadiumService.findStadium(newStadium.getId());
        Assertions.assertEquals(newStadium.getId(), findStadium.getId() );
        Assertions.assertEquals("인조잔디", findStadium.getType());
        Assertions.assertEquals("최대 4 : 4", findStadium.getSize());
        Assertions.assertEquals(55000L, findStadium.getCost());
    }

    @Test
    void removeStadium() {
        JoinRequest memberADto = new JoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(),new Address("busan","guemgangro","46233"),"pjh_jn@naver.com","01012341234", MemberType.NORMAL);
        MemberResponse memberA = memberService.join(memberADto);
        AddCompanyResponse companyA = companyService.addCompany("companyA",memberA.getId(), new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");
        AddStadiumResponse newStadium = stadiumService.addStadium(companyA.getCompanyId(), "인조잔디", "최대 4 : 4", 55000L);
        stadiumService.removeStadium(newStadium.getId());

        StadiumDto findStadium = stadiumService.findStadium(newStadium.getId());
        Assertions.assertNull(findStadium);
    }
}