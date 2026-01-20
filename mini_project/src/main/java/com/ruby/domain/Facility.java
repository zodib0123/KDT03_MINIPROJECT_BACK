package com.ruby.domain;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Facility {
	@Id
	@Column(nullable = false)
	@Schema(description = "시설 시퀀스", example = "123")
	private Integer fid;
	@Schema(description = "경도", example = "129.09")
	private Double lon;
	@Schema(description = "위도", example = "35.24")
	private Double lat;
	@Schema(description = "시설명", example = "경암체육관")
	private String name;
	@Schema(description = "시설유형", example = "생활체육관")
	private String type;
	@JsonIgnore
	private String addr1;
	@JsonIgnore
	private String addr2;
	@Schema(description = "광역시/도", example = "부산광역시")
	private String city;
	@Schema(description = "시/군/구", example = "금정구")
	private String gugun;
	@Schema(description = "전화번호", example = "051-510-1076")
	private String tel;
	@Schema(description = "완공일자", example = "20090210")
	private String createDate;
	@Schema(description = "내진설계여부", example = "Y")
	private Character erdsgn;
	
	// This is a virtual field. Hibernate runs this subquery automatically.
    // Note: Use the actual table/column names from your DB here.
	// coalesce occurs when r.star is null
    @Formula("(SELECT COALESCE(AVG(r.star), 0) FROM review r WHERE r.fid = fid)")
    private Double star;

    @JsonProperty("star")
    @Schema(description = "평점", example = "5.0")
    public Double getStar() {
        return (Math.round(star*10)/10.0);
    }
	
	@JsonProperty("fulladdr")
	@Schema(description = "주소", example = "부산광역시 금정구 부산대학로63번길 2")
	public String getFullAddr() {
		return String.format("%s %s",
				this.addr1 != null ? this.addr1 : "",
				this.addr2 != null ? this.addr2 : ""
				);
	}
}
