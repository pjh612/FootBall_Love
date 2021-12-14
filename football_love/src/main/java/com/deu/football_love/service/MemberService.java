package com.deu.football_love.service;

import com.deu.football_love.dto.auth.TokenInfo;
import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.auth.LoginRequest;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.member.UpdateMemberRequest;

import java.util.List;

public interface MemberService {
    boolean isDuplicationId(String id);

    boolean isDuplicationEmail(String email);

    QueryMemberDto login(LoginRequest loginRequest);

    QueryMemberDto join(MemberJoinRequest joinRequest);

    QueryMemberDto findMember(Long number);

    QueryMemberDto findMemberById(String id);

    List<QueryMemberDto> findMemberDto(Long number);

    QueryMemberDto modifyByMemberNumber(Long memberNum, UpdateMemberRequest request);

    QueryMemberDto modifyByMemberId(String memberId, UpdateMemberRequest request);

    boolean withdraw(String id);

    String checkMemberAuthority(String memberId, String teamName);

    LoginInfo findMemberById_jwt(String id);

    //Jwt Test
    TokenInfo login_jwt(LoginRequest loginRequest);
}
