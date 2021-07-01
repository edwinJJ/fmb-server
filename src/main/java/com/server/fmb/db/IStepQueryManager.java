package com.server.fmb.db;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.fmb.entity.Attachments;
import com.server.fmb.entity.Steps;

public interface IStepQueryManager extends JpaRepository<Steps, String>{
//public interface IStepQueryManager extends JpaRepository<Steps, UUID>{

	
	@Query(value = "SELECT u.* FROM steps u WHERE u.scenario_id = ?1  ORDER BY u.sequence ASC", nativeQuery = true)
	public List<Steps> getStepsByScenarioId(@Param("scenarioId") String scenarioId);
//	public List<Steps> getStepsByScenarioId(@Param("scenarioId") UUID scenarioId);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM steps u WHERE u.id in (?1) ", nativeQuery = true)
	public void deleteStepById(@Param("ids") List<String> ids);
}
