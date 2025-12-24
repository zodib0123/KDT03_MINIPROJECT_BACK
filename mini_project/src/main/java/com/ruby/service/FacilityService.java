package com.ruby.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ruby.persistence.FacilityRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FacilityService {
	private final FacilityRepo frepo;

	// ==================Search
	// ==================Search
	// ==================Search
	public Map<String, Object> getFacility(String name, String city, String gugun, String type, Integer pageNo) {
		Map<String, Object> ret = new HashMap<>();

		Pageable paging = PageRequest.of(pageNo, 20, Direction.ASC, "name");
		if (name != null) {
			if (city != null) {
				if (gugun != null) {
					if (type != null) // city+gugun+type
						ret.put("facility",
								frepo.findByNameContainingAndCityContainingAndGugunContainingAndTypeContaining(name,
										city, gugun, type, paging));
					else // city+gugun
						ret.put("facility", frepo.findByNameContainingAndCityContainingAndGugunContaining(name, city,
								gugun, paging));
				} else if (type != null) {
					ret.put("facility",
							frepo.findByNameContainingAndCityContainingAndTypeContaining(name, city, type, paging));
				} else
					ret.put("facility", frepo.findByNameContainingAndCityContaining(name, city, paging));
			} else {
				ret.put("facility", frepo.findByNameContaining(name, paging));
			}
		}
		// 이름이 없으면 시도/시군구/타입 검색
		else if (city != null) {
			if (gugun != null) {
				if (type != null) // city+gugun+type
					ret.put("facility",
							frepo.findByCityContainingAndGugunContainingAndTypeContaining(city, gugun, type, paging));
				else // city+gugun
					ret.put("facility", frepo.findByCityContainingAndGugunContaining(city, gugun, paging));
			} else if (type != null) {
				ret.put("facility", frepo.findByCityContainingAndTypeContaining(city, type, paging));
			} else
				ret.put("facility", frepo.findByCityContaining(city, paging));
		}
		// 이름, 광역시/도 없으면 타입 검색
		else if (type != null) {
			ret.put("facility", frepo.findByTypeContaining(type, paging));
		}

		else {
			ret.put("facility", "시설명, 광역시/도, 시설종류 중 하나의 검색조건을 반드시 입력하세요.");
		}

		// 카운터 받는 부분
		// if(ret.get("facility") instanceof List<?>)
		// ret.put("total_count", ret.get("facility").size);
		return ret;
	}

	// ==================count
	// ==================count
	// ==================count
	public Map<String, Object> countFacility(String city, String gugun, String type) {
		Map<String, Object> ret = new HashMap<>();

		if (city != null) {
			ret.put("city", city);
			ret.put("city_count_total", frepo.countByCityLike(city));
			if (gugun != null) {
				ret.put("gugun", gugun);
				ret.put("gugun_count_total", frepo.countByCityLikeAndGugunLike(city, gugun));
				if (type != null) {// city+gugun+type
					ret.put("type", type);
					ret.put("type_count_total", frepo.countByCityLikeAndGugunLikeAndTypeLike(city, gugun, type));
				} else {
					ret.put("types_in_gugun_count", frepo.countByTypeinGugun(city, gugun));
				}
			} else {
				ret.put("guguns_in_city_count", frepo.countByGugun(city));
				ret.put("types_in_city_count", frepo.countByType(city));
			}
		} else {
			ret.put("count_all", frepo.countAll());
			ret.put("city_count_list", frepo.countByCity());
			// ret.put("gugun_count_list", fserv.countByGugun());
		}
		return ret;
	}
	
	//====================노후도 count
	//====================노후도 count
	//====================노후도 count
	public Map<String, Object> countOld(String city){
		Map<String, Object> ret = new HashMap<>();
		
		ret.put("city", city);
		ret.put("old", frepo.countOldInCity(city));
		ret.put("mid", frepo.countMidInCity(city));
		ret.put("new", frepo.countNewInCity(city));
		
		return ret;
	}
}
