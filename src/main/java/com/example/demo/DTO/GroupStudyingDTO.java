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
	
    public GroupStudyingDTO() {
    	
    }

    public GroupStudyingDTO(GroupStudying groupStudying) {
        this.groupID = groupStudying.getGroupID();
        this.nameGroup = groupStudying.getNameGroup();
        this.dateCreated = groupStudying.getDateCreated();

        if (groupStudying.getLeaderOfGroup() != null) {
            this.leaderOfGroup = new UserDTO(groupStudying.getLeaderOfGroup());
        }
    }

}