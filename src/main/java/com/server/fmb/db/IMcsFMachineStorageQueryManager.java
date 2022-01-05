package com.server.fmb.db;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.fmb.entity.McsFMachineStorage;

@Repository
public interface IMcsFMachineStorageQueryManager extends JpaRepository<McsFMachineStorage, Object>  {
	
	@Query(value = "SELECT u.localename FROM MCS_F_MACHINE_STORAGE u WHERE u.id = ?1", nativeQuery = true)
	public String getMachineStorageName(@Param("id") String id);

	@Query(value = "SELECT " + 
			"machine.id" + 
			",machine.state as state " + 
			",machine.tscState as scState " + 
			",machine.maxCapacity as maxCapacity " + 
			",carrierCount.full as fullCarrier " + 
			",carrierCount.empty as emptyCarrier " + 
			",carrierCount.emptyEmpty as emptyEmptyCarrier " + 
			",carrierCount.unkCarrier as unkCarrier " + 
			",ROUND((machine.currentcapacity/machine.maxCapacity)*100,2) AS fullRate " + 
			",(SELECT COUNT(*) FROM MCS_T_TRANSPORT_JOB_INF WHERE DESTMACHINENAME = ?1) AS reservedCarrier " + 
			",(SELECT COUNT(*) FROM MCS_T_TRANSPORT_CMD_INF WHERE TRANSPORTMACHINENAME = ?1) AS transferCommand " + 
			",(SELECT PROCESSTYPENAME FROM MCS_T_MACHINE_PROCESSTYPE_MST WHERE MACHINENAME = ?1) AS processType " + 
			"FROM  " + 
			" MCS_R_MACHINE_MST machine,  " + 
			" MCS_F_MACHINE_STORAGE fmbMachine, " + 
			" (SELECT  " + 
			" COUNT(CASE WHEN (carrier.EMPTYTYPE = 'Empty') THEN 1 ELSE NULL END) as empty " + 
			" ,COUNT(CASE WHEN (carrier.EMPTYTYPE = 'EmptyEmpty') THEN 1 ELSE NULL END) as emptyEmpty " + 
			" ,COUNT(CASE WHEN (carrier.EMPTYTYPE = 'Full') THEN 1 ELSE NULL END) as full " + 
			" ,COUNT(CASE WHEN (carrier.ID LIKE 'UNK') THEN 1 ELSE NULL END) as unkCarrier  " + 
			"   FROM MCS_F_CARRIER carrier " + 
			"   WHERE 1 = 1  " + 
			"   AND MACHINENAME = ?1) carrierCount " + 
			"WHERE 1 = 1 " + 
			"AND machine.ID = ?1  " + 
			"AND machine.ID = fmbMachine.ID ", nativeQuery = true)
	public Map<String, String> getStockerPopupData(@Param("id") String id);
}
