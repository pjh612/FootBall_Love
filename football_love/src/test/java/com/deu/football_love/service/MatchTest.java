package com.deu.football_love.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.company.AddCompanyResponse;
import com.deu.football_love.dto.match.MatchApplicationResponse;
import com.deu.football_love.dto.match.MatchApproveResponse;
import com.deu.football_love.dto.match.MatchResponse;
import com.deu.football_love.dto.match.ModifyMatchResponse;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.stadium.AddStadiumResponse;
import com.deu.football_love.dto.team.CreateTeamResponse;

@SpringBootTest
@Transactional
public class MatchTest {

	@Autowired
	private MatchService matchService;

	@Autowired
	private TeamService teamService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private StadiumService stadiumService;
	
	@Autowired
	private CompanyService companyService;
	
	@Test
	public void 매치_생성_찾기() {
		MemberJoinRequest joinInfo = new MemberJoinRequest("dbtlwns","1234","금꽁치","유시준",LocalDate.of(1995,5,2), new Address("1", "2", "3"),"simba0502@naver.com" ,"010-6779-3476",MemberType.BUSINESS);
		QueryMemberDto memberInfo = memberService.join(joinInfo);
		CreateTeamResponse teamInfo = teamService.createNewTeam(memberInfo.getId(), "FC FLOW");
		
		Address address = new Address("1", "1", "9");
		AddCompanyResponse companyInfo = companyService.addCompany("(주)아프리카TV", memberInfo.getNumber(), address, "010-6779-3476", "좋은 회사");
		
		AddStadiumResponse stadiumInfo = stadiumService.addStadium(companyInfo.getCompanyId(), "풋살장", "30*50", 120000L);
		MatchResponse matchDto = matchService.addMatch(teamInfo.getTeamId(),stadiumInfo.getId(),LocalDateTime.now());
		MatchResponse findMatchDto = matchService.findMatch(matchDto.getMatchId());
		
		assertAll(() -> assertEquals(matchDto.getMatchId(), findMatchDto.getMatchId()),
				() -> assertTrue(matchDto.getTeamName().equals(findMatchDto.getTeamName())),
				() -> assertEquals(matchDto.getStadiumId(), findMatchDto.getStadiumId()),
				() -> assertEquals(matchDto.getReservation_time(), findMatchDto.getReservation_time())
				);
	}

	@Test
	public void 매치_신청_승인() {
		MemberJoinRequest joinInfo1 = new MemberJoinRequest("dbtlwns","1234","금꽁치","유시준",LocalDate.of(1995,5,2), new Address("1", "2", "3"),"simba0502@naver.com" ,"010-6779-3476",MemberType.BUSINESS);
		QueryMemberDto memberInfo1 = memberService.join(joinInfo1);
		CreateTeamResponse teamInfo1 = teamService.createNewTeam(memberInfo1.getId(), "FC FLOW");
		
		MemberJoinRequest joinInfo2 = new MemberJoinRequest("pjh612","1234","비빔면진형","박진형",LocalDate.of(1996,5,2), new Address("1", "2", "3"),"pjh612@naver.com" ,"010-6779-3476",MemberType.NORMAL);
		QueryMemberDto memberInfo2 = memberService.join(joinInfo2);
		CreateTeamResponse teamInfo2 = teamService.createNewTeam(memberInfo2.getId(), "FC LOL");
		
		Address address = new Address("1", "1", "9");
		AddCompanyResponse companyInfo = companyService.addCompany("(주)아프리카TV", memberInfo1.getNumber(), address, "010-6779-3476", "좋은 회사");
		
		AddStadiumResponse stadiumInfo = stadiumService.addStadium(companyInfo.getCompanyId(), "풋살장", "30*50", 120000L);
		MatchResponse matchDto = matchService.addMatch(teamInfo1.getTeamId(),stadiumInfo.getId(),LocalDateTime.now());
		MatchApplicationResponse matchApplication = matchService.addMatchApplication(teamInfo2.getTeamId(), matchDto.getMatchId());
		
		MatchApproveResponse match = matchService.approveMatch(matchDto.getMatchId(),matchApplication.getMatchApplicationId());
		
		assertTrue(match.getApproval());
		
	}
	
	@Test
	public void 매치_취소() {
		MemberJoinRequest joinInfo = new MemberJoinRequest("dbtlwns","1234","금꽁치","유시준",LocalDate.of(1995,5,2), new Address("1", "2", "3"),"simba0502@naver.com" ,"010-6779-3476",MemberType.BUSINESS);
		QueryMemberDto memberInfo = memberService.join(joinInfo);
		CreateTeamResponse teamInfo = teamService.createNewTeam(memberInfo.getId(), "FC FLOW");
		
		Address address = new Address("1", "1", "9");
		AddCompanyResponse companyInfo = companyService.addCompany("(주)아프리카TV", memberInfo.getNumber(), address, "010-6779-3476", "좋은 회사");
		
		AddStadiumResponse stadiumInfo = stadiumService.addStadium(companyInfo.getCompanyId(), "풋살장", "30*50", 120000L);
		MatchResponse matchDto = matchService.addMatch(teamInfo.getTeamId(),stadiumInfo.getId(),LocalDateTime.now());
		matchService.cancelMatch(matchDto.getMatchId());
		MatchResponse findMatchDto = matchService.findMatch(matchDto.getMatchId());
		assertNull(findMatchDto);
	}

	@Test
	public void 매치_수정() {
		MemberJoinRequest joinInfo = new MemberJoinRequest("dbtlwns","1234","금꽁치","유시준",LocalDate.of(1995,5,2), new Address("1", "2", "3"),"simba0502@naver.com" ,"010-6779-3476",MemberType.BUSINESS);
		QueryMemberDto memberInfo = memberService.join(joinInfo);
		CreateTeamResponse teamInfo = teamService.createNewTeam(memberInfo.getId(), "FC FLOW");
		
		Address address = new Address("1", "1", "9");
		AddCompanyResponse companyInfo = companyService.addCompany("(주)아프리카TV", memberInfo.getNumber(), address, "010-6779-3476", "좋은 회사");
		
		AddStadiumResponse stadiumInfo = stadiumService.addStadium(companyInfo.getCompanyId(), "풋살장", "30*50", 120000L);
		AddStadiumResponse soccerStadiumInfo = stadiumService.addStadium(companyInfo.getCompanyId(), "축구장", "120*60", 180000L);
		
		MatchResponse matchDto = matchService.addMatch(teamInfo.getTeamId(),stadiumInfo.getId(),LocalDateTime.now());
		ModifyMatchResponse modifyMatchDto = matchService.modifyMatch(matchDto.getMatchId(), soccerStadiumInfo.getId(), LocalDateTime.now().minusHours(2L));
		MatchResponse findMatchDto = matchService.findMatch(modifyMatchDto.getMatchId());
		
		assertAll(() -> assertEquals(matchDto.getMatchId(), findMatchDto.getMatchId()),
				() -> assertTrue(matchDto.getTeamName().equals(findMatchDto.getTeamName())),
				() -> assertNotEquals(matchDto.getStadiumId(), findMatchDto.getStadiumId()),
				() -> assertNotEquals(matchDto.getReservation_time(), findMatchDto.getReservation_time())
				);
	}
}
