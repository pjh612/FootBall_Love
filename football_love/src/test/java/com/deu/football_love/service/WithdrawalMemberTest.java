package com.deu.football_love.service;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.WithdrawalMemberRepository;
import com.deu.football_love.service.schedule.WithdrawalScheduleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@SpringBootTest
@Transactional
public class WithdrawalMemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    WithdrawalScheduleService withdrawalScheduleService;

    @Autowired
    WithdrawalMemberRepository withdrawalMemberRepository;

    @Autowired
    EntityManager em;

    /**
     * 탈퇴 후 15일이 지나면 재가입 가능하도록 WithdrawalMember 테이블에서 삭제 되는지에 대한 테스트
     */
    @Test
    public void withdrawalLimitTest() {

        Member memberA = Member.memberBuilder()
                .id("pjh612")
                .pwd("1234")
                .address(new Address("busan", "guemgangro", "46233"))
                .name("박진형")
                .nickname("박진형")
                .email("pjh_jn@naver.com")
                .phone("010-1234-4567")
                .memberType(MemberType.ROLE_NORMAL).build();


        Member memberB = Member.memberBuilder()
                .id("pjh581")
                .pwd("1234")
                .address(new Address("busan", "guemgangro", "46233"))
                .name("박진행")
                .nickname("박진행")
                .email("pjh612@naver.com")
                .phone("010-1234-4567")
                .memberType(MemberType.ROLE_NORMAL).build();

        em.persist(memberA);
        em.persist(memberB);

        memberService.withdraw("pjh612");
        memberService.withdraw("pjh581");
        LocalDateTime cur = LocalDateTime.now().plusDays(15);
        LocalDateTime threshold = cur.minusDays(16L);
        memberRepository.deleteWithdrawalMemberByDate(threshold);
        Assertions.assertEquals(true, withdrawalMemberRepository.existsByMemberId(memberA.getId()));
        Assertions.assertEquals(true, withdrawalMemberRepository.existsByMemberId(memberB.getId()));

        threshold = cur.minusDays(14L);
        memberRepository.deleteWithdrawalMemberByDate(threshold);
        Assertions.assertFalse(withdrawalMemberRepository.existsByMemberId(memberA.getId()));
        Assertions.assertFalse(withdrawalMemberRepository.existsByMemberId(memberB.getId()));
    }
}
