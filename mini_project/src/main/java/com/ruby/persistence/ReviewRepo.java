package com.ruby.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruby.domain.Review;

public interface ReviewRepo extends JpaRepository<Review, Integer>{

}
