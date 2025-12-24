package com.ruby;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ruby.domain.Facility;
import com.ruby.domain.Member;
import com.ruby.domain.Review;
import com.ruby.persistence.FacilityRepo;
import com.ruby.persistence.MemberRepo;
import com.ruby.persistence.ReviewRepo;

@SpringBootTest
public class QueryMethodTest {
	@Autowired
	FacilityRepo frepo;
	@Autowired
	ReviewRepo rrepo;
	@Autowired
	MemberRepo mrepo;
	
	//@Test
	public void queryTest() {
		Pageable paging = PageRequest.of(0, 5);
		Page<Facility> list = frepo.findByNameContaining("배드민턴",paging);
		for(Facility f:list)
			System.out.println("--->"+f);
	}
	
	//@Test
	public void inputReview() {
		mrepo.save(Member.builder()
				.mid("1234")
				.pwd("1234")
				.alias("노리")
				.build());
		
		for(int i=1;i<=10;i++) {
			rrepo.save(Review.builder()
					.seq(i)
					.cont(i+"번째리뷰")
					.facility(frepo.findById(1).get())
					.member(mrepo.findById("1234").get())
					.star(i%5)
					.build());
		}
	}
}
