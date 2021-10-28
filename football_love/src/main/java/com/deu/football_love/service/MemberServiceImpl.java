package com.deu.football_love.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deu.football_love.domain.Member;
import com.deu.football_love.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;

	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public Member login(String id, String password) {
		String encodedPassword = passwordEncoder.encode(password);
		Member member = memberRepository.selectMember(id);
		if (member != null && member.getId().equals(id) && member.getPwd().equals(encodedPassword)) {
			return member;
		}
		throw new IllegalArgumentException();
	}

	@Override
	@Transactional(readOnly = false)
	public Member join(Member member) {
		String password = member.getPwd();
		String encodedPassword = passwordEncoder.encode(password);
		member.setPwd(encodedPassword);
		return memberRepository.insertMember(member);
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
	public Member findMember(String id) {
		return memberRepository.selectMember(id);
	}

	@Override
	public Member modify(Member member) {
		return memberRepository.updateMember(member);
	}

	@Override
	public boolean withdraw(String id) {
		return memberRepository.updateWithdraw(id);
	}

	@Override
	public String checkMemberAuthority(String id) {
		return memberRepository.selectMemberAuthority(id);
	}

}
