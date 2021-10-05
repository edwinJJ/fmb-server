package com.server.fmb.db;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.fmb.entity.Scenarios;

@Repository
public interface IScenarioQueryManager  extends JpaRepository<Scenarios , String> {
//public interface IScenarioQueryManager  extends JpaRepository<Scenarios , UUID> {
	
	@Query(value = "SELECT u.* FROM FMB_SCENARIOS u WHERE u.id=?1", nativeQuery = true)
	public Scenarios getScenarioStepById(@Param("id") String id);

	@Query(value = "SELECT u.* FROM FMB_SCENARIOS u", nativeQuery = true)
	public List<Scenarios> getAllScenarios();
	
	@Query(value = "SELECT u.* FROM FMB_SCENARIOS u WHERE u.active=?1", nativeQuery = true)
	public List<Scenarios> getScenariosByActive(@Param("active") Integer active);
	
	@Query(value = "SELECT u.* FROM FMB_SCENARIOS u WHERE u.name=?1", nativeQuery = true)
	public Scenarios getScenariosByName(@Param("scenarioName") String scenarioName);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM FMB_SCENARIOS u WHERE u.id in (?1) ", nativeQuery = true)
	public void deleteScenarioById(@Param("ids") List<String> ids);
	
}
