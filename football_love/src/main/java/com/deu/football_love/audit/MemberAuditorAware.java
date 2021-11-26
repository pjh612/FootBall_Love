package com.deu.football_love.audit;

import com.deu.football_love.config.JwtTokenProvider;
import com.deu.football_love.dto.LoginMemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class MemberAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        if(authentication.getPrincipal().equals("anonymousUser")) {
            LoginMemberResponse loginInfo = new LoginMemberResponse();
            log.info("anonymous authority");
            return Optional.of(-1L);
        }
        LoginMemberResponse loginInfo =(LoginMemberResponse) authentication.getPrincipal();
        return Optional.of(loginInfo.getNumber());
    }
}
