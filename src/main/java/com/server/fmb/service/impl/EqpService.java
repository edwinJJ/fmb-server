package com.server.fmb.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.constant.Constant;
import com.server.fmb.service.IEqpService;
import com.server.fmb.util.EnvUtil;

@Service
public class EqpService implements IEqpService {
	
	@Autowired
	EnvUtil envUtil;

	@Override
	public String getEqpNamesByIdType(String id, String type) throws Exception {
		String scDbDriver = envUtil.getProperty(Constant.SECOND_SERVER_DRIVER);
		String scDbUrl = envUtil.getProperty(Constant.SECOND_SERVER_URL);
		String scDbuser = envUtil.getProperty(Constant.SECOND_SERVER_USER);
		String scDbPw = envUtil.getProperty(Constant.SECOND_SERVER_PW);
		
		Class.forName(scDbDriver);
    	Connection dbConnection = DriverManager.getConnection(scDbUrl, scDbuser, scDbPw);
    	Statement stmt = dbConnection.createStatement();
    	String query = "";
    	if (type.equals(Constant.STOCKER)) {
    		query += "select localename from MCS_F_MACHINE_STORAGE where id='" + id + "'";
    	} else if (type.equals(Constant.EQP)) {
    		query += "select localename from MCS_F_MACHINE where id='" + id +"'";
    	}
		ResultSet rs = stmt.executeQuery(query);
		String eqpName = "";
		while (rs.next()) {
			eqpName = rs.getString(1);
        }
		return eqpName;
	}

}
