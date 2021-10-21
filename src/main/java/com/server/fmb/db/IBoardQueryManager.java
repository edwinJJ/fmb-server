/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:   	IBoardQueryManager.java
 *  Description:  	Boards 데이터베이스 테이블 Query에 관한 Interface 정의 모듈
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.fmb.entity.Boards;


@Repository
public interface IBoardQueryManager extends JpaRepository<Boards, String> {
//public interface IBoardQueryManager extends JpaRepository<Boards, UUID> {
	
	@Query(value = "SELECT u.* FROM FMB_BOARDS u WHERE u.id = ?1 AND u.use_mcs_auto_config = ?2", nativeQuery = true)
	public Boards getBoardByUseMcsAutoConfigAndId(@Param("id") String id, @Param("useMcsAutoConfig") boolean useMcsAutoConfig);

	@Query(value = "SELECT u.* FROM FMB_BOARDS u ORDER BY u.name DESC", nativeQuery = true)
	public List<Boards> getBoards(@Param("start") int start, @Param("end") int end);
//	@Query(value = "SELECT u.* FROM boards u ORDER BY u.name DESC OFFSET ?1 LIMIT ?2", nativeQuery = true)
//	public List<Boards> getBoards(@Param("start") int start, @Param("end") int end);
	
	@Query(value = "SELECT t.* FROM FMB_BOARDS t WHERE t.id IN (?1)", nativeQuery = true)
	public List<Boards> getBoardsByRoutingIds(@Param("routingIds") List<String> routingIds);
//	@Query(value = "SELECT t.* FROM boards t WHERE t.id IN (?1)", nativeQuery = true)
//	public List<Boards> getBoardsByRoutingIds(@Param("routingIds") List<UUID> routingIds);
	
	@Query(value = "SELECT t.* FROM FMB_BOARDS t WHERE t.group_id = ?1", nativeQuery = true)
	public List<Boards> getBoardsByGroupId(@Param("groupId") String groupId);
//	public List<Boards> getBoardsByGroupId(@Param("groupId") UUID groupId);
	
}
