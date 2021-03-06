/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:   	IGroupQueryManager.java
 *  Description:  	Groups 데이터베이스 테이블 Query에 관한 Interface 정의 모듈
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.server.fmb.entity.Groups;

@Repository
public interface IGroupQueryManager extends JpaRepository<Groups, String> {
//public interface IGroupQueryManager extends JpaRepository<Groups, UUID> {
	
	@Query(value = "SELECT g.* FROM FMB_GROUPS g", nativeQuery = true)
	public List<Groups> fetchGroupList();
	
	@Query(value = "SELECT g.* FROM FMB_PLAY_GROUPS g", nativeQuery = true)
	public List<Groups> fetchPlayGroupList();
	
}
