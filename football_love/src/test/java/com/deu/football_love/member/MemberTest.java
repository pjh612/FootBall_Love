package com.deu.football_love.member;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.member.UpdateMemberRequest;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
@RequiredArgsConstructor

public class MemberTest {

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  MemberService memberService;

  @Autowired
  TeamService teamService;

  private final LocalDate BIRTH_DAY = LocalDate.of(2000, 1, 1);

  private final Address ADDRESS = new Address("양산", "행복길", "11");

  private final MemberJoinRequest JOIN_REQUEST = new MemberJoinRequest("pjh612", "123", "jhjh",
      "박진형", BIRTH_DAY, ADDRESS, "pjh_jn@naver.com", "010-1234-5678", MemberType.NORMAL);

  @Test
  public void member_비밀번호_암호화_테스트() {
    String password = "123456789";
    String encodePassword = passwordEncoder.encode(password);
    assertAll(() -> assertNotEquals(password, encodePassword),
        () -> assertTrue(passwordEncoder.matches(password, encodePassword)));

  }

  @Test
  public void 멤버_가입() {
    MemberJoinRequest request =
        new MemberJoinRequest("dbtlwns", "1234", "금꽁치", "유시준", LocalDate.of(1995, 5, 2),
            new Address("1", "2", "3"), "simba0502@naver.com", "010-6779-3476", MemberType.NORMAL);
    QueryMemberDto memberResponse = memberService.join(request);
    assertAll(() -> assertEquals(request.getId(), memberResponse.getId()),
        () -> assertEquals(request.getNickname(), memberResponse.getNickname()),
        () -> assertEquals(request.getName(), memberResponse.getName()),
        () -> assertEquals(request.getEmail(), memberResponse.getEmail()));
  }

  @Test
  public void 멤버_찾기() {
    MemberJoinRequest joinRequest =
        new MemberJoinRequest("dbtlwns", "1234", "금꽁치", "유시준", LocalDate.of(1995, 5, 2),
            new Address("1", "2", "3"), "simba0502@naver.com", "010-6779-3476", MemberType.NORMAL);
    memberService.join(joinRequest);
    String id = "dbtlwns";
    QueryMemberDto memberResponse = memberService.findMemberById(id);
    assertAll(() -> assertEquals("dbtlwns", memberResponse.getId()),
        () -> assertEquals("금꽁치", memberResponse.getNickname()),
        () -> assertEquals("유시준", memberResponse.getName()),
        () -> assertEquals("simba0502@naver.com", memberResponse.getEmail()));
  }

  @Test
  public void 멤버_수정() {
    memberService.join(JOIN_REQUEST);
    UpdateMemberRequest request =
        UpdateMemberRequest.builder().pwd("1111").nickname("jhjh").address(ADDRESS)
            .email("dbtlwns@naver.com").phone("updatedPhone").type(MemberType.NORMAL).build();
    QueryMemberDto memberResponse = memberService.modifyByMemberId(JOIN_REQUEST.getId(), request);
    assertAll(() -> assertEquals(JOIN_REQUEST.getId(), memberResponse.getId()),
        () -> assertEquals(request.getNickname(), memberResponse.getNickname()),
        () -> assertEquals("박진형", memberResponse.getName()),
        () -> assertEquals(request.getEmail(), memberResponse.getEmail()));
  }

  @DisplayName("아이디 중복확인 true일때 아이디 중복")
  @Test
  public void 멤버_아이디중복확인() {
    MemberJoinRequest joinRequest =
        new MemberJoinRequest("dbtlwns", "1234", "금꽁치", "유시준", LocalDate.of(1995, 5, 2),
            new Address("1", "2", "3"), "simba0502@naver.com", "010-6779-3476", MemberType.NORMAL);
    memberService.join(joinRequest);
    String id = "dbtlwns";
    assertTrue(memberService.isDuplicationId(id));
  }

  @DisplayName("이메일 중복확인 true일때 이메일 중복")
  @Test
  public void 멤버_이메일중복확인() {
    MemberJoinRequest joinRequest =
        new MemberJoinRequest("dbtlwns", "1234", "금꽁치", "유시준", LocalDate.of(1995, 5, 2),
            new Address("1", "2", "3"), "simba0502@naver.com", "010-6779-3476", MemberType.NORMAL);
    memberService.join(joinRequest);
    String email = "simba0502@naver.com";
    assertTrue(memberService.isDuplicationEmail(email));
  }

  @Test
  public void 멤버_그룹권한확인() {
    MemberJoinRequest joinRequest =
        new MemberJoinRequest("dbtlwns", "1234", "금꽁치", "유시준", LocalDate.of(1995, 5, 2),
            new Address("1", "2", "3"), "simba0502@naver.com", "010-6779-3476", MemberType.NORMAL);
    memberService.join(joinRequest);

    teamService.createNewTeam("dbtlwns", "FC Flow");

    String memberId = "dbtlwns";
    String teamName = "FC Flow";
    assertEquals(memberService.checkMemberAuthority(memberId, teamName), TeamMemberType.LEADER);
  }

  @DisplayName("회원탈퇴 true일때 성공")
  @Test
  public void 멤버_탈퇴() {
    MemberJoinRequest joinRequest =
        new MemberJoinRequest("dbtlwns", "1234", "금꽁치", "유시준", LocalDate.of(1995, 5, 2),
            new Address("1", "2", "3"), "simba0502@naver.com", "010-6779-3476", MemberType.NORMAL);
    memberService.join(joinRequest);
    assertTrue(memberService.withdraw(joinRequest.getId()));
  }
}
