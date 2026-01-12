package com.ruby.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ruby.domain.Facility;

public interface FacilityRepo extends JpaRepository<Facility, Integer>{
	//return Lists of facilities
	Page<Facility> findByNameContaining(String name, Pageable paging);
	Page<Facility> findByNameContainingAndCityContaining(String name,String city, Pageable paging);
	Page<Facility> findByNameContainingAndCityContainingAndGugunContaining(String name,String city, String gugun, Pageable paging);
	Page<Facility> findByNameContainingAndCityContainingAndTypeContaining(String name,String city, String type, Pageable paging); 
	Page<Facility> findByNameContainingAndCityContainingAndGugunContainingAndTypeContaining(String name,String city, String gugun, String type, Pageable paging);
	//noname
	Page<Facility> findByCityContaining(String city, Pageable paging);
	Page<Facility> findByCityContainingAndGugunContaining(String city, String gugun, Pageable paging);
	Page<Facility> findByCityContainingAndTypeContaining(String city, String type, Pageable paging); 
	Page<Facility> findByCityContainingAndGugunContainingAndTypeContaining(String city, String gugun, String type, Pageable paging);
	Page<Facility> findByTypeContaining(String type, Pageable paging);
	
	
	//return facility counts with input
	Integer countByCityLike(String city);
	Integer countByCityLikeAndGugunLike(String city, String gugun);
	Integer countByCityLikeAndGugunLikeAndTypeLike(String city, String gugun, String type);
	Integer countByCityLikeAndTypeLike(String city, String type);
	
	//return facility counts without input
	@Query("SELECT COUNT(f) FROM Facility f")
	Integer countAll(); 
	
	@Query("SELECT f.city, COUNT(f) FROM Facility f GROUP BY f.city ORDER BY f.city")
	List<Object[]> countByCity(); 
	/*
	@Query("SELECT f.city, f.gugun, count(f) FROM Facility f GROUP BY f.city, f.gugun ORDER BY f.city")
	List<Object[]> countByGugun(); 
	*/
	//given city, return counts within cities gugun/types
	@Query("SELECT f.gugun, count(f) FROM Facility f GROUP BY f.city, f.gugun HAVING f.city=?1 ORDER BY f.city")
	List<Object[]> countByGugun(String city); 
	
	@Query("SELECT f.type, count(f) FROM Facility f GROUP BY f.city, f.type HAVING f.city=?1 ORDER BY f.city")
	List<Object[]> countByType(String city);

	@Query("SELECT f.type, count(f) FROM Facility f GROUP BY f.city, f.gugun, f.type HAVING f.city=?1 AND f.gugun=?2 ORDER BY f.gugun")
	List<Object[]> countByTypeinGugun(String city, String gugun);
	
	//노후도 count
	/*
	String tenYearsAgo = LocalDate.now().minusYears(10).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	String twentyFiveYearsAgo = LocalDate.now().minusYears(25).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	
	@Query("SELECT count(f) FROM facility f WHERE f.create_date >= :tenYearsAgo AND f.city = ?1")
	List<Object[]> countNewInCity(String city, @Param("tenYearsAgo") String tenYearsAgo);
	@Query("SELECT count(f) FROM facility f WHERE f.create_date <= :tenYearsAgo AND f.create_date >= :twntyFiveYearsAgo AND f.city = ?1")
	List<Object[]> countMidInCity(String city, @Param("tenYearsAgo") String tenYearsAgo, @Param("twentyFiveYearsAgo") String twentyFiveYearsAgo);	
	@Query("SELECT count(f) FROM facility f WHERE f.create_date <= :twentyFiveYearsAgo AND f.city = ?1")
	List<Object[]> countOldInCity(String city, @Param("twentyFiveYearsAgo") String twentyFiveYearsAgo);
	*/
	
	@Query(value = "SELECT count(*) FROM facility WHERE create_date >= DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 10 YEAR), '%Y%m%d') AND city=?1", 
		       nativeQuery = true)
	Integer countNewInCity(String city);
	@Query(value = "SELECT count(*) FROM facility WHERE create_date > DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 25 YEAR), '%Y%m%d') AND create_date < DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 10 YEAR), '%Y%m%d') AND city=?1", 
			nativeQuery = true)
	Integer countMidInCity(String city);
	@Query(value = "SELECT count(*) FROM facility WHERE create_date <= DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 25 YEAR), '%Y%m%d') AND city=?1", 
			nativeQuery = true)
	Integer countOldInCity(String city);
	@Query(value = "SELECT count(*) FROM facility WHERE erdsgn='Y' AND city=?1", 
			nativeQuery = true)
	Integer countERDInCity(String city);
	
	//misc query
	@Query(value = "SELECT count(*) FROM (SELECT DISTINCT gugun FROM facility WHERE city=?1) as count_guguns", 
			nativeQuery = true)
	Integer countGugunsInCity(String city);
	@Query(value = "select round(avg((curdate()-create_date))/10000, 1) from facility where city like ?1", 
			nativeQuery = true)
	Double avgOldInCity(String city);
	
}
