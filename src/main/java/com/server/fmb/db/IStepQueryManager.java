package com.server.fmb.db;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.fmb.entity.Steps;

@Repository
public interface IStepQueryManager extends JpaRepository<Steps, String>{
//public interface IStepQueryManager extends JpaRepository<Steps, UUID>{

	
	@Query(value = "SELECT u.* FROM FMB_STEPS u WHERE u.scenario_id = ?1  ORDER BY u.sequence ASC", nativeQuery = true)
	public List<Steps> getStepsByScenarioId(@Param("scenarioId") String scenarioId);
//	public List<Steps> getStepsByScenarioId(@Param("scenarioId") UUID scenarioId);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM FMB_STEPS u WHERE u.id in (?1) ", nativeQuery = true)
	public void deleteStepById(@Param("ids") List<String> ids);
}
