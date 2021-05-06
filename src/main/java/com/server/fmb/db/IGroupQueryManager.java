package com.server.fmb.db;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.server.fmb.entity.Groups;

public interface IGroupQueryManager extends JpaRepository<Groups, UUID> {
	
	@Query(value = "SELECT g.* FROM play_groups g", nativeQuery = true)
	public List<Groups> fetchPlayGroupList();
}
