package com.server.fmb.db;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.fmb.entity.Scenarios;

public interface IScenarioQueryManager  extends JpaRepository<Scenarios , UUID> {

	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM scenarios u WHERE u.id = ?1 ", nativeQuery = true)
	public void deleteScenarioById(@Param("id") String id);
}
