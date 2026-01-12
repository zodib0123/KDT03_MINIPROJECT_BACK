package com.ruby.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ruby.domain.dto.request.ReviewRequestDTO;
import com.ruby.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReviewController {
	
	private final ReviewService rserv;
	
	@GetMapping("/review")
	public ResponseEntity<?> getReview(Integer fid){
		return ResponseEntity.ok(rserv.getReview(fid));
	}
	
	@PostMapping("/review")
	public ResponseEntity<?> addReview(@RequestBody ReviewRequestDTO dto) {
		return ResponseEntity.ok(rserv.addReview(dto));
	}
	
	@DeleteMapping("/review/{seq}")
	public ResponseEntity<?> deleteReview(@PathVariable Integer seq, Authentication auth) {
		String mid = auth.getName();
		rserv.deleteReview(seq, mid);
		return ResponseEntity.ok().build();
	}
}
