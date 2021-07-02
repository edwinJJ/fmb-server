package com.server.fmb.db;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.fmb.entity.Connections;
import com.server.fmb.entity.Scenarios;

public interface IScenarioQueryManager  extends JpaRepository<Scenarios , String> {
//public interface IScenarioQueryManager  extends JpaRepository<Scenarios , UUID> {

	
	@Query(value = "SELECT u.* FROM scenarios u WHERE u.active=?1", nativeQuery = true)
	public List<Scenarios> getScenariosByActive(@Param("active") Integer active);
	
	@Query(value = "SELECT u.* FROM scenarios u WHERE u.name=?1", nativeQuery = true)
	public Scenarios getScenariosByName(@Param("scenarioName") String scenarioName);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM scenarios u WHERE u.id in (?1) ", nativeQuery = true)
	public void deleteScenarioById(@Param("ids") List<String> ids);
	
}
