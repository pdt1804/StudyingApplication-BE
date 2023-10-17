package com.example.demo.controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.demo.entities.Blog;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Reply;
import com.example.demo.entities.Subject;
import com.example.demo.services.BlogService;

@RestController
@RequestMapping("/api/v1/blog")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
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
			e.printStackTrace();
			return false;
		}
	}
	
	@PostMapping("/createNewBlog")
	public long createNewBlog(@RequestParam("groupID") int groupID, @RequestParam("userName") String userName, @RequestBody Blog blog)
	{
		System.out.println(blog.getContent());
		return blogService.createBlog(groupID, userName, blog);
	}
	
	@PostMapping("/insertImage")
	public void insertImage(@RequestParam("blogID") long blogID, @RequestParam("file") MultipartFile file)
	{
		blogService.insertImageInBlog(blogID, file);
	}
	
	@PutMapping("/updateBlog")
	public void updateBlog(@RequestParam("blogID") long blogID, Blog blog)
	{
		blogService.updateBlog(blogID, blog);
	}
	
	@DeleteMapping("/deleteBlog")
	public void deleteBlog(@RequestParam("blogID") long blogID)
	{
		blogService.deleteBlog(blogID);
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
	public void commentBlog(@RequestParam("blogID") long blogID, @RequestParam("userName") String userName, @RequestBody Comment comment)
	{
		blogService.commentBlog(blogID, userName, comment);
	}
	
	@PutMapping("/updateComment")
	public void updateComment(@RequestParam("commentID") int commentID, @RequestBody Comment comment)
	{
		blogService.updateComment(commentID, comment);
	}
	
	@DeleteMapping("/deleteComment")
	public void deleteComment(@RequestParam("commentID") int commentID)
	{
		blogService.deleteComment(commentID);
	}
	
	@PostMapping("/replyComment")
	public void replyComment(@RequestParam("commentID") int commentID, @RequestParam("userName") String userName, @RequestBody Reply reply)
	{
		blogService.replyComment(commentID, userName, reply);
	}
	
	@PutMapping("/updateReply")
	public void updateReply(@RequestParam("replyID") int replyID, @RequestBody Reply reply)
	{
		blogService.updateReply(replyID, reply);
	}
	
	@DeleteMapping("/deleteReply")
	public void deleteReply(@RequestParam("replyID") int replyID)
	{
		blogService.deleteReply(replyID);
	}
	
	
}
