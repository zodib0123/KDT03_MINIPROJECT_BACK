package com.ruby.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ruby.domain.Facility;
import com.ruby.service.FacilityService;
import com.ruby.util.SwaggerExampleObjects;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
public class FacilityController {
	private final FacilityService fserv;
	
	//통합검색 메서드
	@GetMapping("/facility")
	@Operation(summary = "시설 통합검색", description = "검색 조건에 맞는 시설 검색. 1페이지에 20개의 검색결과 출력.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "검색 성공", 
				content = @Content(mediaType = org.springframework.http.MediaType.APPLICATION_JSON_VALUE
				//schema = @Schema(implementation = Facility.class)
				)),
		//@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
	})
	public ResponseEntity<?> getFacility(
			@Parameter(description = "시설명")
			@RequestParam(required = false)
			String name, 
			@Parameter(description = "광역시/도", required = false) 
			@RequestParam(required = false)
			String city, 
			@Parameter(description = "시/군/구", required = false) 
			@RequestParam(required = false)
			String gugun, 
			@Parameter(description = "시설유형", required = false) 
			@RequestParam(required = false)
			String type, 
			@Parameter(description = "정렬조건 (name = 이름 오름차순, new=완공일자 내림차순, star=평점 내림차순)", required = false)
			@RequestParam(defaultValue = "name") String sort,
			@Parameter(description = "페이지 번호", required = false)
			@RequestParam(defaultValue = "0") Integer pageNo){
		return ResponseEntity.ok(fserv.getFacility(name, city, gugun, type, sort, pageNo));
	}
	
	@GetMapping("/facility/detail")
	@Operation(summary = "시설 시퀀스 검색", description = "해당 시퀀스의 시설 1개 검색.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "검색 성공", 
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Facility.class))),
		@ApiResponse(responseCode = "400", description = "Bad Request (존재하지 않는 시퀀스)", content = @Content),
	})
	public ResponseEntity<?> getFacility(
			@Parameter(description = "시설 시퀀스 번호", required = true) 
			@RequestParam(required = false)
			Integer fid){
		return ResponseEntity.ok(fserv.getFacilityById(fid));
	}
	
	//count 메서드
	@GetMapping("/count")
	@Operation(summary = "시설개수 조건검색", description = "광역시/도, 시/군/구, 시설유형 조건 만족하는 시설개수 검색")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "검색 성공", 
				content = @Content(mediaType = "application/json",
				examples = @ExampleObject(value = SwaggerExampleObjects.getCount)
				)),
	})
	public ResponseEntity<?> getCount(
			@Parameter(description = "광역시/도", required = false) 
			@RequestParam(required = false)
			String city, 
			@RequestParam(required = false)
			@Parameter(description = "시/군/구", required = false) 
			String gugun, 
			@RequestParam(required = false)
			@Parameter(description = "시설유형", required = false) 
			String type){
		return ResponseEntity.ok(fserv.countFacility(city, gugun, type));
	}
	
	@GetMapping("/count/old")
	@Operation(summary = "노후도로 구분한 시설개수", description = "해당 광역시/도 내 시설개수를 노후도로 구분하여 검색")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "검색 성공", 
				content = @Content(mediaType = "application/json",
				examples = @ExampleObject(value = SwaggerExampleObjects.getCountOld)
				)),
	})
	public ResponseEntity<?> getCountOld(
			@Parameter(description = "광역시/도", required = false) 			
			@RequestParam(required = false)
			String city){
		return ResponseEntity.ok(fserv.countOld(city));
	}
	
	@GetMapping("/count/erdsgn")
	@Operation(summary = "내진설계여부로 구분한 시설개수", description = "해당 광역시/도 내 시설개수를 내진설계여부로 구분하여 검색")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "검색 성공", 
				content = @Content(mediaType = "application/json",
				examples = @ExampleObject(value = SwaggerExampleObjects.getCountErd)
				)),
	})
	public ResponseEntity<?> getCountERD(
			@Parameter(description = "광역시/도", required = false) 
			@RequestParam(required = false)
			String city){
		return ResponseEntity.ok(fserv.countERD(city));
	}
	
	//@GetMapping("/onehotcount")
	public ResponseEntity<?> onehotcount(){
		return ResponseEntity.ok(fserv.oneHotCount());
	}
}
