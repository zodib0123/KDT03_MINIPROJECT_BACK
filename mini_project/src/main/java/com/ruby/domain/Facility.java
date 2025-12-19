package com.ruby.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class Facility {
	@Id
	@Column(nullable = false)
	private Integer fid;
	private Double lon;
	private Double lat;
	private String name;
	private String type;
	private String zip;
	private String addr1;
	private String addr2;
	private String city;
	private String gugun;
	private String tel;
	private String date;
	
	@JsonProperty("fulladdr")
	public String getFullAddr() {
		return String.format("%s %s %s",
				this.addr1 != null ? this.addr1 : "",
				this.addr2 != null ? this.addr2 : "",
				this.zip != null ? this.zip : ""
				);
	}
}
