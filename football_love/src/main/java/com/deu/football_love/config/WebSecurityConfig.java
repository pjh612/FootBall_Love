package com.deu.football_love.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtTokenProvider jwtTokenProvider;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.addAllowedOrigin("http://115.85.183.128:3000");
		configuration.addAllowedOrigin("http://127.0.0.1:3000");
		configuration.addAllowedOrigin("http://localhost:3000");
		configuration.addAllowedOrigin("https://127.0.0.1:3000");
		configuration.addAllowedOrigin("https://localhost:3000");
		configuration.addAllowedOrigin("https://dev.example.com:3000");
		configuration.addAllowedOrigin("https://localhost:3000");
		configuration.addAllowedOrigin("https://flove.fbl.p-e.kr");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.
				httpBasic()
					.disable()
					.cors().configurationSource(corsConfigurationSource())
				.and()
					.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and()
					.authorizeRequests()
					.antMatchers("/**", "/","/member","/member/login_jwt/**","/team/**").permitAll()
					.anyRequest().authenticated()
				.and()
					.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
	}
}
