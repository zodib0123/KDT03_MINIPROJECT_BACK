package com.ruby.domain.dto.response;

import com.ruby.domain.dto.ReviewDTO;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ReviewResponseDTO extends ReviewDTO{
	Integer seq;
	String alias;
	public ReviewResponseDTO(Integer seq, String cont, Integer fid, String mid, Integer star, String alias) {
		super(cont, fid, mid, star);
		this.alias = alias;
		this.seq = seq;
	}
	
}
