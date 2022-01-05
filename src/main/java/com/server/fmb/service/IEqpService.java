package com.server.fmb.service;

import java.util.Map;

public interface IEqpService {
	public String getEqpNamesByIdType(String id, String type) throws Exception;
	public Map<String, String> getStockerPopupData(String id) throws Exception;
	public Map<String, String> getStockerEqpPortPopupData(String name) throws Exception;
}
