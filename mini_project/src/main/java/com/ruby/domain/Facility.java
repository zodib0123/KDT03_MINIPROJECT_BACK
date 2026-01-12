package com.ruby.domain;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	private Integer fid;
	private Double lon;
	private Double lat;
	private String name;
	private String type;
	@JsonIgnore
	private String addr1;
	@JsonIgnore
	private String addr2;
	private String city;
	private String gugun;
	private String tel;
	private String createDate;
	private Character erdsgn;
	
	// This is a virtual field. Hibernate runs this subquery automatically.
    // Note: Use the actual table/column names from your DB here.
	// coalesce occurs when r.star is null
    @Formula("(SELECT COALESCE(AVG(r.star), 0) FROM review r WHERE r.fid = fid)")
    private Double star;

    @JsonProperty("star")
    public Double getStar() {
        return (Math.round(star*10)/10.0);
    }
	
	@JsonProperty("fulladdr")
	public String getFullAddr() {
		return String.format("%s %s",
				this.addr1 != null ? this.addr1 : "",
				this.addr2 != null ? this.addr2 : ""
				);
	}
}
