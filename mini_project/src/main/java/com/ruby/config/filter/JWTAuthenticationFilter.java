package com.ruby.config.filter;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruby.domain.Member;
import com.ruby.util.CustomUser;
import com.ruby.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private final AuthenticationManager authManager;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		Member member = null;
		
		try {
			member = mapper.readValue(request.getInputStream(), Member.class);
		} catch(IOException e) {
			return null;
		}
		
		if(member == null)
			return null;
		
		Authentication token = new UsernamePasswordAuthenticationToken(member.getMid(), member.getPwd());
		
		return authManager.authenticate(token);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
	    //response.addHeader("alias", URLEncoder.encode(alias, StandardCharsets.UTF_8));
		
		CustomUser user = (CustomUser)authResult.getPrincipal();
		String token = JWTUtil.getJWT(user.getUsername());
		if(token.startsWith("Bearer"))
			token = token.split(" ")[1];
		
		String alias = user.getAlias();
		/*
		HashMap<String, String> map = new HashMap<>();
		//map.put("tok", token);
		map.put("alias", alias);
		map.put("mid", user.getUsername());
		new ObjectMapper().writeValue(response.getOutputStream(), map);
		 */
		ResponseCookie jwtCookie = ResponseCookie.from("jwt_token", token)
								.httpOnly(true)
								.secure(false) //set to true in production?
								.path("/")
								.maxAge(12*3600)
								.sameSite("Lax")
								.build();
		
		ResponseCookie midCookie = ResponseCookie.from("mid", user.getUsername())
				.httpOnly(false)
				.path("/")
				.maxAge(12*3600)
				.build();
		
		ResponseCookie aliasCookie = ResponseCookie.from("alias", alias)
				.httpOnly(false)
				.path("/")
				.maxAge(12*3600)
				.build();
		
		response.addHeader(HttpHeaders.AUTHORIZATION, token);
		response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
		response.addHeader(HttpHeaders.SET_COOKIE, midCookie.toString());
		response.addHeader(HttpHeaders.SET_COOKIE, aliasCookie.toString());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		response.setStatus(HttpStatus.OK.value());
		
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());		
	}
}
