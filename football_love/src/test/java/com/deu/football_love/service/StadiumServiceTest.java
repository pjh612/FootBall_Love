package com.deu.football_love.service;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.Company;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.company.AddCompanyResponse;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.MemberResponse;
import com.deu.football_love.dto.stadium.AddStadiumResponse;
import com.deu.football_love.dto.stadium.QueryStadiumDto;
import com.deu.football_love.repository.CompanyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        MemberResponse memberA = memberService.join(memberADto);
        AddCompanyResponse companyA = companyService.addCompany("companyA", memberA.getNumber(), new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");
        AddStadiumResponse newStadium = stadiumService.addStadium(companyA.getCompanyId(), "인조잔디", "최대 4 : 4", 55000L);


        Company company = companyRepository.selectCompany(companyA.getCompanyId());

        Assertions.assertNotNull(stadiumService.findStadium(newStadium.getId()));
        Assertions.assertEquals(newStadium.getId(), company.getStadiums().get(0).getId());
    }

    @Test
    void findStadium() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        MemberResponse memberA = memberService.join(memberADto);
        AddCompanyResponse companyA = companyService.addCompany("companyA", memberA.getNumber(), new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");
        AddStadiumResponse newStadium = stadiumService.addStadium(companyA.getCompanyId(), "인조잔디", "최대 4 : 4", 55000L);

        QueryStadiumDto findStadium = stadiumService.findStadium(newStadium.getId());
        Assertions.assertEquals(newStadium.getId(), findStadium.getId());
        Assertions.assertEquals("인조잔디", findStadium.getType());
        Assertions.assertEquals("최대 4 : 4", findStadium.getSize());
        Assertions.assertEquals(55000L, findStadium.getCost());
    }

    @Test
    void findAllStadiumById() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        MemberResponse memberA = memberService.join(memberADto);
        AddCompanyResponse companyA = companyService.addCompany("companyA", memberA.getNumber(), new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");
        AddStadiumResponse stadiumA = stadiumService.addStadium(companyA.getCompanyId(), "인조잔디", "최대 4 : 4", 55000L);
        AddStadiumResponse stadiumB = stadiumService.addStadium(companyA.getCompanyId(), "인조잔디", "최대 3 : 3", 45000L);
        AddStadiumResponse stadiumC = stadiumService.addStadium(companyA.getCompanyId(), "천연잔디", "최대 4 : 4", 35000L);
        AddStadiumResponse stadiumD = stadiumService.addStadium(companyA.getCompanyId(), "인조잔디", "최대 2 : 2", 25000L);

        List<QueryStadiumDto> result = stadiumService.findAllStadiumByCompanyId(companyA.getCompanyId());
        for (QueryStadiumDto stadiumDto : result) {
            System.out.println("stadiumDto = " + stadiumDto);
        }
        Assertions.assertEquals(4, result.size());
    }

    @Test
    void removeStadium() {
        MemberJoinRequest memberADto = new MemberJoinRequest("memberA", passwordEncoder.encode("1234"), "jinhyungPark", "jinhyungPark", LocalDate.now(), new Address("busan", "guemgangro", "46233"), "pjh_jn@naver.com", "01012341234", MemberType.NORMAL);
        MemberResponse memberA = memberService.join(memberADto);
        AddCompanyResponse companyA = companyService.addCompany("companyA", memberA.getNumber(), new Address("busan", "geumgangro", "46233"), "01012341234", "부산 금강로에 위치한 풋살장");
        AddStadiumResponse newStadium = stadiumService.addStadium(companyA.getCompanyId(), "인조잔디", "최대 4 : 4", 55000L);
        stadiumService.deleteStadium(newStadium.getId());

        QueryStadiumDto findStadium = stadiumService.findStadium(newStadium.getId());
        Assertions.assertNull(findStadium);
    }
}