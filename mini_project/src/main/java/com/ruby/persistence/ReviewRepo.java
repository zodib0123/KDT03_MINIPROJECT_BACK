package com.ruby.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruby.domain.Review;

public interface ReviewRepo extends JpaRepository<Review, Integer>{
	//Page<Review> findByFacility_Fid(Integer fid, Pageable paging);
	List<Review> findByFacility_Fid(Integer fid);
}
