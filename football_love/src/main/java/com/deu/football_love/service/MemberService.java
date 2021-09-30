package com.deu.football_love.service;

import com.deu.football_love.domain.Member;
import com.deu.football_love.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public String join(Member member)
    {
        memberRepository.save(member);
        return member.getId();
    }
}
