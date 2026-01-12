package com.ruby.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ruby.domain.Facility;
import com.ruby.persistence.FacilityRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FacilityService {
	private final FacilityRepo frepo;

	// ==================Search
	// ==================Search
	// ==================Search
	public Map<String, Object> getFacility(String name, String city, String gugun, String type, String sort, Integer pageNo) {
		Map<String, Object> ret = new LinkedHashMap<>();
			
		Pageable paging = sort.equals("star") 
				? PageRequest.of(pageNo, 20, Direction.DESC, "star")
				: sort.equals("new") 
				? PageRequest.of(pageNo, 20, Direction.DESC, "createDate")
				: PageRequest.of(pageNo, 20, Direction.ASC, "name");
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
			ret.put("facility", frepo.findByCityContaining("",paging));
		}

		// 카운터 받는 부분
		// if(ret.get("facility") instanceof List<?>)
		// ret.put("total_count", ret.get("facility").size);
		return ret;
	}
	
	// ==================count
	// ==================count
	// ==================count
	public Facility getFacilityById(Integer fid) {
		return frepo.findById(fid).get();
	}
	
	// ==================count
	// ==================count
	// ==================count
	public Map<String, Object> countFacility(String city, String gugun, String type) {
		Map<String, Object> ret = new LinkedHashMap<>();

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
		Map<String, Object> ret = new LinkedHashMap<>();
		
		ret.put("city", city);
		ret.put("old", frepo.countOldInCity(city));
		ret.put("mid", frepo.countMidInCity(city));
		ret.put("new", frepo.countNewInCity(city));
		
		return ret;
	}

	//====================내진설계 count
	//====================내진설계 count
	//====================내진설계 count
	public Map<String, Object> countERD(String city){
		Map<String, Object> ret = new LinkedHashMap<>();
		
		ret.put("city", city);
		ret.put("erdsgn", frepo.countERDInCity(city));
		ret.put("avg_old", frepo.avgOldInCity(city));
		ret.put("number_of_guguns", frepo.countGugunsInCity(city));
		ret.put("city_count_total", frepo.countByCityLike(city));
		return ret;
	}
	
	public Map<String, Object> oneHotCount(){
		Map<String, Object> ret = new LinkedHashMap<>();
		ret.put("count_all", frepo.countAll());
		
		/*
ㄴtype wise count(obj)
ㄴerd count
ㄴold count
		 * */
		
		//province wise count
		Map<Object, Object> cityCountList = new LinkedHashMap<>();
		//city wise count(obj)
		for(Object[] city : frepo.countByCity()) {
			Map<Object, Object> gugunCountList = new LinkedHashMap<>();
			gugunCountList.put("total", city[1]);
			gugunCountList.put("erdsgn", frepo.countERDInCity(String.valueOf( city[0])));
			gugunCountList.put("avg_old", frepo.avgOldInCity(String.valueOf( city[0])));
			gugunCountList.put("number_of_guguns", frepo.countGugunsInCity(String.valueOf( city[0])));
			gugunCountList.put("old", frepo.countOldInCity(String.valueOf( city[0])));
			gugunCountList.put("mid", frepo.countMidInCity(String.valueOf( city[0])));
			gugunCountList.put("new", frepo.countNewInCity(String.valueOf( city[0])));
			for(Object[] gugun : frepo.countByGugun(String.valueOf(city[0]))) {
				//type wise count
				Map<Object, Object> typeCountList = new LinkedHashMap<>();
				typeCountList.put("total", gugun[1]);
				for(Object[] type : frepo.countByTypeinGugun(String.valueOf(city[0]), String.valueOf(gugun[0]))) {
					typeCountList.put(type[0], type[1]);
				}
				gugunCountList.put(gugun[0], typeCountList);
			}
			cityCountList.put(city[0],gugunCountList);
		}
		
		ret.put("result", cityCountList);
		
		
		
		return ret;
	}
}
