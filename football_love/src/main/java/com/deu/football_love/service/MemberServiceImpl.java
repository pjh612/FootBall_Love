package com.deu.football_love.service;

import com.deu.football_love.config.JwtTokenProvider;
import com.deu.football_love.dto.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deu.football_love.domain.Member;
import com.deu.football_love.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;

	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	@Override
	@Transactional(readOnly = true)
	public MemberResponse login(LoginRequest loginRequest) {
		String encodedPassword = passwordEncoder.encode(loginRequest.getPwd());
		Member member = memberRepository.selectMemberById(loginRequest.getId());
		if (member != null && member.getId().equals(loginRequest.getId()) && passwordEncoder.matches(member.getPwd(),encodedPassword)) {
			return new MemberResponse(member);
		}
		throw new IllegalArgumentException();
	}

	@Override
	@Transactional(readOnly = true)
	public LoginInfo login_jwt(LoginRequest loginRequest) {
		String encodedPassword = passwordEncoder.encode(loginRequest.getPwd());

		Member member = memberRepository.selectMemberById(loginRequest.getId());
		LoginInfo loginInfo = new LoginInfo();
		System.out.println(member.getId()+", "+loginRequest.getId());
		System.out.println(member.getPwd()+", "+encodedPassword +", "+passwordEncoder.matches(loginRequest.getPwd(),member.getPwd()));
		if (member != null && member.getId().equals(loginRequest.getId()) && passwordEncoder.matches(loginRequest.getPwd(),member.getPwd())) {
			List<String> roleList = Arrays.asList(member.getMemberType().name());
			loginInfo.setResult("success");
			loginInfo.setToken(jwtTokenProvider.createToken(member.getId(), roleList));
		}
		else
		{
			loginInfo.setResult("fail");
		}
		return loginInfo;
	}

	@Override
	@Transactional
	public MemberResponse join(JoinRequest joinRequest) {
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
		MemberResponse memberResponse = new MemberResponse(member);

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
	public MemberResponse findMember(Long number) {
		Member member = memberRepository.selectMember(number);
		return new MemberResponse(member);
	}

	@Override
	@Transactional(readOnly = true)
	public MemberResponse findMemberById(String id) {
		Member member = memberRepository.selectMemberById(id);
		return new MemberResponse(member);
	}

	@Override
	@Transactional(readOnly = true)
	public LoginMemberResponse findMemberById_jwt(String id) {
		Member member = memberRepository.selectMemberById(id);
		return new LoginMemberResponse(member);
	}

	@Override
	public MemberResponse modifyByMemberNumber(Long memberNum, UpdateMemberRequest request) {
		Member findMember = memberRepository.selectMember(memberNum);
		if (!passwordEncoder.matches(request.getPwd(),findMember.getPwd()))
			findMember.setPwd(passwordEncoder.encode(request.getPwd()));
		findMember.setEmail(request.getEmail());
		findMember.setName(request.getName());
		findMember.setNickname(request.getNickname());
		findMember.setBirth(request.getBirth());
		findMember.setAddress(request.getAddress());
		findMember.setPhone(request.getPhone());
		return new MemberResponse(findMember);
	}

	@Override
	public MemberResponse modifyByMemberId(String memberId, UpdateMemberRequest request) {
		Member findMember = memberRepository.selectMemberById(memberId);
		if (!passwordEncoder.matches(request.getPwd(),findMember.getPwd()))
			findMember.setPwd(passwordEncoder.encode(request.getPwd()));
		findMember.setEmail(request.getEmail());
		findMember.setName(request.getName());
		findMember.setNickname(request.getNickname());
		findMember.setBirth(request.getBirth());
		findMember.setAddress(request.getAddress());
		findMember.setPhone(request.getPhone());
		return new MemberResponse(findMember);
	}

	@Override
	public boolean withdraw(String id) {
		int withDrawCnt = memberRepository.chkWithDraw(id);
		if (withDrawCnt > 0) {
			return false;
		}
		memberRepository.updateWithdraw(id);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public String checkMemberAuthority(String memberId, String teamName) {
		return memberRepository.selectMemberAuthority(memberId, teamName);
	}

}
