package com.deu.football_love.dto;

import com.deu.football_love.domain.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class LoginMemberResponse implements UserDetails {
    private Long number;
    private String id;
    private String name;
    private String password;
    private Collection<GrantedAuthority> roles;

    public LoginMemberResponse()
    {
        this.number = -1L;
        this.name = "anonymous";
    }
    public LoginMemberResponse(Member member)
    {
        this.number = member.getNumber();
        this.id = member.getId();
        this.name = member.getName();
        this.password = member.getPwd();
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority(member.getMemberType().name()));
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
