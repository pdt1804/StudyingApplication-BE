package com.example.demo.DTO;

import java.util.Date;

import com.example.demo.entities.GroupStudying;

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
    }

}