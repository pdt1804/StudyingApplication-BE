package com.example.demo.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.BlogDTO;
import com.example.demo.entities.Blog;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Reply;
import com.example.demo.entities.Subject;
import com.example.demo.entities.UpdateBlogRequest;
import com.example.demo.services.BlogService;
import com.example.demo.services.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/blog")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private JwtService jwtService;
	
	public String extractTokenToGetUsername(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        return jwtService.extractUsername(token);
	}
	
	@GetMapping("/getAllSubject")
	public List<Subject> getAllSubject(@RequestParam("groupID") int groupID)
	{
		return blogService.getAllSubject(groupID);
	}
	
	@GetMapping("/getAllBlog")
	public List<Blog> getAllBlog(@RequestParam("groupID") int groupID)
	{
		return blogService.getAllBlogInGroup(groupID);
	}
	
	@GetMapping("/getAllBlogByContent")
	public List<Blog> getAllBlogByContent(@RequestParam("groupID") int groupID, @RequestParam("input") String input)
	{
		return blogService.getAllBlogInGroupByContent(groupID, input);
	}
	
	@PostMapping("/insertImageInBlog")
	public void insertImageInBlog(@RequestParam("blogID") long blogID, @RequestParam("file") MultipartFile file)
	{
		blogService.insertImageInBlog(blogID, file);
	}
	
	@GetMapping("/getBlogById")
	public ResponseEntity<BlogDTO> getBlogById(@RequestParam("blogID") long id)
	{
		var blog = blogService.getBlogById(id);

	    if (blog != null) {
	        return ResponseEntity.ok(new BlogDTO(blog));
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/getNumberOfBlogBySubject")
	public int getNumberOfBlogBySubject(@RequestParam("subjectID") int subjectID, @RequestParam("groupID") int groupID)
	{
		return blogService.getNumberOfBlogBySubject(subjectID, groupID);
	}
	
	@GetMapping("/checkLikeBlog")
	public boolean checkLikeBlog(HttpServletRequest request, @RequestParam("blogID") long blogID)
	{
		return blogService.checkLikeBlog(extractTokenToGetUsername(request), blogID);
	}
	
	@PostMapping("/likeBlog")
	public void likeBlog(HttpServletRequest request, @RequestParam("blogID") long blogID)
	{
		blogService.likeBlog(extractTokenToGetUsername(request), blogID);
	}
	
	@GetMapping("/getAllBlogBySubject")
	public List<Blog> getAllBlogBySubject(@RequestParam("groupID") int groupID, @RequestParam("subjectID") int subjectID)
	{
		return blogService.getAllBlogInGroupBySubject(groupID, subjectID);
	}
	
	@GetMapping("/getAllCommentInBlog")
	public List<Comment> getAllCommentInBlog(@RequestParam("blogID") int blogID)
	{
		return blogService.getAllCommentOfBlog(blogID);
	}
	
	@GetMapping("/getAllReplyInComment")
	public List<Reply> getAllReplyInComment(@RequestParam("commentID") int commentID)
	{
		return blogService.getAllReplyOfComment(commentID);
	}
	
	@GetMapping("/findAllSubjectInGroupbyInput")
	public List<Subject> findAllSubjectInGroupbyInput(@RequestParam("groupID") int groupID, @RequestParam("input") String input)
	{
		return blogService.findSubjectByName(groupID, input);
	}
	
	@PostMapping("/createNewSubject")
	public void createNewSubject(@RequestParam("groupID") int groupID, @RequestParam("nameSubject") String nameSubject)
	{
		blogService.createSubject(groupID, nameSubject);
	}
	
	@PutMapping("/updateSubject")
	public void updateSubject(@RequestParam("subjectID") int subjectID, @RequestParam("newNameSubject") String newNameSubject)
	{
		blogService.updateSubject(subjectID, newNameSubject);
	}
	
	@DeleteMapping("/deleteSubject")
	public boolean deleteSubject(@RequestParam("subjectID") int subjectID)
	{
		try
		{
			blogService.deleteSubject(subjectID);
			return true;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	@PostMapping("/createNewBlog")
	public long createNewBlog(@RequestParam("groupID") int groupID, 
							  @RequestParam("content") String content, HttpServletRequest request, 
							  @RequestParam("subjectID") int subjectID, 
							  @RequestParam("userNames") List<String> userNames)
	{
		return blogService.createBlog(groupID, extractTokenToGetUsername(request), content, subjectID, userNames);
	}
	
	@PostMapping(value = "/uploadImage")
	public void uploadBlogImage(@RequestParam("blogID") int blogID, 
				     			@RequestParam("file") MultipartFile file) throws IOException
	{
		System.out.println(file);
		blogService.UploadImageToCloudinaryForBlog(blogID, file);
	}
	
	@PostMapping("/insertImage")
	public void insertImage(@RequestParam("blogID") long blogID, @RequestParam("file") MultipartFile file)
	{
		blogService.insertImageInBlog(blogID, file);
	}
	
	@PutMapping("/updateBlog") 
	public void updateBlog(@RequestParam("blogID") long blogID, @RequestParam("content") String content, @RequestParam("requestImages") List<UpdateBlogRequest> requests) throws java.io.IOException
	{
		blogService.updateBlog(blogID, content, requests);
	}
	
	@DeleteMapping("/deleteBlog")
	public void deleteBlog(@RequestParam("blogID") long blogID)
	{
		blogService.deleteBlog(blogID);
	}
	
	@PostMapping("/reSendNotificationForBlog")
	public void resendNotificationForBlog(@RequestParam("blogID") int blogID, @RequestParam("groupID") int groupID, @RequestParam("userNames") List<String> userNames, HttpServletRequest request)
	{
		blogService.resendNotificationForBlog(userNames, extractTokenToGetUsername(request), groupID, blogID);
	}
	
	@DeleteMapping("/sureToDeleteSubject")
	public void sureToDeleteSubject(@RequestParam("subjectID") int subjectID, @RequestParam("groupID") int groupID)
	{
		blogService.sureToDeleteSubject(groupID, subjectID);
	}
	
	/*
	@PostMapping("/likeBlog")
	public void likeBlog(@RequestParam("blogID") int blogID)
	{
		blogService.likeBlog(blogID);
	}*/
	
	@PostMapping("/commentBlog")
	public int commentBlog(@RequestParam("blogID") long blogID, @RequestParam("content") String content, HttpServletRequest request , @RequestParam("userNames") List<String> userNames)
	{
		return blogService.commentBlog(blogID, extractTokenToGetUsername(request), content, userNames);
	}
	
	@PostMapping("/insertImageInComment")
	public void insertImageInComment(@RequestParam("commentID") int commentID, @RequestParam("file") MultipartFile file)
	{
		blogService.insertImageInComment(commentID, file);
	}
	
	@PutMapping("/updateComment")
	public void updateComment(@RequestParam("commentID") int commentID, @RequestBody Comment comment)
	{
		blogService.updateComment(commentID, comment);
	}
	
	@DeleteMapping("/deleteComment")
	public void deleteComment(@RequestParam("commentID") int commentID) throws IOException
	{
		blogService.deleteComment(commentID);
	}
	
	@PostMapping("/replyComment")
	public int replyComment(@RequestParam("commentID") int commentID, HttpServletRequest request, @RequestParam("content") String content, @RequestParam("userNames") List<String> userNames)
	{
		return blogService.replyComment(commentID, extractTokenToGetUsername(request), content, userNames);
	}
	
	@PostMapping("/insertImageInReply")
	public void insertImageInReply(@RequestParam("replyID") int replyID, @RequestParam("file") MultipartFile file)
	{
		blogService.insertImageInReply(replyID, file);
	}
	
	@PutMapping("/updateReply")
	public void updateReply(@RequestParam("replyID") int replyID, @RequestBody Reply reply)
	{
		blogService.updateReply(replyID, reply);
	}
	
	@DeleteMapping("/deleteReply")
	public void deleteReply(@RequestParam("replyID") int replyID) throws IOException
	{
		blogService.deleteReply(replyID);
	}
	
	
}
