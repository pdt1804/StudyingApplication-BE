package com.example.demo.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.entities.GroupStudying;
import com.example.demo.entities.Topic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupStudyingDTO {

	private int groupID;
	private String nameGroup;
	private Date dateCreated;
	private UserDTO leaderOfGroup;
	private String image;
	private int numberOfMembers;
	private String passWord;
	private List<Topic> topics = new ArrayList<>();
	
    public GroupStudyingDTO() {
    	
    }

    public GroupStudyingDTO(GroupStudying groupStudying) {
        this.groupID = groupStudying.getGroupID();
        this.nameGroup = groupStudying.getNameGroup();
        this.dateCreated = groupStudying.getDateCreated();
        this.image = groupStudying.getImageGroup();
        this.numberOfMembers = groupStudying.getUsers().size();
        this.passWord = groupStudying.getPassWord();
        
        if (groupStudying.getLeaderOfGroup() != null) {
            this.leaderOfGroup = new UserDTO(groupStudying.getLeaderOfGroup());
        }
        
        if (groupStudying.getTopics() != null)
        {
        	this.topics = groupStudying.getTopics();
        }
    }

}