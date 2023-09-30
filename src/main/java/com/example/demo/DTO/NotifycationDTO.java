package com.example.demo.DTO;

import java.util.Date;

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
    private Date dateSent;
    private GroupStudyingDTO group;
    
    public NotifycationDTO(Notifycation notifycation)
    {
    	this.notifycationID = notifycation.getNotifycationID();
    	this.Header = notifycation.getHeader();
    	this.Content = notifycation.getContent();
    	this.dateSent = notifycation.getDateSent();

        if (notifycation.getGroupStudying() != null) {
        	this.group = new GroupStudyingDTO(notifycation.getGroupStudying());
        }
    }
}
