package com.example.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Blog;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Notifycation;
import com.example.demo.entities.NotifycationType;
import com.example.demo.entities.Reply;
import com.example.demo.entities.Subject;
import com.example.demo.repositories.BlogRepository;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.GroupStudyingRepository;
import com.example.demo.repositories.NotifycationRepository;
import com.example.demo.repositories.ReplyRepository;
import com.example.demo.repositories.SubjectRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private NotifycationRepository notifycationRepository;
	
	@Autowired
	private GroupStudyingRepository groupStudyingRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired 
	private ReplyRepository replyRepository;
	
	
	public List<Subject> getAllSubject(int groupID)
	{
		return groupStudyingRepository.getById(groupID).getSubjects();
	}
	
	public List<Blog> getAllBlogInGroup(int groupID)
	{
		return groupStudyingRepository.getById(groupID).getBlogs().stream().sorted((b1,b2) -> b1.getDateCreated().compareTo(b2.getDateCreated())).toList();
	}
	
	public List<Blog> getAllBlogInGroupBySubject(int groupID, int subjectID)
	{
		return groupStudyingRepository.getById(groupID).getBlogs().stream()
				.filter(p -> p.getSubject().getSubjectID() == subjectID)
				.sorted((b1,b2) -> b1.getDateCreated().compareTo(b2.getDateCreated())).toList();
	}
	
	public List<Blog> getAllBlogInGroupByContent(int groupID, String input)
	{
		return groupStudyingRepository.getById(groupID).getBlogs().stream()
				.filter(p -> p.getContent().contains(input))
				.sorted((b1,b2) -> b1.getDateCreated().compareTo(b2.getDateCreated())).toList();
	}
	
	public void insertImageInBlog(long blogID, MultipartFile file)
	{
		try
		{
			var blog = blogRepository.getById(blogID);
			blog.setImages(file.getBytes());
			blogRepository.save(blog);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void createSubject(int groupID, String nameSubject)
	{
		var group = groupStudyingRepository.getById(groupID);
		var subject = new Subject();
		subject.setNameSubject(nameSubject);
		subject.setGroup(group);
		group.getSubjects().add(subject);
		
		subjectRepository.save(subject);
		groupStudyingRepository.save(group);
		
	}
	
	public void updateSubject(int subjectID, String newNameSubject)
	{
		var subject = subjectRepository.getById(subjectID);
		subject.setNameSubject(newNameSubject);
		subjectRepository.save(subject);
	}
	
	public void sureToDeleteSubject(int groupID, int subjectID)
	{
		var subject = subjectRepository.getById(subjectID);
		var group = groupStudyingRepository.getById(groupID);
		
		// Tạo 3 list để lưu trữ các phần tử cần xoá 
		List<Blog> listBlogToDelete = new ArrayList<>();
		List<Comment> listCommentToDelete = new ArrayList<>();
		List<Reply> listReplyToDelete = new ArrayList<>();
		
		for (var blog : group.getBlogs()) // duyệt từng blog 
		{
			if (blog.getSubject().getSubjectID() == subjectID)
			{
				for (var cmt: blog.getComments()) // duyệt từng comment trong blog để xoá 
				{
					listReplyToDelete = new ArrayList<>();
					for (var rep : cmt.getReplies()) // duyệt từng reply trong comment để xoá 
					{
						listReplyToDelete.add(rep);
					}
					cmt.getReplies().removeAll(listReplyToDelete);
					replyRepository.deleteAll(listReplyToDelete);
					cmt.setBlog(null);
					cmt.setUserComment(null);
					commentRepository.save(cmt);
					listCommentToDelete.add(cmt);
				}
				blog.getComments().removeAll(listCommentToDelete);
				commentRepository.deleteAll(listCommentToDelete);
				blog.setGroup(null);
				blog.setSubject(null);
				blog.setUserCreated(null);
				blogRepository.save(blog);
				listBlogToDelete.add(blog);
			}
		}
		group.getBlogs().removeAll(listBlogToDelete);
		group.getSubjects().remove(subject);
		groupStudyingRepository.save(group);
	
		subject.setGroup(null);
		subjectRepository.delete(subject);
	}
	
	public void deleteSubject(int subjectID)
	{
		var subject = subjectRepository.getById(subjectID);
		subject.getGroup().getSubjects().remove(subject);
		subject.setGroup(null);
		subjectRepository.delete(subject);
	}
	
	public List<Subject> findSubjectByName(int groupID, String input)
	{
		return groupStudyingRepository.getById(groupID).getSubjects().stream().filter(p -> p.getNameSubject().contains(input)).toList();
	}
	
	public long createBlog(int GroupID, String userName, Blog blog)
	{
		var user = userRepository.getById(userName);
		var group = groupStudyingRepository.getById(GroupID);
		
		try
		{
			blog.setDateCreated(new Date());
			blog.setLikeCount(0);
			blog.setUserCreated(user);
			blog.setGroup(group);
						
			blogRepository.save(blog);
			
			Notifycation notifycation = new Notifycation().builder()
					 .Header("New Blog !!!")
					 .Content("Group " + group.getNameGroup() + " has new blog ")
					 .dateSent(new Date()).notifycationType(NotifycationType.admin)
					 .groupStudying(group).build();
		
			group.getNotifycations().add(notifycation);
			group.getBlogs().add(blog);
			groupStudyingRepository.save(group);
			
			if (notifycation.getUserSeenNotifycation() == null) notifycation.setUserSeenNotifycation(new ArrayList<>());
			if (notifycation.getUsers() == null) notifycation.setUsers(new ArrayList<>());
	
			for (var p: group.getUsers())
			{
				notifycation.getUserSeenNotifycation().add(p);
				notifycation.getUsers().add(p);
				p.getNotifycations().add(notifycation);
			}
			
			notifycationRepository.save(notifycation); 
			
			return blog.getBlogID();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	/*
	public void likeBlog(long blogID)
	{
		var blog = blogRepository.getById(blogID);
		blog.setLikeCount(blog.getLikeCount() + 1);
		blogRepository.save(blog);
	}*/
	
	public void updateBlog(long blogID, Blog blog)
	{
		var existingBlog = blogRepository.getById(blogID);
		existingBlog.setContent(blog.getContent());
		existingBlog.setDateCreated(new Date());
		existingBlog.setSubject(blog.getSubject());
		existingBlog.setLikeCount(blog.getLikeCount());
		blogRepository.save(existingBlog);
	}
	
	public void deleteBlog(long blogID)
	{
		var blog = blogRepository.getById(blogID);
		blog.getGroup().getBlogs().remove(blog);
		blog.setGroup(null);
		blog.setUserCreated(null);
		blog.setSubject(null);
		for (var p : blog.getComments())
		{
			deleteComment(p.getCommentID());
		}
		blog.setComments(null);
		blogRepository.delete(blog);
	}
	
	public void commentBlog(long blogID, String userName, Comment cmt)
	{
		var blog = blogRepository.getById(blogID);
		var sentUser = userRepository.getById(userName);
		var receivedUser = blog.getUserCreated();
		
		cmt.setBlog(blog);
		cmt.setUserComment(sentUser);
		cmt.setDateComment(new Date());
		commentRepository.save(cmt);
		
		blog.getComments().add(cmt);
		blogRepository.save(blog);
		
		Notifycation notifycation = new Notifycation().builder()
				 .Header("Your Blog " + blog.getBlogID() + " in group " + blog.getGroup().getNameGroup() + " has new comment !!!")
				 .Content("Group " + blog.getGroup().getNameGroup() + ", blog " + blog.getContent() + " has new comment ")
				 .dateSent(new Date()).notifycationType(NotifycationType.admin)
				 .groupStudying(blog.getGroup()).build();
		
		if (notifycation.getUserSeenNotifycation() == null) notifycation.setUserSeenNotifycation(new ArrayList<>());
		if (notifycation.getUsers() == null) notifycation.setUsers(new ArrayList<>());
		
		receivedUser.getNotifycations().add(notifycation);
		notifycation.getUsers().add(receivedUser);
		notifycation.getUserSeenNotifycation().add(receivedUser);
		
		notifycationRepository.save(notifycation); 
		userRepository.save(sentUser);
		userRepository.save(receivedUser);
	}
	
	public void updateComment(int commentID, Comment cmt)
	{
		var existingComment = commentRepository.getById(commentID);
		existingComment.setContent(cmt.getContent());
		existingComment.setDateComment(new Date());
		commentRepository.save(existingComment);
	}
	
	public void deleteComment(int commentID)
	{
		var cmt = commentRepository.getById(commentID);
		cmt.setBlog(null);
		cmt.setUserComment(null);
		
		for (var rep : cmt.getReplies())
		{
			deleteReply(rep.getReplyID());
		}
		
		cmt.setReplies(null);
		commentRepository.delete(cmt);
	}
	
	public List<Comment> getAllCommentOfBlog(long blogID)
	{
		return blogRepository.getById(blogID).getComments();
	}
	
	public List<Reply> getAllReplyOfComment(int commentID)
	{
		return commentRepository.getById(commentID).getReplies();
	}
	
	public void replyComment(int commentID, String userName, Reply reply)
	{
		var cmt = commentRepository.getById(commentID);
		var sentUser = userRepository.getById(userName);
		var receivedUser = cmt.getUserComment();
		
		reply.setDateReplied(new Date());
		reply.setUserReplied(sentUser);
		reply.setComment(cmt);
		cmt.getReplies().add(reply);
		
		replyRepository.save(reply);
		commentRepository.save(cmt);
		
		Notifycation notifycation = new Notifycation().builder()
				 .Header("Your Comment " + cmt.getCommentID() + " in group " + cmt.getBlog().getGroup().getNameGroup() + ", in blog " + cmt.getBlog().getBlogID() + " has new reply !!!")
				 .Content("Group " + cmt.getBlog().getGroup().getNameGroup() + ", blog " + cmt.getBlog().getContent() + ", in comment " + cmt.getContent() + " has new reply ")
				 .dateSent(new Date()).notifycationType(NotifycationType.admin)
				 .groupStudying(cmt.getBlog().getGroup()).build();
		
		if (notifycation.getUserSeenNotifycation() == null) notifycation.setUserSeenNotifycation(new ArrayList<>());
		if (notifycation.getUsers() == null) notifycation.setUsers(new ArrayList<>());
		
		receivedUser.getNotifycations().add(notifycation);
		notifycation.getUsers().add(receivedUser);
		notifycation.getUserSeenNotifycation().add(receivedUser);
		
		notifycationRepository.save(notifycation);
		userRepository.save(sentUser);
		userRepository.save(receivedUser);
	}
	
	public void updateReply(int replyID, Reply reply)
	{
		var existingReply = replyRepository.getById(replyID);
		existingReply.setContent(reply.getContent());
		existingReply.setDateReplied(new Date());
		replyRepository.save(existingReply);
	}
	
	public void deleteReply(int replyID)
	{
		var reply = replyRepository.getById(replyID);
		reply.setUserReplied(null);
		reply.setComment(null);
		replyRepository.delete(reply);
	}
}
