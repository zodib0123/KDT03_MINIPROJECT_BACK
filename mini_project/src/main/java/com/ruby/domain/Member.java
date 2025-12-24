package com.ruby.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class Member {
	@Id
	@NotBlank(message = "ID is required")
	private String mid;
	@NotBlank(message = "Password is required")
	private String pwd;
	@NotBlank(message = "Alias is required")
	private String alias;
	//private Role role;
}
