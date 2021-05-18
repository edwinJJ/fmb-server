/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    Fonts.java
 *  Description:  	Fonts 관련 테이블 정의 
 *  Authors:        J.H. Lee
 *  Update History:
 *                  2021.05.07 : Created by J.H. Lee
 *
 */
package com.server.fmb.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Fonts {


	@Id
	private String id;
//	@Id
//	@GeneratedValue(generator = "UUID")
//	private UUID id;
	
	@Column
	private String name;
	
	@Column
	private String provider;
	
	@Column
	private String uri;
	
	@Column
	private String path;
	
	@Column
	private Boolean active;
	
	@Column
	private Date createdAt;
	
	@Column
	private Date updatedAt;
	
	@Column
	private String domainId;
//	private UUID domainId;
	
	@Column
	private String CreatorId;
//	private UUID CreatorId;
	
	@Column
	private String updaterId;
//	private UUID updaterId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
//	public UUID getId() {
//		return id;
//	}
//
//	public void setId(UUID id) {
//		this.id = id;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
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

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
//	public UUID getDomainId() {
//		return domainId;
//	}
//
//	public void setDomainId(UUID domainId) {
//		this.domainId = domainId;
//	}

	public String getCreatorId() {
		return CreatorId;
	}

	public void setCreatorId(String creatorId) {
		CreatorId = creatorId;
	}
//	public UUID getCreatorId() {
//		return CreatorId;
//	}
//
//	public void setCreatorId(UUID creatorId) {
//		CreatorId = creatorId;
//	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}
//	public UUID getUpdaterId() {
//		return updaterId;
//	}
//
//	public void setUpdaterId(UUID updaterId) {
//		this.updaterId = updaterId;
//	}
	
	
}
