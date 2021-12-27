package com.server.fmb.service;

public interface IEqpService {
	public String getEqpNamesByIdType(String id, String type) throws Exception;
	public Object getStockerPopupData(String id, String empty, String unknownRule) throws Exception;
	public Object getStockerEqpPortPopupData(String name) throws Exception;
}
