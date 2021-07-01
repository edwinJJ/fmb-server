/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:   	IConnectionQueryManager.java
 *  Description:  	Connections 데이터베이스 테이블 Query에 관한 Interface 정의 모듈
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.db;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.fmb.entity.Boards;
import com.server.fmb.entity.Connections;

public interface IConnectionQueryManager extends JpaRepository<Connections, String> {
//public interface IConnectionQueryManager extends JpaRepository<Connections, UUID> {

	@Query(value = "SELECT u.* FROM connections u ORDER BY u.name DESC", nativeQuery = true)
	public List<Connections> getConnections();
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM connections u WHERE u.name in (?1) ", nativeQuery = true)
	public void deleteConnectionByName(@Param("names") List<String> names);
}
