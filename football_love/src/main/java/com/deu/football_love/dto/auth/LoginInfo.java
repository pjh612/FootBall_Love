package com.deu.football_love.dto.auth;

import com.deu.football_love.domain.Member;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@ToString
public class LoginInfo implements UserDetails {
    private Long number;
    private String id;
    private String name;
    private String password;
    private Collection<GrantedAuthority> roles;

    public LoginInfo() {
        this.number = -1L;
        this.name = "anonymous";
        this.password = "secret";
    }

    public LoginInfo(Member member) {
        this.number = member.getNumber();
        this.id = member.getId();
        this.name = member.getName();
        //this.password = member.getPwd();
        roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority(member.getMemberType().name()));
    }

    public boolean isLoggedIn() {
        return (number != -1 && name != "anonymous");
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
