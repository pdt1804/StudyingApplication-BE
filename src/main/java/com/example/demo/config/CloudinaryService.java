package com.example.demo.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.entities.File;
import com.example.demo.entities.User;
import com.example.demo.repositories.BlogRepository;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.FileRepository;
import com.example.demo.repositories.ReplyRepository;
import com.example.demo.services.BlogService;
import com.example.demo.services.UserService;

@Configuration
public class CloudinaryService {

	@Autowired
	private UserService userService;
	
	@Autowired 
	private BlogRepository blogRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private ReplyRepository replyRepository;

	@Bean
	public Cloudinary getCloudinary()
	{
		var config = new HashMap<>();
        config.put("cloud_name", "dguickmkg");
        config.put("api_key", "337925671823776");
        config.put("api_secret", "yOHPBilWervow9a2HVUVkeC6DKg");
        config.put("secure", true);
        return new Cloudinary(config);
	}
	
	public String uploadImageAvatar(MultipartFile file, String userName)
	{
		try{
	           Map<String, String> data = this.getCloudinary().uploader().upload(file.getBytes(), Map.of());
	           User user = userService.GetUserByUsername(userName);
				if (user == null)
				{ 
					return "Không tồn tại tài khoản";
				}
				else
				{
					if (user.getInformation().getPublicID() != null)
					{
						this.getCloudinary().uploader().destroy(user.getInformation().getPublicID(), ObjectUtils.asMap("type", "upload", "resource_type", "image"));
					}
					
					user.getInformation().setPublicID(data.get("public_id"));
					user.getInformation().setImage(data.get("url"));
					userService.changeAvatar(user);
					System.out.println("thanhf coong");
					return "Thay đổi ảnh thành công";
				}
	        }catch (IOException io){
	            throw new RuntimeException("Image upload fail");
	            //return "That bai";
	        }
	}

	public void uploadFilesBlog(MultipartFile file, long blogID, int width, int height)
	{
		try
		{
			var blog = blogRepository.getById(blogID);
			Map<String, String> data = this.getCloudinary().uploader().upload(file.getBytes(), Map.of());
			//blog.getImage().add(data.get("url") + "-" + data.get("public_id"));
			var f = new File(data.get("url"), data.get("public_id"), blog);
			f.setHeight(height);
			f.setWidth(width);
			fileRepository.save(f);
			blog.getFiles().add(f);
			blogRepository.save(blog);
		}        
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void uploadFilesComment(MultipartFile file, int cmtID, int width, int height)
	{
		try
		{
			var cmt = commentRepository.getById(cmtID);
			Map<String, String> data = this.getCloudinary().uploader().upload(file.getBytes(), Map.of());
			var f = new File(data.get("url"), data.get("public_id"), cmt);
			f.setHeight(height);
			f.setWidth(width);
			fileRepository.save(f);
			cmt.getFiles().add(f);
			commentRepository.save(cmt);
		}        
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void uploadFilesReply(MultipartFile file, int blogID, int width, int height)
	{
		try
		{
			var rep = replyRepository.getById(blogID);
			Map<String, String> data = this.getCloudinary().uploader().upload(file.getBytes(), Map.of());
			var f = new File(data.get("url"), data.get("public_id"), rep);
			f.setHeight(height);
			f.setWidth(width);
			fileRepository.save(f);
			rep.getFiles().add(f);
			replyRepository.save(rep);
		}        
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
}
