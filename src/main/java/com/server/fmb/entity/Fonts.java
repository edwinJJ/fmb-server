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
import javax.persistence.Table;

@Entity
@Table(name = "FMB_FONTS")
public class Fonts {


	@Id
	private String id;
//	@Id
//	@GeneratedValue(generator = "UUID")
//	private UUID id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "provider")
	private String provider;
	
	@Column(name = "uri")
	private String uri;
	
	@Column(name = "path")
	private String path;
	
	@Column(name = "active")
	private Boolean active;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@Column(name = "domain_id")
	private String domainId;
//	private UUID domainId;
	
	@Column(name = "creator_id")
	private String creatorId;
//	private UUID CreatorId;
	
	@Column(name = "updater_id")
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
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
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
