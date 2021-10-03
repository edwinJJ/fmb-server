/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:   	IAttachmentQueryManager.java
 *  Description:  	Attachments 데이터베이스 테이블 Query에 관한 Interface 정의 모듈
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

import com.server.fmb.entity.Attachments;


@Repository
public interface IAttachmentQueryManager extends JpaRepository<Attachments, String> {
//public interface IAttachmentQueryManager extends JpaRepository<Attachments, UUID> {

	@Query(value = "SELECT u.* FROM FMB_ATTACHMENTS u ORDER BY u.name DESC", nativeQuery = true)
	public List<Attachments> getAttachments(@Param("start") int start, @Param("end") int end);
//	@Query(value = "SELECT u.* FROM attachments u ORDER BY u.name DESC OFFSET ?1 LIMIT ?2", nativeQuery = true)
//	public List<Attachments> getAttachments(@Param("start") int start, @Param("end") int end);
	
	@Query(value = "SELECT u.* FROM FMB_ATTACHMENTS u WHERE u.category = ?1  ORDER BY u.name DESC", nativeQuery = true)
	public List<Attachments> getAttachmentsByCategory(@Param("category") String category,@Param("start") int start, @Param("end") int end);
//	@Query(value = "SELECT u.* FROM attachments u WHERE u.category = ?1  ORDER BY u.name DESC OFFSET ?2 LIMIT ?3", nativeQuery = true)
//	public List<Attachments> getAttachmentsByCategory(@Param("category") String category,@Param("start") int start, @Param("end") int end);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM FMB_ATTACHMENTS u WHERE u.ref_by = ?1 ", nativeQuery = true)
	public void deleteAttachmentByRef(@Param("refBy") String refBy);


}
