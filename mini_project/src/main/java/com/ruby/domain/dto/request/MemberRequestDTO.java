package com.ruby.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDTO {
	//@NotBlank(message = "ID is required")
	@Size(min=6, max=12, message="ID는 6~12자리여야합니다")
	@Pattern(regexp = "^[a-z0-9]*$",
	message="ID는 소문자와 숫자조합만 가능합니다")
	@Schema
	private String mid;
	
	@Size(min=8, max=18, message="비밀번호는 8~18자리여야합니다")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
				message="비밀번호는 대문자, 소문자, 숫자, 특수기호가 반드시 하나씩 조합돼야합니다.")
	@Schema
	private String pwd;
	
	@Size(min=2, max=8, message="별명은 2~8자리여야합니다")
	@Pattern(regexp = "^[\\p{L}0-9]+$",
	message="별명은 문자와 숫자조합만 가능합니다")
	@Schema
	private String alias;
}
