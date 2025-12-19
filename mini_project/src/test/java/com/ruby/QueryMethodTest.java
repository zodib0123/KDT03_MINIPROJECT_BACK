package com.ruby;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ruby.domain.Facility;
import com.ruby.persistence.FacilityRepo;

@SpringBootTest
public class QueryMethodTest {
	@Autowired
	FacilityRepo frepo;
	
	@Test
	public void queryTest() {
		Pageable paging = PageRequest.of(0, 5);
		Page<Facility> list = frepo.findByNameContaining("배드민턴",paging);
		for(Facility f:list)
			System.out.println("--->"+f);
	}
}
