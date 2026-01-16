package com.ruby.domain.dto.request;

import com.ruby.domain.dto.ReviewDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Schema(description = "리뷰 등록 요청 DTO")
public class ReviewRequestDTO extends ReviewDTO{
	
	public ReviewRequestDTO(String cont, Integer fid, String mid, Integer star) {
		super(cont, fid, mid, star);
	}
}
