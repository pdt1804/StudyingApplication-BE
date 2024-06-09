package com.example.demo.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Review;
import com.example.demo.repositories.GroupStudyingRepository;
import com.example.demo.repositories.ReviewRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GroupStudyingRepository groupStudyingRepository;
	
	public boolean checkUserReview(String userName, int groupID)
	{
		return groupStudyingRepository.getById(groupID).getUserNameReviewed().contains(userName);
	}
	
	public List<Review> getAllReviewOfGroup(int groupID)
	{
		return groupStudyingRepository.getById(groupID).getReviews().stream().sorted((r1,r2) -> r2.getDateEdited().compareTo(r1.getDateEdited())).toList();
	}
	
	public void createReview(String content, double rating, String userName, int groupID)
	{
		var group = groupStudyingRepository.getById(groupID);
		var user = userRepository.getById(userName);
		
		var review = new Review();
		review.setContent(content);
		review.setGroup(group);
		review.setReviewer(user);
		review.setDateCreated(new Date());
		review.setDateEdited(new Date());
		review.setRating(rating);
		reviewRepository.save(review);
		
		group.getReviews().add(review);
		group.getUserNameReviewed().add(userName);
		groupStudyingRepository.save(group);
		
		user.getReviews().add(review);
		userRepository.save(user);
	}
	
	public void updateReview(String content, double rating, int reviewID, String userName)
	{
		var review = reviewRepository.getById(reviewID);
		
		if (review.getReviewer().getUserName().equals(userName))
		{
			review.setContent(content);
			review.setRating(rating);
			review.setDateEdited(new Date());
			reviewRepository.save(review);
		}		
	}
	
	public void deleteReview(String userName, int reviewID)
	{
		var review = reviewRepository.getById(reviewID);
		
		if (review.getReviewer().getUserName().equals(userName))
		{
			var group = review.getGroup();		
			review.setGroup(null);
			review.setReviewer(null);
			group.getReviews().remove(review);
			group.getUserNameReviewed().remove(userName);
			
			groupStudyingRepository.save(group);
			reviewRepository.delete(review);
		}
	}
}
