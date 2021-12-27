package com.server.fmb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.constant.Constant;
import com.server.fmb.db.IMcsFMachineQueryManager;
import com.server.fmb.db.IMcsFMachineStorageQueryManager;
import com.server.fmb.service.IEqpService;
import com.server.fmb.util.EnvUtil;

@Service
public class EqpService implements IEqpService {
	
	@Autowired
	IMcsFMachineQueryManager iMcsFMachineQueryManager;
	
	@Autowired
	IMcsFMachineStorageQueryManager iMcsFMachineStorageQueryManager;
	
	@Autowired
	EnvUtil envUtil;

	@Override
	public String getEqpNamesByIdType(String id, String type) throws Exception {
    	String eqpName = "";
    	if (type.equals(Constant.STOCKER)) {
    		eqpName = iMcsFMachineStorageQueryManager.getMachineStorageName(id);
    	} else if (type.equals(Constant.PROCESS)) {
    		eqpName = iMcsFMachineQueryManager.getMachineName(id);
    	}
		return eqpName;
	}

	@Override
	public Object getStockerPopupData(String id, String empty, String unknownRule) throws Exception {
		return iMcsFMachineStorageQueryManager.getStockerPopupData(id, empty, unknownRule);
	}
	
	@Override
	public Object getStockerEqpPortPopupData(String name) throws Exception {
		return iMcsFMachineQueryManager.getStockerEqpPortPopupData(name);
	}
}
