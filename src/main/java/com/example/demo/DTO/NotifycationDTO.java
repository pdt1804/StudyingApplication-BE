package com.example.demo.DTO;

import java.util.Date;
import java.util.List;

import com.example.demo.entities.File;
import com.example.demo.entities.GroupStudying;
import com.example.demo.entities.Notifycation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotifycationDTO {

	private int notifycationID;
	private String Header;
	private String Content;
	private List<File> files;
    private Date dateSent;
    private GroupStudyingDTO group;
    private int documentID;
    private long blogID;
    
    public NotifycationDTO(Notifycation notifycation)
    {
    	this.notifycationID = notifycation.getNotifycationID();
    	this.Header = notifycation.getHeader();
    	this.Content = notifycation.getContent();
    	this.dateSent = notifycation.getDateSent();
    	this.files = notifycation.getFiles();

        if (notifycation.getGroupStudying() != null) {
        	this.group = new GroupStudyingDTO(notifycation.getGroupStudying());
        }
        
        if (notifycation.getContent().contains("mã thảo luận là "))
        {
        	blogID = extractDiscussionNumber(Content);
        	documentID = -1;
        }
        else if (notifycation.getContent().contains("mã tài liệu là "))
        {
        	documentID = extractDocumentNumber(Content);
        	blogID = -1;
        }
        else 
        {
        	documentID = -1;
        	blogID = -1;
        }
    }
    
    private long extractDiscussionNumber(String input) {
        int startIndex = input.indexOf("mã thảo luận là");

        if (startIndex != -1) {
            int commaIndex = input.indexOf(",", startIndex);

            if (commaIndex != -1) {
                String numberString = input.substring(startIndex + "mã thảo luận là".length(), commaIndex).trim();

                try {
                    return Long.parseLong(numberString);
                } catch (NumberFormatException e) {
                    System.out.println("Không thể chuyển đổi chuỗi thành số nguyên.");
                }
            } else {
                System.out.println("Không tìm thấy dấu phẩy sau 'mã thảo luận là'.");
            }
        }
        
        return -1;
    }
    
    private int extractDocumentNumber(String input) {
        int startIndex = input.indexOf("mã tài liệu là");

        if (startIndex != -1) {
            int commaIndex = input.indexOf(",", startIndex);

            if (commaIndex != -1) {
                String numberString = input.substring(startIndex + "mã tài liệu là".length(), commaIndex).trim();

                try {
                    return Integer.parseInt(numberString);
                } catch (NumberFormatException e) {
                    System.out.println("Không thể chuyển đổi chuỗi thành số nguyên.");
                }
            } else {
                System.out.println("Không tìm thấy dấu phẩy sau 'mã thảo luận là'.");
            }
        }

        return -1;
    }
}
