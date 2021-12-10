package com.deu.football_love.service;

import com.deu.football_love.dto.auth.TokenInfo;
import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.auth.LoginRequest;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.MemberResponse;
import com.deu.football_love.dto.member.UpdateMemberRequest;

public interface MemberService {
    boolean isDuplicationId(String id);

    boolean isDuplicationEmail(String email);

    MemberResponse join(MemberJoinRequest joinRequest);

    MemberResponse findMember(Long number);

    MemberResponse findMemberById(String id);

    MemberResponse modifyByMemberNumber(Long memberNum, UpdateMemberRequest request);

    MemberResponse modifyByMemberId(String memberId, UpdateMemberRequest request);

    boolean withdraw(String id);

    String checkMemberAuthority(String memberId, String teamName);

    LoginInfo findMemberById_jwt(String id);

    TokenInfo login_jwt(LoginRequest loginRequest);
}
