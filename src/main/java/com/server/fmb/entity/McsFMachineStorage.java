package com.server.fmb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MCS_F_MACHINE_STORAGE")
public class McsFMachineStorage {
	
	@Id
	private String id;
	
	@Column(name = "localename")
	private String localeName;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "maxcapacity")
	private int maxCapacity;
	
	@Column(name = "highwatermark")
	private int highWaterMark;
	
	@Column(name = "lowwatermark")
	private int lowWaterMark;
	
	@Column(name = "currentcapacity")
	private int currentCapacity;
	
	@Column(name = "capacitybyemptytype")
	private String capacityByEmptyType;
	
	@Column(name = "edittime")
	private Date editTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLocaleName() {
		return localeName;
	}

	public void setLocaleName(String localeName) {
		this.localeName = localeName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public int getHighWaterMark() {
		return highWaterMark;
	}

	public void setHighWaterMark(int highWaterMark) {
		this.highWaterMark = highWaterMark;
	}

	public int getLowWaterMark() {
		return lowWaterMark;
	}

	public void setLowWaterMark(int lowWaterMark) {
		this.lowWaterMark = lowWaterMark;
	}

	public int getCurrentCapacity() {
		return currentCapacity;
	}

	public void setCurrentCapacity(int currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	public String getCapacityByEmptyType() {
		return capacityByEmptyType;
	}

	public void setCapacityByEmptyType(String capacityByEmptyType) {
		this.capacityByEmptyType = capacityByEmptyType;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}
	
}
