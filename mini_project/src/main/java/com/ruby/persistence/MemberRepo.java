package com.ruby.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruby.domain.Member;

public interface MemberRepo extends JpaRepository<Member, String>{

}
