package com.example.demo.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Notifycation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notifycation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int notifycationID;
	private String Header;
    @Column(columnDefinition = "TEXT")
	private String Content;
    private Date dateSent;
    private NotifycationType notifycationType;
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = true)
    @JsonIgnore
    private GroupStudying groupStudying;
    @ManyToMany(mappedBy = "notifycations")
    @Column(nullable = true)
    @JsonIgnore
    private List<User> users = new ArrayList<>();
    @OneToMany
    @JsonIgnore
    @JoinColumn(name="username_notifycation", nullable = true)
    private List<User> userSeenNotifycation = new ArrayList<>();
	
}
