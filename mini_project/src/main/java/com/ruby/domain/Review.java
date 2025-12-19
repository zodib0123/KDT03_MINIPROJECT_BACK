package com.ruby.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
	@Id
	private Integer seq;
	private String cont;
	//@ManyToOne
	//@JoinColumn(name="fId", nullable = false)
	//private Integer fId;
	//@ManyToOne
	//@JoinColumn(name="mId", nullable = false)
	//private String mId;
}
