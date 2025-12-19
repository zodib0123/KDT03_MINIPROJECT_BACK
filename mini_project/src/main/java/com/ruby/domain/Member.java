package com.ruby.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Member {
	@Id
	@Column(nullable = false)
	private String mId;
	@Column(nullable = false)
	private String pwd;
	@Column(nullable = false)
	private String alias;
	private Role role;
}
