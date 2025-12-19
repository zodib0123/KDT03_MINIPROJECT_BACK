package com.ruby.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ruby.service.FacilityService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
public class FacilityController {
	private final FacilityService fserv;
	
	//통합검색 메서드
	@GetMapping("/facility")
	public ResponseEntity<?> getFacility(String name, String city, String gugun, String type, 
			@RequestParam(defaultValue = "0") Integer pageNo){
		return ResponseEntity.ok(fserv.getFacility(name, city, gugun, type, pageNo));
	}
	
	//count 메서드
	@GetMapping("/count")
	public ResponseEntity<?> getCount(String city, String gugun, String type){
		return ResponseEntity.ok(fserv.countFacility(city, gugun, type));
	}
}
