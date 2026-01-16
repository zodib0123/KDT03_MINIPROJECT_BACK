package com.ruby.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ruby.domain.Member;
import com.ruby.domain.dto.request.MemberRequestDTO;
import com.ruby.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
public class MemberController {
	private final MemberService mserv;
	
	@PostMapping("/auth/signup")
	@Operation(summary = "회원가입")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "가입 성공"),
		@ApiResponse(responseCode = "400", description = "가입 실패"),
	})
	public ResponseEntity<?> signup(@Valid @RequestBody 
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "회원가입시 요청 데이터",
			required = true,
			content = @Content(
					schema = @Schema(implementation = MemberRequestDTO.class)
					)
			)
			MemberRequestDTO dto) {
			String msg = mserv.signup(dto);	
		return ResponseEntity.ok(msg);
	}
}
