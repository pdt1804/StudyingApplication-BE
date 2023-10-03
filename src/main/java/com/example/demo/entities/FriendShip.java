package com.example.demo.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table
@Builder
@Entity
@AllArgsConstructor
public class FriendShip {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int FriendShip_ID;

    @ManyToOne
    @JoinColumn(name = "sentUser_UserName", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "friendUser_UserName", nullable = false)
    private User friend;
    
    private Date sentTime;
    private FriendShipStatus status;
    private Date lastTimeEdited;
    
    public FriendShip()
    {
    	
    }
    
}
