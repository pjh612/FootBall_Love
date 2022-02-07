package com.deu.football_love.config;

import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new LoginInfo(memberRepository.findById(username).orElseThrow(()-> new IllegalArgumentException("no such member data")));
    }
}
