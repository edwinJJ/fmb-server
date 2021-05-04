package com.server.fmb.entity;

import java.util.Date;

import javax.persistence.Column;

public class Common {
	
	@Column
	private Date createdAt; 
	
	@Column
	private Date updatedAt;
}
