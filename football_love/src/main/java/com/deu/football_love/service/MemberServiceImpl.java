package com.deu.football_love.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deu.football_love.domain.Member;
import com.deu.football_love.dto.MemberDto;
import com.deu.football_love.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;

	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public MemberDto login(String id, String password) {
		String encodedPassword = passwordEncoder.encode(password);
		Member member = memberRepository.selectMember(id);
		if (member != null && member.getId().equals(id) && member.getPwd().equals(encodedPassword)) {
			return new MemberDto(member);
		}
		throw new IllegalArgumentException();
	}

	@Override
	@Transactional(readOnly = false)
	public MemberDto join(MemberDto memberDto) {
		String password = memberDto.getPwd();
		String encodedPassword = passwordEncoder.encode(password);
		memberDto.setPwd(encodedPassword);

		Member member = new Member();
		member.setAddress(memberDto.getAddress());
		member.setBirth(memberDto.getBirth());
		member.setEmail(memberDto.getEmail());
		member.setId(memberDto.getId());
		member.setPwd(memberDto.getPwd());
		member.setNickname(memberDto.getNickname());
		member.setName(memberDto.getName());
		member.setPhone(memberDto.getPhone());

		memberRepository.insertMember(member);
		return memberDto;
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
	public MemberDto findMember(String id) {
		Member member = memberRepository.selectMember(id);
		return new MemberDto(member);
	}

	@Override
	@Transactional(readOnly = false)
	public MemberDto modify(MemberDto memberDto) {
		Member updatedMember = memberRepository.updateMember(memberDto);
		return new MemberDto(updatedMember);
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
	public String checkMemberAuthority(String memberId, String teamId) {
		return memberRepository.selectMemberAuthority(memberId, teamId);
	}

}
