package com.example.demo.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;
	@Lob
	@Column(columnDefinition = "TEXT")
	private String Content;
	private Date dateSent;
	private MessageUserStatus status;
    @ManyToOne
    @JoinColumn(name = "sent_username")
    @JsonIgnore
	private User sentUser;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "received_username")
	private User receivedUser;
    @OneToMany(mappedBy = "messageUser", cascade = CascadeType.ALL)
	private List<File> files = new ArrayList<>();
}
