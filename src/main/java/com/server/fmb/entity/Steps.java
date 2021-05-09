package com.server.fmb.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Steps {

	
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column(name = "sequence" , nullable = false)
	private int sequence;
	
	@Column(name = "task" , nullable = false)
	private String task;
	
	@Column
	private Boolean skip;
	
	@Column
	private Boolean log;
	
	@Column
	private int connection;
	
	@Column
	private String params;
	
	@Column
	private Date createdAt; 
	
	@Column
	private Date updatedAt;
	
	@Column
	private UUID creatorId;
	
	@Column
	private UUID scenarioId;

	@Column
	private UUID domainId;
}
