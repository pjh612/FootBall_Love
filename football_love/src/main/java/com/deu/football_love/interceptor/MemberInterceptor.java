package com.deu.football_love.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class MemberInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("memberInfo") != null) {
			return true;
		}
		//잘못된 접근에 대한 분기는 정하지 못함
		//밑에는 수정해야할 부분
		response.sendRedirect(request.getContextPath() + "/login"); 
		return false;
	}
	
	

}
