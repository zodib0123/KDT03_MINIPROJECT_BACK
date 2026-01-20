package com.ruby.config.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruby.domain.Member;
import com.ruby.exception.ResourceNotFoundException;
import com.ruby.util.CustomUser;
import com.ruby.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private AuthenticationManager authManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authManager) {
        this.authManager=authManager;
        setFilterProcessesUrl("/api/login"); 
    }
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		Member member = null;
		
		try {
			member = mapper.readValue(request.getInputStream(), Member.class);
		} catch(IOException e) {
			throw new ResourceNotFoundException("Login Failed");
		}
		
		if(member == null)
			throw new ResourceNotFoundException("Login Failed");
		
		Authentication token = new UsernamePasswordAuthenticationToken(member.getMid(), member.getPwd());
		
		return authManager.authenticate(token);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		CustomUser user = (CustomUser)authResult.getPrincipal();
		String rawtoken = JWTUtil.getJWT(user.getUsername());
		/*
		if(token.startsWith("Bearer"))
			token = token.split(" ")[1];
		*/
		String alias = user.getAlias();
		String mid = user.getUsername();
		/*
		ResponseCookie jwtCookie = ResponseCookie.from("jwt_token", token)
								.httpOnly(true)
								.secure(false) //set to true in production?
								.path("/")
								.maxAge(12*3600)
								.sameSite("Lax")
								.build();
		
		ResponseCookie midCookie = ResponseCookie.from("mid", mid)
				.httpOnly(false)
				.path("/")
				.maxAge(12*3600)
				.build();
		
		ResponseCookie aliasCookie = ResponseCookie.from("alias", alias)
				.httpOnly(false)
				.path("/")
				.maxAge(12*3600)
				.build();
		
		response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
		response.addHeader(HttpHeaders.SET_COOKIE, midCookie.toString());
		response.addHeader(HttpHeaders.SET_COOKIE, aliasCookie.toString());
		*/
		
		//response config
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpStatus.OK.value());
		
		response.addHeader(HttpHeaders.AUTHORIZATION, rawtoken);
		
		Map<String, String> responseData = new HashMap<>();
		responseData.put("jwt", rawtoken);
		responseData.put("mid", mid);
	    responseData.put("alias", alias);

	    // 3. Write to the body using ObjectMapper
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.writeValue(response.getWriter(), responseData);
		
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());		
	}
}
