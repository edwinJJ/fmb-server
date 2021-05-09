package com.server.fmb.db;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.fmb.entity.Connections;

public interface IConnectionQueryManager extends JpaRepository<Connections, UUID> {

	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM connections u WHERE u.name = ?1 ", nativeQuery = true)
	public void deleteConnectionByName(@Param("name") String name);
}
