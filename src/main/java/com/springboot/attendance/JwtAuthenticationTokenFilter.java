package com.springboot.attendance;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter{



   public JwtAuthenticationTokenFilter() {
		super("/entry/**");
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		
		String header= request.getHeader("Authorization");
	
		System.out.println(header);
		if(header == null || !header.startsWith("Token"))
		{
			throw new RuntimeException("JWT token is missing");
		}
		
		String authenticationToken = header.substring(6);
		
		JwtAuthenticationToken token = new JwtAuthenticationToken(authenticationToken);
		
		return getAuthenticationManager().authenticate(token);
	}
	
	
   @Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}
	

	
	 
}
