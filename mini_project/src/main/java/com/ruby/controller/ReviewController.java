package com.ruby.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ruby.domain.dto.ReviewRequestDTO;
import com.ruby.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReviewController {
	
	private final ReviewService rserv;
	
	@GetMapping("/review/get")
	public ResponseEntity<?> getReview(Integer fid){
		return ResponseEntity.ok(rserv.getReview(fid));
	}
	
	@PostMapping("/review/add")
	public void addReview(@RequestBody ReviewRequestDTO dto) {
		rserv.addReview(dto);
	}
	
	@PostMapping("/review/del")
	public void deleteReview(@RequestBody ReviewRequestDTO dto) {
		rserv.deleteReview(dto);
	}
}
