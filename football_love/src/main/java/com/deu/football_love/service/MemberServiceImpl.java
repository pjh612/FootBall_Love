package com.deu.football_love.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deu.football_love.domain.Member;
import com.deu.football_love.dto.JoinRequest;
import com.deu.football_love.dto.LoginRequest;
import com.deu.football_love.dto.MemberResponse;
import com.deu.football_love.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;

	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public MemberResponse login(LoginRequest loginRequest) {
		String encodedPassword = passwordEncoder.encode(loginRequest.getPwd());
		Member member = memberRepository.selectMemberById(loginRequest.getId());
		if (member != null && member.getId().equals(loginRequest.getId()) && member.getPwd().equals(encodedPassword)) {
			return new MemberResponse(member);
		}
		throw new IllegalArgumentException();
	}

	@Override
	@Transactional(readOnly = false)
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

		memberRepository.insertMember(member);
		MemberResponse memberResponse = new MemberResponse(member);
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
	@Transactional(readOnly = false)
	public MemberResponse modify(JoinRequest joinRequest) {
		Member updatedMember = memberRepository.updateMember(joinRequest);
		return new MemberResponse(updatedMember);
	}

	@Override
	@Transactional(readOnly = false)
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
