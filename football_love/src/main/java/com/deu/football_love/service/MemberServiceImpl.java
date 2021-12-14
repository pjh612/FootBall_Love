package com.deu.football_love.service;

import com.deu.football_love.config.JwtTokenProvider;
import com.deu.football_love.domain.*;
import com.deu.football_love.dto.auth.TokenInfo;
import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.auth.LoginRequest;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.member.UpdateMemberRequest;
import com.deu.football_love.repository.PostRepository;
import com.deu.football_love.repository.TeamRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deu.football_love.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional(readOnly = true)
    public TokenInfo login_jwt(LoginRequest loginRequest) {
        Member member = memberRepository.selectMemberById(loginRequest.getId());
        if (member != null && member.getId().equals(loginRequest.getId()) && passwordEncoder.matches(loginRequest.getPwd(), member.getPwd())) {
            List<String> roleList = Arrays.asList(member.getMemberType().name());
            String accessToken = jwtTokenProvider.createAccessToken(member.getId(), roleList);
            String refreshToken = jwtTokenProvider.createRefreshToken();
            return new TokenInfo("success", "create token success", accessToken, refreshToken);
        } else
            return new TokenInfo("fail", "create token fail", null, null);
    }

    @Override
    public QueryMemberDto join(MemberJoinRequest joinRequest) {
        String password = joinRequest.getPwd();
        String encodedPassword = passwordEncoder.encode(password);
        joinRequest.setPwd(encodedPassword);

        Member member = new Member();
        member.setAddress(joinRequest.getAddress());
        member.setBirth(joinRequest.getBirth());
        member.setEmail(joinRequest.getEmail());
        member.setId(joinRequest.getId());
        member.setPwd(joinRequest.getPwd());
        member.setNickname(joinRequest.getNickname());
        member.setName(joinRequest.getName());
        member.setPhone(joinRequest.getPhone());
        member.setMemberType(joinRequest.getType());
        Long number = memberRepository.insertMember(member);
        member.setCreatedBy(number);

        member.setLastModifiedBy(number);
        QueryMemberDto memberResponse = new QueryMemberDto(member);

        System.out.println("memberResponse = " + memberResponse);
        return memberResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isDuplicationId(String id) {
        int cnt = memberRepository.isDuplicationId(id);
        if (cnt == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isDuplicationEmail(String email) {
        int cnt = memberRepository.isDuplicationEmail(email);
        if (cnt == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public QueryMemberDto findMember(Long number) {
        Member member = memberRepository.selectMember(number);
        return new QueryMemberDto(member);
    }

    @Override
    @Transactional(readOnly = true)
    public QueryMemberDto findMemberById(String id) {
        Member member = memberRepository.selectMemberById(id);
        return new QueryMemberDto(member);
    }

    @Override
    @Transactional(readOnly = true)
    public LoginInfo findMemberById_jwt(String id) {
        Member member = memberRepository.selectMemberById(id);
        return new LoginInfo(member);
    }

    public List<QueryMemberDto> findMemberDto(Long number) {
        return memberRepository.selectQueryMemberDto(number);
    }

    @Override
    public QueryMemberDto modifyByMemberNumber(Long memberNum, UpdateMemberRequest request) {
        Member findMember = memberRepository.selectMember(memberNum);
        if (!passwordEncoder.matches(request.getPwd(), findMember.getPwd()))
            findMember.setPwd(passwordEncoder.encode(request.getPwd()));
        findMember.setEmail(request.getEmail());
        findMember.setName(request.getName());
        findMember.setNickname(request.getNickname());
        findMember.setBirth(request.getBirth());
        findMember.setAddress(request.getAddress());
        findMember.setPhone(request.getPhone());
        return new QueryMemberDto(findMember);
    }

    @Override
    public QueryMemberDto modifyByMemberId(String memberId, UpdateMemberRequest request) {
        Member findMember = memberRepository.selectMemberById(memberId);
        if (!passwordEncoder.matches(request.getPwd(), findMember.getPwd()))
            findMember.setPwd(passwordEncoder.encode(request.getPwd()));
        findMember.setEmail(request.getEmail());
        findMember.setName(request.getName());
        findMember.setNickname(request.getNickname());
        findMember.setBirth(request.getBirth());
        findMember.setAddress(request.getAddress());
        findMember.setPhone(request.getPhone());
        return new QueryMemberDto(findMember);
    }

    @Override
    public boolean withdraw(String id) {
        Member findMember = memberRepository.selectMemberById(id);
        if (findMember == null)
            return false;
        Long withDrawCnt = memberRepository.chkWithDraw(id);
        memberRepository.updateWithdraw(id);
        while (findMember.getPosts().size() != 0) {
            Post post = findMember.getPosts().get(0);
            post.deletePost();
            postRepository.deletePost(post);
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public String checkMemberAuthority(String memberId, String teamName) {
        return memberRepository.selectMemberAuthority(memberId, teamName);
    }
}
