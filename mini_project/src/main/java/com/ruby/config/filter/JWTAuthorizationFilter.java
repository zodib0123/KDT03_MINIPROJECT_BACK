package com.ruby.config.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ruby.domain.Member;
import com.ruby.persistence.MemberRepo;
import com.ruby.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private final MemberRepo mrepo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// get jwt from header
		String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
		//if not, get jwt from cookies
		if(jwt == null)
			jwt = getJwtFromCookies(request);
		
		// if no valid jwt, pass filter
		if (jwt == null || !jwt.startsWith(JWTUtil.prefix)) {
			filterChain.doFilter(request, response);
			return;
		}
		// extract username from token
		String username = JWTUtil.getClaim(jwt, JWTUtil.usernameClaim);

		Optional<Member> opt = mrepo.findById(username);
		if (opt.isEmpty()) {
			filterChain.doFilter(request, response);
			return;
		}
		Member member = opt.get();
		System.out.println("JWTAuthorizationFilter: " + member.getMid());

		// have authority defined);
		// when authority list is null, STS process it as unauthorized. So in case
		// authority is not defined, just put an empty list.
		Authentication auth = new UsernamePasswordAuthenticationToken(member.getMid(), null, new ArrayList<>());

		SecurityContextHolder.getContext().setAuthentication(auth);
		filterChain.doFilter(request, response);
	}
	
	//=====================================================
	//==========Read JWT from Cookie=======================
	//=====================================================	
	private String getJwtFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies !=null) {
			for (Cookie cookie : cookies) {
				if("jwt_token".equals(cookie.getName()))
					return cookie.getValue();
			}
		}
		return null;
	}
}
