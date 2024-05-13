package com.example.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.config.CloudinaryService;
import com.example.demo.entities.Blog;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Document;
import com.example.demo.entities.GroupStudying;
import com.example.demo.entities.Notifycation;
import com.example.demo.entities.NotifycationType;
import com.example.demo.entities.Reply;
import com.example.demo.entities.Subject;
import com.example.demo.entities.TagType;
import com.example.demo.entities.TypeRequest;
import com.example.demo.entities.UpdateBlogRequest;
import com.example.demo.repositories.BlogRepository;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.GroupStudyingRepository;
import com.example.demo.repositories.NotifycationRepository;
import com.example.demo.repositories.ReplyRepository;
import com.example.demo.repositories.SubjectRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.serviceInterfaces.BlogManagement;
import com.example.demo.serviceInterfaces.CommentManagement;
import com.example.demo.serviceInterfaces.ReplyManagement;
import com.example.demo.serviceInterfaces.SubjectManagement;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

import io.jsonwebtoken.io.IOException;

@Service
public class BlogService implements SubjectManagement, BlogManagement, CommentManagement, ReplyManagement {

	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private CloudinaryService cloudinaryService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Cloudinary cloudinary;
	
	@Autowired 
	private NotifycationRepository notifycationRepository;
	
	@Autowired 
	private NotifycationService notifycationService;
	
	@Autowired
	private GroupStudyingRepository groupStudyingRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired 
	private ReplyRepository replyRepository;
	
	@Override
	public int getNumberOfBlogBySubject(int subjectID, int groupID)
	{
		int i = 0;
		for (var p : groupStudyingRepository.getById(groupID).getBlogs())
		{
			if (p.getSubject().getSubjectID() == subjectID)
			{
				i++;
			}
		}
		return i;
	}
	
	@Override
	public Blog getBlogById(long id)
	{
		try
		{
			var blog = blogRepository.getById(id);
			if (blog != null)
			{
				return blogRepository.getById(id);
			}
			else
			{
				return null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Subject> getAllSubject(int groupID)
	{
		return groupStudyingRepository.getById(groupID).getSubjects();
	}

	@Override
	public List<Blog> getAllBlogInGroup(int groupID)
	{
		return groupStudyingRepository.getById(groupID).getBlogs().stream().sorted((b1,b2) -> b2.getDateCreated().compareTo(b1.getDateCreated())).toList();
	}

	@Override
	public List<Blog> getAllBlogInGroupBySubject(int groupID, int subjectID)
	{
		return groupStudyingRepository.getById(groupID).getBlogs().stream()
				.filter(p -> p.getSubject().getSubjectID() == subjectID)
				.sorted((b1,b2) -> b2.getDateCreated().compareTo(b1.getDateCreated())).toList();
	}

	@Override
	public List<Blog> getAllBlogInGroupByContent(int groupID, String input)
	{
		return groupStudyingRepository.getById(groupID).getBlogs().stream()
				.filter(p -> p.getContent().contains(input))
				.sorted((b1,b2) -> b2.getDateCreated().compareTo(b1.getDateCreated())).toList();
	}

	@Override
	public void likeBlog(String userName, long blogID) {
	    var user = userRepository.getById(userName);
	    var blog = blogRepository.getById(blogID);
	    
	    List<String> likes = blog.getLikes();
	    
	    if (likes == null) {
	        likes = new ArrayList<>();
	        blog.setLikes(likes);
	    }
	    
	    boolean check = false;
	    
	    for (var p : likes) {
	        if (p.equals(user.getUserName())) {
	            likes.remove(p);
	            check = true;
	            break;
	        }
	    }
	    
	    if (!check) {
	        likes.add(user.getUserName());
	    }

	    blogRepository.save(blog);
	    userRepository.save(user);
	}


	@Override
	public boolean checkLikeBlog(String userName, long blogID)
	{
		boolean check = false;
		
		var blog = blogRepository.getById(blogID);
		
		if (blog.getLikes() == null)
		{
			return false;
		}
		
		for (var p : blog.getLikes())
		{
			if (p.equals(userName))
			{
				check = true;
				break;
			}
		}
		
		return check;
	}
	
	@Override
	public void insertImageInBlog(long blogID, MultipartFile file)
	{
		try
		{
			var blog = blogRepository.getById(blogID);
			Map<String, String> data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
			blog.getImage().add(data.get("url") + "-" + data.get("public_id"));
			blogRepository.save(blog);
		}        
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void insertImageInComment(int commentID, MultipartFile file)
	{
		try
		{
			var cmt = commentRepository.getById(commentID);
			Map<String, String> data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
			cmt.getImages().add(data.get("url") + "-" + data.get("public_id"));
			commentRepository.save(cmt);
		}        
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void insertImageInReply(int replyID, MultipartFile file)
	{
		try
		{
			var rep = replyRepository.getById(replyID);
			Map<String, String> data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
			rep.getImages().add(data.get("url") + "-" + data.get("public_id"));
			replyRepository.save(rep);
		}        
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
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

	@Override
	public void updateSubject(int subjectID, String newNameSubject)
	{
		var subject = subjectRepository.getById(subjectID);
		subject.setNameSubject(newNameSubject);
		subjectRepository.save(subject);
	}
	
	@Override
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
	
	@Override
	public List<Subject> findSubjectByName(int groupID, String input)
	{
		return groupStudyingRepository.getById(groupID).getSubjects().stream().filter(p -> p.getNameSubject().contains(input)).toList();
	}
	
	@Override
	public long createBlog(int GroupID, String userName, String content, int subjectID, List<String> userNames)
	{
		var user = userRepository.getById(userName);
		var group = groupStudyingRepository.getById(GroupID);
		var subject = subjectRepository.getById(subjectID);
		
		try
		{
			var blog = new Blog();
			blog.setContent(content);
			blog.setDateCreated(new Date());
			//blog.setLikeCount(0);
			blog.setUserCreated(user);
			blog.setGroup(group);
			blog.setSubject(subject);
						
			blogRepository.save(blog);
						
			sendNotification(userNames, userName, blog.getBlogID(), TagType.BLOG, group);
						
			Notifycation notifycation = new Notifycation().builder()
					 .Header("Bài thảo luận mới !!!")
					 .Content("Nhóm " + group.getNameGroup() + " có bài thảo luận mới với mã thảo luận là " + blog.getBlogID() + ", click vào đây để xem ")
					 .dateSent(new Date()).notifycationType(NotifycationType.admin)
					 .groupStudying(group).build();
		
			group.getNotifycations().add(notifycation);
			group.getBlogs().add(blog);
			groupStudyingRepository.save(group);
			
			if (notifycation.getUserSeenNotifycation() == null) notifycation.setUserSeenNotifycation(new ArrayList<>());
			if (notifycation.getUsers() == null) notifycation.setUsers(new ArrayList<>());
	
			for (var p: group.getUsers())
			{
				notifycation.getUserSeenNotifycation().add(p.getUserName());
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
	
	public void UploadImageToCloudinaryForBlog(long id, MultipartFile file) throws java.io.IOException
	{
		if (file == null) return;
		else cloudinaryService.uploadFilesBlog(file, id);
	}
	
	private void UploadImageToFirebaseForComment(int id, List<MultipartFile> files) throws java.io.IOException
	{
		Comment obj = commentRepository.getById(id);;
		
		if (obj != null)
		{
			for (var file : files)
			{
				Random rd = new Random();
				String nameOnCloud = file.getName() + "-" + "-" + rd.nextInt(1, 9999999) + "-" + UUID.randomUUID();
				Bucket bucket = StorageClient.getInstance().bucket();
				var blob = bucket.create(nameOnCloud, file.getBytes(), file.getContentType());
				
				obj.getImages().add(nameOnCloud);
			}
			
			commentRepository.save(obj);
		}
	}
	
	private void UploadImageToFirebaseForReply(int id, List<MultipartFile> files) throws java.io.IOException
	{
		Reply obj = replyRepository.getById(id);;
		
		if (obj != null)
		{
			for (var file : files)
			{
				Random rd = new Random();
				String nameOnCloud = file.getName() + "-" + "-" + rd.nextInt(1, 9999999) + "-" + UUID.randomUUID();
				Bucket bucket = StorageClient.getInstance().bucket();
				var blob = bucket.create(nameOnCloud, file.getBytes(), file.getContentType());
				
				obj.getImages().add(nameOnCloud);
			}
			
			replyRepository.save(obj);
		}
	}
	
	/*
	public void likeBlog(long blogID)
	{
		var blog = blogRepository.getById(blogID);
		blog.setLikeCount(blog.getLikeCount() + 1);
		blogRepository.save(blog);
	}*/
	
	@Override
	public void updateBlog(long blogID, String content, List<UpdateBlogRequest> requests) throws java.io.IOException
	{
		var existingBlog = blogRepository.getById(blogID);

		if (existingBlog != null)
		{
			if (requests.size() > 0)
			{
				for (var p : requests)
				{
					ExecuteRequest(existingBlog, p);
				}
			}
			
			if (content != null)
			{	
				existingBlog.setContent(content);
			}
			
			existingBlog.setDateCreated(new Date());
			blogRepository.save(existingBlog);
		}
	}
	
	private void ExecuteRequest(Blog blog, UpdateBlogRequest p) throws java.io.IOException {
		Bucket bucket = StorageClient.getInstance().bucket();
		
		if (p.getType() == TypeRequest.ADD)
		{
			Random rd = new Random();
			String nameOnCloud = p.getFile().getName() + "-" + "-" + rd.nextInt(1, 9999999) + "-" + UUID.randomUUID();
			bucket.create(nameOnCloud, p.getFile().getBytes(), p.getFile().getContentType());
			
			blog.getImage().add(nameOnCloud);
		}
		else if (p.getType() == TypeRequest.REMOVE)
		{
			bucket.get(p.getFileName()).delete();
			blog.getImage().remove(p.getFileName());
		}
	}

	@Override
	public void deleteBlog(long blogID)
	{
		try
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
			
			deleteFiles(blog.getImage());

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public int commentBlog(long blogID, String userName, String content, List<String> userNames)
	{
		var blog = blogRepository.getById(blogID);
		var sentUser = userRepository.getById(userName);
		var receivedUser = blog.getUserCreated();
		
		var cmt = new Comment();
		cmt.setContent(content);
		
		cmt.setBlog(blog);
		cmt.setUserComment(sentUser);
		cmt.setDateComment(new Date());
		cmt.setImages(new ArrayList<>());

		commentRepository.save(cmt);
		
		sendNotification(userNames, userName, cmt.getCommentID(), TagType.COMMENT, blog.getGroup());
		
		blog.getComments().add(cmt);
		blogRepository.save(blog);
		
		Notifycation notifycation = new Notifycation().builder()
				 .Content("Bài thảo luận có mã thảo luận là " + blog.getBlogID() + ", trong nhóm " + blog.getGroup().getNameGroup() + " có bình luận mới: " + cmt.getContent())
				 .Header("Nhóm " + blog.getGroup().getNameGroup() + ", blog " + blog.getContent() + " có bình luận mới ")
				 .dateSent(new Date()).notifycationType(NotifycationType.admin)
				 .groupStudying(blog.getGroup()).build();
		
		if (notifycation.getUserSeenNotifycation() == null) notifycation.setUserSeenNotifycation(new ArrayList<>());
		if (notifycation.getUsers() == null) notifycation.setUsers(new ArrayList<>());
		
		receivedUser.getNotifycations().add(notifycation);
		notifycation.getUsers().add(receivedUser);
		notifycation.getUserSeenNotifycation().add(receivedUser.getUserName());
		
		notifycationRepository.save(notifycation); 
		userRepository.save(sentUser);
		userRepository.save(receivedUser);
		
		return cmt.getCommentID();
	}
	
	public void updateComment(int commentID, Comment cmt)
	{
		var existingComment = commentRepository.getById(commentID);
		existingComment.setContent(cmt.getContent());
		existingComment.setDateComment(new Date());
		commentRepository.save(existingComment);
	}
	
	public void deleteComment(int commentID) throws java.io.IOException
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
		
		deleteFiles(cmt.getImages());
	}
	
	@Override
	public List<Comment> getAllCommentOfBlog(long blogID)
	{
		return blogRepository.getById(blogID).getComments();
	}
	
	@Override
	public List<Reply> getAllReplyOfComment(int commentID)
	{
		return commentRepository.getById(commentID).getReplies();
	}
	
	@Override
	public int replyComment(int commentID, String userName, String content, List<String> userNames)
	{
		var cmt = commentRepository.getById(commentID);
		var sentUser = userRepository.getById(userName);
		var receivedUser = cmt.getUserComment();
		
		var reply = new Reply();
		reply.setContent(content);
		
		reply.setDateReplied(new Date());
		reply.setUserReplied(sentUser);
		reply.setComment(cmt);
		cmt.getReplies().add(reply);
		reply.setImages(new ArrayList<>());
		
		replyRepository.save(reply);
		
		sendNotification(userNames, userName, reply.getReplyID(), TagType.REPLY, cmt.getBlog().getGroup());

		commentRepository.save(cmt);
		
		Notifycation notifycation = new Notifycation().builder()
				 .Content("Bình luận " + cmt.getContent() + " trong nhóm " + cmt.getBlog().getGroup().getNameGroup() + ", trong bài thảo luận có mã thảo luận là " + cmt.getBlog().getBlogID() + ", có phản hồi mới !!!")
				 .Header("Nhóm " + cmt.getBlog().getGroup().getNameGroup() + ", bài thảo luận " + cmt.getBlog().getContent() + ", bình luận " + cmt.getContent() + " có phản hồi mới ")
				 .dateSent(new Date()).notifycationType(NotifycationType.admin)
				 .groupStudying(cmt.getBlog().getGroup()).build();
		
		if (notifycation.getUserSeenNotifycation() == null) notifycation.setUserSeenNotifycation(new ArrayList<>());
		if (notifycation.getUsers() == null) notifycation.setUsers(new ArrayList<>());
		
		receivedUser.getNotifycations().add(notifycation);
		notifycation.getUsers().add(receivedUser);
		notifycation.getUserSeenNotifycation().add(receivedUser.getUserName());
		
		notifycationRepository.save(notifycation);
		userRepository.save(sentUser);
		userRepository.save(receivedUser);
		
		return reply.getReplyID();
	}
	
	public void updateReply(int replyID, Reply reply)
	{
		var existingReply = replyRepository.getById(replyID);
		existingReply.setContent(reply.getContent());
		existingReply.setDateReplied(new Date());
		replyRepository.save(existingReply);
	}
	
	public void deleteReply(int replyID) throws java.io.IOException
	{
		var reply = replyRepository.getById(replyID);
		reply.setUserReplied(null);
		reply.setComment(null);
		replyRepository.delete(reply);
		
		deleteFiles(reply.getImages());
	}
	
	private void deleteFiles(List<String> images) throws java.io.IOException {
		if (images.size() == 0) return;
		
		for (var p : images)
		{
			String[] parts = p.split("-");
			System.out.println(parts[1]);
			cloudinary.uploader().destroy(parts[1], ObjectUtils.asMap("type", "upload", "resource_type", "image"));
		}
	}

	private void sendNotification(List<String> userNames, String userNameTag, int id, TagType type, GroupStudying group)
	{
		if (userNames.size() == 0) return;
		
		String typeName;
		if (type == TagType.BLOG) typeName = "bài blog";
		else if (type == TagType.COMMENT) typeName = "1 comment";
		else typeName = "1 reply";
		
		var userTag = userRepository.getById(userNameTag);
		
		Notifycation notifycation = new Notifycation().builder()
				 .Content(userTag.getInformation().getFulName() + " tag bạn vào " + typeName + " trong nhóm " + group.getNameGroup() + ".")
				 .Header("Bạn đã được tag tên !!!").contentID(id)
				 .groupStudying(group)
				 .dateSent(new Date()).notifycationType(NotifycationType.user).build();
				
		if (notifycation.getUserSeenNotifycation() == null) notifycation.setUserSeenNotifycation(new ArrayList<>());
		if (notifycation.getUsers() == null) notifycation.setUsers(new ArrayList<>());
		
		notifycationRepository.save(notifycation);
				
		for (var p : userNames)
		{
			var user = userRepository.getById(p);
			
			user.getNotifycations().add(notifycation);
			notifycation.getUsers().add(user);
			notifycation.getUserSeenNotifycation().add(user.getUserName());

			userRepository.save(user);			
		}		
		notifycationRepository.save(notifycation);
	}
	
	public void resendNotificationForBlog(List<String> userNames, String userNameTag, int groupID, int blogID)
	{
		if (userNames.size() == 0) return;
		
		var userTag = userRepository.getById(userNameTag);
		var group = groupStudyingRepository.getById(groupID);
		
		Notifycation notifycation = new Notifycation().builder()
				 .Content(userTag.getInformation().getFulName() + " tag bạn vào 1 bài viết trong nhóm " + group.getNameGroup() + ".")
				 .Header("Bạn đã được tag tên !!!").contentID(blogID)
				 .groupStudying(group)
				 .dateSent(new Date()).notifycationType(NotifycationType.user).build();
				
		if (notifycation.getUserSeenNotifycation() == null) notifycation.setUserSeenNotifycation(new ArrayList<>());
		if (notifycation.getUsers() == null) notifycation.setUsers(new ArrayList<>());
		
		notifycationRepository.save(notifycation);
				
		for (var p : userNames)
		{
			var user = userRepository.getById(p);
			
			user.getNotifycations().add(notifycation);
			notifycation.getUsers().add(user);
			notifycation.getUserSeenNotifycation().add(user.getUserName());

			userRepository.save(user);			
		}		
		notifycationRepository.save(notifycation);
	}
	
//	private Notifycation Clone(Notifycation notifycation)
//	{
//		return  new Notifycation().builder()
//				 .Content(notifycation.getContent())
//				 .Header(notifycation.getHeader()).contentID(notifycation.getContentID())
//				 .dateSent(new Date()).notifycationType(NotifycationType.user).build();	
//	}
}
