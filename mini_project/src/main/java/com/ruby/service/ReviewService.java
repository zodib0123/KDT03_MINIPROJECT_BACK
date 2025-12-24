package com.ruby.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ruby.domain.Facility;
import com.ruby.domain.Review;
import com.ruby.domain.dto.ReviewRequestDTO;
import com.ruby.domain.dto.ReviewResponseDTO;
import com.ruby.persistence.FacilityRepo;
import com.ruby.persistence.MemberRepo;
import com.ruby.persistence.ReviewRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepo rrepo;
	private final MemberRepo mrepo;
	private final FacilityRepo frepo;
	
//	public Map<String, Object> getReview(Integer fid){
	public List<ReviewResponseDTO> getReview(Integer fid){
		/*
		Map<String,Object> ret = new HashMap<>();
		ret.put("facility_id", fid);
		Pageable paging= PageRequest.of(0,100); 
		ret.put("review", rrepo.findByFacility_Fid(fid, paging));
		return ret;
		*/
		List<ReviewResponseDTO> ret = new LinkedList<>();
		List<Review> list = rrepo.findByFacility_Fid(fid);
		for(Review r : list) {
			ReviewResponseDTO dto = new ReviewResponseDTO(r.getSeq(),
										  r.getCont(),
										  r.getFacility().getFid(), 
										  r.getMember().getMid(), 
										  r.getStar()
									);
			
			ret.add(dto);
		}
		return ret;
	}
	
	public void addReview(ReviewRequestDTO dto) {
		Facility f = frepo.findById(dto.fid).get();
		
		Review review = Review.builder()
							  .cont(dto.cont)
							  .facility(f)
							  .member(mrepo.findById(dto.mid).get())
							  .star(dto.star)
							  .build();
		rrepo.save(review);
		
		//update facility.star
		/*
		Double currentStar = f.getStar()*f.getReviewCount(); 
		f.setReviewCount(f.getReviewCount()+1);
		f.setStar( (currentStar + dto.star) / f.getReviewCount());
		frepo.save(f);*/
	}
	
	public void deleteReview(ReviewRequestDTO dto) {
		//delete review
		rrepo.deleteById(dto.seq);

		//update facility.star
		/*
		Facility f = frepo.findById(dto.fid).get();
		Double currentStar = f.getStar()*f.getReviewCount(); 
		f.setReviewCount(f.getReviewCount()-1);
		f.setStar( (currentStar - dto.star) / f.getReviewCount());
		frepo.save(f);*/
		
	}
}
