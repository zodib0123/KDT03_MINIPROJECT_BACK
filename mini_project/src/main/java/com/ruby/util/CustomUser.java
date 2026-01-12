package com.ruby.util;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;

import com.ruby.domain.Member;

public class CustomUser extends User{

	private static final long serialVersionUID = -6161351492377755047L;
	
	private final String alias;
	
	public CustomUser(Member member) {
		super(member.getMid(), member.getPwd(), new ArrayList<>());
		this.alias = member.getAlias();
	}
	
	public String getAlias() {
		return alias;
	}
}
