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
public class Attachments {

	
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private String mimetype;
	
	@Column
	private String encoding;
	
	@Column
	private String category;

	@Column
	private String refBy;
	
	@Column
	private String path;
	
	@Column
	private String size;
	
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

	public UUID getDomainId() {
		return domainId;
	}

	public void setDomainId(UUID domainId) {
		this.domainId = domainId;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getRefBy() {
		return refBy;
	}

	public void setRefBy(String refBy) {
		this.refBy = refBy;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	
	
}
