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
@Table(name = "FMB_SCENARIOS")
public class Scenarios {


	@Id
	private String id;
//	@Id
//	@GeneratedValue(generator = "UUID")
//	private UUID id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "active")
	private Boolean active;
	
	@Column(name = "fmb_key")
	private String fmbKey;
	
	@Column(name = "schedule")
	private String schedule;
	
	@Column(name = "timezone")
	private String timezone;
	
	@Column(name = "created_at")
	private Date createdAt; 
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@Column(name = "creator_id")
	private String creatorId;
//	private UUID creatorId;
	
	@Column(name = "updater_id")
	private String updaterId;
//	private UUID updaterId;

	@Column(name = "domain_id")
	private String domainId;
//	private UUID domainId;
	
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

	public String getFmbKey() {
		return fmbKey;
	}

	public void setFmbKey(String fmbKey) {
		this.fmbKey = fmbKey;
	}
	
	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
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

	
	
}
