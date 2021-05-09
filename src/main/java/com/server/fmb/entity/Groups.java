/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    Groups.java
 *  Description:  	Groups 관련 테이블 정의 
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Groups {
	
	@Id
	@GeneratedValue(generator = "UUID")
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
	private UUID domainId;
	
	@Column
	private UUID creatorId;
	
	@Column
	private UUID updaterId;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public UUID getDomainId() {
		return domainId;
	}
	public void setDomainId(UUID domainId) {
		this.domainId = domainId;
	}
	public UUID getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(UUID creatorId) {
		this.creatorId = creatorId;
	}
	public UUID getUpdaterId() {
		return updaterId;
	}
	public void setUpdaterId(UUID updaterId) {
		this.updaterId = updaterId;
	}
	
}
