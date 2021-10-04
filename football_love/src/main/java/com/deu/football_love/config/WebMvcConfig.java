package com.deu.football_love.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.deu.football_love.interceptor.MemberInterceptor;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final MemberInterceptor memberInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(memberInterceptor); //경로 추가 해야함
	}
	
	
	

}
