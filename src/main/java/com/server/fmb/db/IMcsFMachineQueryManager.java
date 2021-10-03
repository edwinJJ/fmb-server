package com.server.fmb.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.fmb.entity.McsFMachine;

@Repository
public interface IMcsFMachineQueryManager extends JpaRepository<McsFMachine, String> {

	@Query(value = "SELECT u.localename FROM MCS_F_MACHINE u WHERE u.id = ?1", nativeQuery = true)
	public String getMachineName(@Param("id") String id);
	
}
