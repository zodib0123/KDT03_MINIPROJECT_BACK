package com.ruby.domain.dto.response;

import com.ruby.domain.dto.ReviewDTO;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ReviewResponseDTO extends ReviewDTO{
	String alias;
	public ReviewResponseDTO(Integer seq, String cont, Integer fid, String mid, Integer star, String alias) {
		super(seq, cont, fid, mid, star);
		this.alias = alias;
	}
	
}
