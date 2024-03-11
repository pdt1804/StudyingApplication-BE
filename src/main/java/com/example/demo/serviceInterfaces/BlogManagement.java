package com.example.demo.serviceInterfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Blog;
import com.example.demo.entities.Notifycation;
import com.example.demo.entities.NotifycationType;

public interface BlogManagement {

	public int getNumberOfBlogBySubject(int subjectID, int groupID);
	
	public Blog getBlogById(long id);
	
	public List<Blog> getAllBlogInGroup(int groupID);
	
	public List<Blog> getAllBlogInGroupBySubject(int groupID, int subjectID);
	
	public List<Blog> getAllBlogInGroupByContent(int groupID, String input);
	
	public void likeBlog(String userName, long blogID);
	
	public boolean checkLikeBlog(String userName, long blogID);
	
	public void insertImageInBlog(long blogID, MultipartFile file);
	
	public long createBlog(int GroupID, String userName, int subjectID, Blog blog);
	
	public void updateBlog(long blogID, String content);
	
	public void deleteBlog(long blogID);
}
