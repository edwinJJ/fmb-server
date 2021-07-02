/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    Connctions.java
 *  Description:  	Connections 관련 테이블 정의 
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
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Connections {
	
	@Id
	private String id;
//	@Id
//	@GeneratedValue(generator = "UUID")
//	private UUID id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private String type;
	
	@Column
	private String endpoint;
	
	@Column
	private Boolean active;
	
	@Column
	private String params;
	
	@Column
	private Date createdAt;
	
	@Column
	private Date updatedAt;
	
	@Column
	private String domainId;
//	private UUID domainId;
	
	@Column
	private String creatorId;
//	private UUID creatorId;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
//	public Integer getActive() {
//		return active;
//	}
//
//	public void setActive(Integer active) {
//		this.active = active;
//	}
	
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
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
//		return creatorId;
//	}
//
//	public void setCreatorId(UUID creatorId) {
//		this.creatorId = creatorId;
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
