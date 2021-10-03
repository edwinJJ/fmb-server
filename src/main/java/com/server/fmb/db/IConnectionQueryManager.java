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

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.fmb.entity.Connections;

@Repository
public interface IConnectionQueryManager extends JpaRepository<Connections, String> {
//public interface IConnectionQueryManager extends JpaRepository<Connections, UUID> {

	@Query(value = "SELECT u.* FROM FMB_CONNECTIONS u ORDER BY u.name DESC", nativeQuery = true)
	public List<Connections> getConnections();
	
	@Query(value = "SELECT u.* FROM FMB_CONNECTIONS u WHERE u.active=?1", nativeQuery = true)
	public List<Connections> getConnectionsByActive(@Param("active") Integer active);
	
	@Query(value = "SELECT u.* FROM FMB_CONNECTIONS u WHERE u.name=?1", nativeQuery = true)
	public Connections getConnectionByName(@Param("name") String name);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM FMB_CONNECTIONS u WHERE u.name in (?1) ", nativeQuery = true)
	public void deleteConnectionByName(@Param("names") List<String> names);
}
