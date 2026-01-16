package com.ruby.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
	@Schema(description = "리뷰 내용", example = "샘플 리뷰입니다.")
	public String cont;
	@Schema(description = "시설ID", example = "1")
	public Integer fid;
	@Schema(description = "멤버ID", example = "qwer1234")
	public String mid;
	@Schema(description = "평점(1~5)", example = "3")
	public Integer star;
	
}
