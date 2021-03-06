package com.deu.football_love.controller;

import com.deu.football_love.dto.member.BusinessJoinRequest;
import com.deu.football_love.dto.member.BusinessJoinResponse;
import com.deu.football_love.service.JoinService;
import java.util.ArrayList;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.deu.football_love.config.JwtTokenProvider;
import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.auth.LoginRequest;
import com.deu.football_love.dto.auth.LoginResponse;
import com.deu.football_love.dto.auth.ValidRefreshTokenResponse;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.member.UpdateMemberRequest;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.redis.RedisService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
public class MemberController {

  private final MemberService memberService;
  private final JoinService joinService;
  private final JwtTokenProvider jwtTokenProvider;
  private final RedisService redisService;

  @GetMapping("/{number}")
  public QueryMemberDto getMember(@PathVariable(name = "number") Long number) {
    log.info(Long.toString(number));
    return memberService.findQueryMemberDtoByNumber(number);
  }

  @GetMapping("/auth")
  public LoginInfo get(@AuthenticationPrincipal LoginInfo loginInfo) {
    return loginInfo;
  }

  @ApiOperation(value = "일반회원 회원가입 요청")
  @PostMapping
  public ResponseEntity<QueryMemberDto> normalJoin(@Valid @RequestBody MemberJoinRequest joinRequest) {
    QueryMemberDto joinMember = memberService.join(joinRequest);
    return new ResponseEntity<>(joinMember, HttpStatus.OK);
  }

  @ApiOperation(value = "비즈니스 회원가입 요청")
  @PostMapping("/business")
  public ResponseEntity<BusinessJoinResponse> businessJoin(@Valid @RequestBody BusinessJoinRequest joinRequest) {
    BusinessJoinResponse response = joinService.businessJoin(joinRequest);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }


  @GetMapping("/loginInfo")
  public ResponseEntity getMember(@AuthenticationPrincipal LoginInfo loginInfo) {
    if (loginInfo.getNumber() == -1) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    return new ResponseEntity(memberService.findQueryMemberDtoByNumber(loginInfo.getNumber()), HttpStatus.OK);
  }

  @ApiOperation(value = "jwt 로그인 요청")
  @PostMapping("/login_jwt/{id}")
  public ResponseEntity<LoginResponse> login_jwt(@RequestBody LoginRequest loginRequest,
      HttpServletResponse response) {
    LoginResponse loginResponse = memberService.login_jwt(loginRequest);
    ArrayList<String> data = new ArrayList<>();
    data.add(loginRequest.getId());
    data.add(loginResponse.getAccessToken());
    ResponseCookie accessTokenCookie =
        ResponseCookie.from(JwtTokenProvider.ACCESS_TOKEN_NAME, loginResponse.getAccessToken())
            .path("/").secure(true).sameSite("None").build();
    ResponseCookie refreshTokenCookie =
        ResponseCookie.from(JwtTokenProvider.REFRESH_TOKEN_NAME, loginResponse.getRefreshToken())
            .path("/").secure(true).sameSite("None").build();
    response.setHeader("Set-Cookie", accessTokenCookie.toString());
    response.addHeader("Set-Cookie", refreshTokenCookie.toString());
    redisService.setStringValue(loginResponse.getRefreshToken(), data,
        JwtTokenProvider.REFRESH_TOKEN_VALIDATION_SECOND);
    System.out.println("로그인 성공");
    return new ResponseEntity<>(loginResponse, HttpStatus.OK);
  }

  @PostMapping("/refresh")
  public ResponseEntity<ValidRefreshTokenResponse> refresh(HttpServletResponse response,
      @CookieValue(value = "accessToken") String accessToken,
      @CookieValue(value = "refreshToken") String refreshToken) {
    if (accessToken == null || refreshToken == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    ValidRefreshTokenResponse result =
        jwtTokenProvider.validateRefreshToken(accessToken, refreshToken);
    log.info("validate result = {} ", result);
    if (result.getStatus() == 200) {
      response.addCookie((new Cookie("accessToken", result.getAccessToken())));
      return new ResponseEntity<>(result, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "로그아웃 요청")
  @PostMapping("/logout_jwt")
  public ResponseEntity<LoginResponse> logout_jwt(@AuthenticationPrincipal LoginInfo principal,
      @CookieValue(value = "accessToken") String accessToken
      , @CookieValue(value = "refreshToken") String refreshToken
  ) {
    log.info("컨트롤러 진입");
    if (accessToken == null || !jwtTokenProvider.validateToken(accessToken) || refreshToken == null || !jwtTokenProvider
        .validateToken(refreshToken)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    log.info("access Token = {} ", accessToken);
    log.info("accessToken validate = {}", jwtTokenProvider.validateToken(accessToken));
    Long remainExpiration = jwtTokenProvider.remainExpiration(accessToken);
    if (remainExpiration >= 1) {
      log.info("블랙리스트 등록");
      redisService.del(refreshToken);
      redisService.setStringValue(accessToken, "true", remainExpiration);
      log.info("remain time  = {}", remainExpiration);

      return new ResponseEntity(HttpStatus.OK);
    }
    return new ResponseEntity(HttpStatus.UNAUTHORIZED);
  }

  @ApiOperation(value = "아이디 중복확인 요청")
  @GetMapping("/duplication/id")
  public ResponseEntity isDuplicaitonId(@RequestParam String id) {
    if (!memberService.isDuplicationId(id)) {
      return new ResponseEntity(HttpStatus.OK);
    } else {
      return new ResponseEntity(HttpStatus.CONFLICT);
    }
  }

  @ApiOperation(value = "이메일 중복확인 요청")
  @GetMapping("/duplication/email")
  public ResponseEntity isDuplicaitonEmail(@RequestParam String email) {
    if (!memberService.isDuplicationEmail(email)) {
      return new ResponseEntity(HttpStatus.OK);
    } else {
      return new ResponseEntity(HttpStatus.CONFLICT);
    }
  }

  @ApiOperation(value = "회원정보 수정요청")
  @PutMapping
  public ResponseEntity<QueryMemberDto> modify(@Valid @RequestBody UpdateMemberRequest request,
      @AuthenticationPrincipal LoginInfo loginInfo) {
    QueryMemberDto modifiedMember = memberService.modifyByMemberId(loginInfo.getId(), request);
    return new ResponseEntity<>(modifiedMember, HttpStatus.OK);
  }

  @ApiOperation(value = "회원탈퇴 요청", notes = "id와 회원을 확인해 회원탈퇴 요청을 한다.")
  @PutMapping("/withdrawals")
  public ResponseEntity withdrawMember(@AuthenticationPrincipal LoginInfo loginInfo) {
    boolean deleteFlag = memberService.withdraw(loginInfo.getId());
    if (deleteFlag) {
      return new ResponseEntity(HttpStatus.OK);
    } else {
      return new ResponseEntity(HttpStatus.CONFLICT);
    }
  }

}
