package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Review;
import com.example.demo.services.JwtService;
import com.example.demo.services.ReviewService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private JwtService jwtService;
	
	public String extractTokenToGetUsername(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        return jwtService.extractUsername(token);
	}
	
	@GetMapping("/checkUserReview")
	public boolean checkUserReview(@RequestParam("groupID") int groupID, HttpServletRequest request)
	{
		return reviewService.checkUserReview(extractTokenToGetUsername(request), groupID);
	}
	
	@GetMapping("/getAllReviewOfGroup")
	public List<Review> getAllReviewOfGroup(@RequestParam("groupID") int groupID)
	{
		return reviewService.getAllReviewOfGroup(groupID);
	}
	
	@PostMapping("/createReview")
	public void createReview(@RequestParam("groupID") int groupID, @RequestParam("rating") double rating, @RequestParam("content") String content, HttpServletRequest request)
	{
		reviewService.createReview(content, rating, extractTokenToGetUsername(request), groupID);
	}
	
	@PutMapping("/updateReview")
	public void updateReview(@RequestParam("reviewID") int reviewID, @RequestParam("rating") double rating, @RequestParam("content") String content, HttpServletRequest request)
	{
		reviewService.updateReview(content, rating, reviewID, extractTokenToGetUsername(request));
	}
	
	@DeleteMapping("/deleteReview")
	public void deleteReview(@RequestParam("reviewID") int reviewID, HttpServletRequest request)
	{
		reviewService.deleteReview(extractTokenToGetUsername(request), reviewID);
	}
}
