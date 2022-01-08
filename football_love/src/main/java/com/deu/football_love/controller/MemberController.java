package com.deu.football_love.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deu.football_love.config.JwtTokenProvider;
import com.deu.football_love.controller.consts.SessionConst;
import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.auth.LoginResponse;
import com.deu.football_love.dto.auth.LoginRequest;
import com.deu.football_love.dto.auth.ValidRefreshTokenResponse;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.member.UpdateMemberRequest;
import com.deu.football_love.service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.deu.football_love.domain.Member;
import com.deu.football_love.service.MemberService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    @GetMapping("/{number}")
    public List<QueryMemberDto> getMember(@PathVariable(name = "number") Long number) {
        log.info(Long.toString(number));
        return memberService.findMemberDto(number);
    }

    @GetMapping("/auth")
    public LoginInfo get(@AuthenticationPrincipal LoginInfo loginInfo) {
        return loginInfo;
    }

    @ApiOperation(value = "회원가입 요청")
    @PostMapping
    public ResponseEntity<QueryMemberDto> join(@RequestBody MemberJoinRequest joinRequest) {
        QueryMemberDto joinMember = memberService.join(joinRequest);
        if (joinMember == null) {
            return new ResponseEntity<QueryMemberDto>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<QueryMemberDto>(joinMember, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "jwt 로그인 요청")
    @PostMapping("/login_jwt/{id}")
    public ResponseEntity<LoginResponse> login_jwt(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginResponse loginResponse = memberService.login_jwt(loginRequest);
        if (loginResponse.getResult().equals("fail")) {
            return new ResponseEntity<LoginResponse>(HttpStatus.CONFLICT);
        } else {
            ArrayList<String> data = new ArrayList<>();
            data.add(loginRequest.getId());
            data.add(loginResponse.getAccessToken());
            Cookie accessTokenCookie = new Cookie(JwtTokenProvider.ACCESS_TOKEN_NAME, loginResponse.getAccessToken());
            Cookie refreshTokenCookie = new Cookie(JwtTokenProvider.REFRESH_TOKEN_NAME, loginResponse.getRefreshToken());
            // accessTokenCookie.setMaxAge((int) JwtTokenProvider.TOKEN_VALIDATION_SECOND);
            // accessTokenCookie.setSecure(true);
            // accessTokenCookie.setHttpOnly(true);
            // refreshTokenCookie.setMaxAge((int) JwtTokenProvider.REFRESH_TOKEN_VALIDATION_SECOND);
            // refreshTokenCookie.setSecure(true);
            // refreshTokenCookie.setHttpOnly(true);
            response.addCookie(accessTokenCookie);
            response.addCookie(refreshTokenCookie);
            redisService.setStringValue(loginResponse.getRefreshToken(), data, JwtTokenProvider.REFRESH_TOKEN_VALIDATION_SECOND);
            return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity refresh(HttpServletResponse response,
                                  @CookieValue(value = "accessToken") String accessToken
            , @CookieValue(value = "refreshToken") String refreshToken) {
        if (accessToken == null || refreshToken == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        ValidRefreshTokenResponse result = jwtTokenProvider.validateRefreshToken(accessToken, refreshToken);
        log.info("validate result = {} ", result);
        if (result.getStatus() == 200) {
            response.addCookie((new Cookie("accessToken", result.getAccessToken())));
            return new ResponseEntity(result, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "로그아웃 요청")
    @PostMapping("/logout_jwt")
    public ResponseEntity<LoginResponse> logout_jwt(@AuthenticationPrincipal LoginInfo principal,
                                                    @CookieValue(value = "accessToken") String accessToken
            , @CookieValue(value = "refreshToken") String refreshToken
    ) {
        log.info("accessToken = {}", accessToken);
        log.info("refreshToken = {}", refreshToken);
        if (accessToken == null || !jwtTokenProvider.validateToken(accessToken) || refreshToken == null || !jwtTokenProvider.validateToken(refreshToken)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Long remainExpiration = jwtTokenProvider.remainExpiration(accessToken);
        log.info("login info = {}", (principal));

        if (remainExpiration >= 1) {
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
    @PutMapping("/{memberId}")
    public ResponseEntity<QueryMemberDto> modify(@PathVariable String memberId, @RequestBody UpdateMemberRequest request, HttpSession session) {
        QueryMemberDto modifiedMember = memberService.modifyByMemberId(memberId, request);
        return new ResponseEntity<QueryMemberDto>(modifiedMember, HttpStatus.OK);
    }

    @ApiOperation(value = "회원탈퇴 요청", notes = "id와 회원을 확인해 회원탈퇴 요청을 한다.")
    @PutMapping("/withdrawals/{id}")
    public ResponseEntity withdrawMember(@PathVariable String id, @AuthenticationPrincipal LoginInfo loginInfo) {

        QueryMemberDto findMember = memberService.findMemberById(id);
        if(loginInfo.getNumber() != findMember.getNumber())
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        boolean deleteFlag = memberService.withdraw(id);
        if (deleteFlag) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "회원 권한확인 요청")
    @GetMapping("/{memberId}/authority")
    public ResponseEntity<TeamMemberType> checkMemberAuthority(@RequestParam("memberId") String memberId,
                                                       @RequestParam("teamName") String teamName, HttpSession session) {
        Member sessionMember = (Member) session.getAttribute(SessionConst.SESSION_MEMBER);
        if (sessionMember == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        TeamMemberType authority = memberService.checkMemberAuthority(memberId, teamName);
        if (authority == null) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<TeamMemberType>(authority, HttpStatus.OK);
        }
    }

	/*@ExceptionHandler(Exception.class)
	public ResponseEntity serverException() {
		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
	}*/
}
