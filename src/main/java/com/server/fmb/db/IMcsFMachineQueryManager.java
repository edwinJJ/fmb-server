package com.server.fmb.db;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.fmb.entity.McsFMachine;

@Repository
public interface IMcsFMachineQueryManager extends JpaRepository<McsFMachine, Object> {

	@Query(value = "SELECT u.localename FROM MCS_F_MACHINE u WHERE u.id = ?1", nativeQuery = true)
	public String getMachineName(@Param("id") String id);
	
	@Query(value = "SELECT " + 
			"unit.id " + 
			",unit.machineName " + 
			",unit.name " + 
			",unit.state          as state " + 
			",unit.subState      as subState " + 
			",unit.banned        as banned " + 
			",unit.type          as type " + 
			",unit.inOutType     as inOutType " + 
			",unit.manualFlag    as manual " + 
			",unit.reserved      as reserved " + 
			",unit.occupied      as occupied " + 
			",unit.accessMode    as accessMode " + 
			",carrier.name       as carrier " + 
			",carrier.state      as carrierState " + 
			",unitProcessType.processTypeName    as processType " + 
			"FROM  " + 
			" mcs.MCS_R_UNIT_MST unit,  " + 
			" mcs.MCS_M_CARRIER_MST carrier, " + 
			" mcs.MCS_T_UNIT_PROCESSTYPE_MST unitProcessType " + 
			"WHERE 1 = 1 " + 
			"AND unit.name = ?1  " + 
			"AND unit.name = carrier.unitName(+)  " + 
			"AND unit.name = unitProcessType.unitName(+)", nativeQuery = true)
	public Map<String, String> getStockerEqpPortPopupData(@Param("name") String name);
}
