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
		return memberRepository.selectMember(id, encodedPassword);
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
		if(cnt == 0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Member findMember(String id) {
		return memberRepository.selectMember(id,"123");
	}


}
