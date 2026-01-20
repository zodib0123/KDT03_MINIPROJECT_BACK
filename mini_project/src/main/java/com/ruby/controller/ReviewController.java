package com.ruby.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ruby.domain.dto.request.ReviewRequestDTO;
import com.ruby.domain.dto.response.ReviewResponseDTO;
import com.ruby.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReviewController {
	
	private final ReviewService rserv;
	
	@GetMapping("/review")
	@Operation(summary = "시설ID에 해당하는 리뷰들을 조회합니다.")
	@Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Bearer 인증토큰")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "조회 성공",
					content = {@Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ReviewResponseDTO.class)
							)}
					),
			@ApiResponse(responseCode = "401", description = "인가 없음", 
			content = {@Content})
	})
	public ResponseEntity<?> getReview(@Parameter(description = "시설ID", required = true) 
	Integer fid){
		return ResponseEntity.ok(rserv.getReview(fid));
	}
	
	@PostMapping("/review")
	@Operation(summary = "작성한 리뷰를 등록합니다.")
	@Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Bearer 인증토큰")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "등록 성공",
				content = {@Content}),
		@ApiResponse(responseCode = "401", description = "인가 없음", 
		content = {@Content})
	})
	public ResponseEntity<?> addReview(@RequestBody 
			@io.swagger.v3.oas.annotations.parameters.RequestBody
			(description = "리뷰 등록시 요청 데이터",
			required = true,
			content = @Content(
					mediaType = "/review",
					schema = @Schema(implementation = ReviewRequestDTO.class)
					)
			)
			ReviewRequestDTO dto) {
		return ResponseEntity.ok(rserv.addReview(dto));
	}
	
	@DeleteMapping("/review/{seq}")
	@Operation(summary = "리뷰 시퀀스에 해당하는 리뷰를 삭제합니다.")
	@Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Bearer 인증토큰")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "삭제 성공",
				content = {@Content}),
		@ApiResponse(responseCode = "401", description = "인가 없음", 
		content = {@Content})
	})
	public ResponseEntity<?> deleteReview(@PathVariable
			@Parameter(description = "리뷰 시퀀스", required = true)
			Integer seq) {
		rserv.deleteReview(seq);
		return ResponseEntity.ok().build();
	}
}
