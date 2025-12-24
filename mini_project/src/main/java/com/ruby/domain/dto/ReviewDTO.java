package com.ruby.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
	public Integer seq;
	public String cont;
	public Integer fid;
	public String mid;
	public Integer star;	
}
