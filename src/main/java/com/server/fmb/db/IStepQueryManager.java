package com.server.fmb.db;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.fmb.entity.Attachments;
import com.server.fmb.entity.Steps;

public interface IStepQueryManager extends JpaRepository<Steps, UUID>{

	
	@Query(value = "SELECT u.* FROM steps u WHERE u.scenario_id = ?1  ORDER BY u.sequence DESC", nativeQuery = true)
	public List<Steps> getStepsByScenarioId(@Param("scenarioId") UUID scenarioId);
}
