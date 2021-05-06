package com.server.fmb.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;



@Entity
public class Attachment {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Type(type="uuid-char")
	private UUID id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private Date createdAt; 
	
	@Column
	private Date updatedAt;
	
	@Column
	private UUID creatorId;
	
	@Column
	private UUID updaterId;

	@Column
	private UUID domainId;
	
	@Column
	private String mimetype;
	
	@Column
	private String category;
	
	@Column
	private String encoding;
	
	@Column
	private String refBy;
	
	@Column
	private String path;
	
	@Column
	private String size;
	
	
	
}
