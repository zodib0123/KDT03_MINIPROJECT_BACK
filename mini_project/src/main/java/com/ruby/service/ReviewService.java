package com.ruby.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ruby.domain.Facility;
import com.ruby.domain.Review;
import com.ruby.domain.dto.request.ReviewRequestDTO;
import com.ruby.domain.dto.response.ReviewResponseDTO;
import com.ruby.exception.ResourceNotFoundException;
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
	
	public List<ReviewResponseDTO> getReview(Integer fid){
		List<ReviewResponseDTO> ret = new LinkedList<>();
		List<Review> list = rrepo.findByFacility_Fid(fid);
		for(Review r : list) {
			ReviewResponseDTO dto = new ReviewResponseDTO(r.getSeq(),
										  r.getCont(),
										  r.getFacility().getFid(), 
										  r.getMember().getMid(), 
										  r.getStar(),
										  r.getMember().getAlias()
									);
			
			ret.add(dto);
		}
		return ret;
	}
	
	public String addReview(ReviewRequestDTO dto) {
		Facility f = frepo.findById(dto.fid).get();
		
		Review review = Review.builder()
							  .cont(dto.cont)
							  .facility(f)
							  .member(mrepo.findById(dto.mid).get())
							  .star(dto.star)
							  .build();
		rrepo.save(review);
		return "OK";
	}
	
	public void deleteReview(Integer seq) {
		//delete review
		rrepo.findById(seq)
				.orElseThrow(()-> new ResourceNotFoundException("Review not found."));
		rrepo.deleteById(seq);
	}
}
