package com.ruby.service;

import org.springframework.stereotype.Service;

import com.ruby.domain.Member;
import com.ruby.persistence.MemberRepo;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepo mrepo;
	
	public void signup(Member member) {
		mrepo.save(member);
	}
}
