/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:   	IFavoriteQueryManager.java
 *  Description:  	Favorites 데이터베이스 테이블 Query에 관한 Interface 정의 모듈
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.db;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.fmb.entity.Favorites;

@Repository
public interface IFavoriteQueryManager extends JpaRepository<Favorites, UUID> {
	
	@Modifying
	@Query(value = "SELECT u.* FROM FMB_FAVORITES u", nativeQuery = true)
	public List<Favorites> getAllFavorites();

	@Modifying
	@Query(value = "DELETE FROM FMB_FAVORITES t WHERE t.routing = ?1", nativeQuery = true)
	public void deleteByRoutingId(@Param("boardId") String boardId);
	
}
