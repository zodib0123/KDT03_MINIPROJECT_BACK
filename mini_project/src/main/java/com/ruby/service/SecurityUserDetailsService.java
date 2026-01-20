package com.ruby.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ruby.domain.Member;
import com.ruby.persistence.MemberRepo;
import com.ruby.util.CustomUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {
	private final MemberRepo mrepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = mrepo.findById(username).orElseThrow(()->new UsernameNotFoundException(username+" does not exist"));
		return new CustomUser(member);
	}
}
