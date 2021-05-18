/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:   	IUserQueryManager.java
 *  Description:  	Users 데이터베이스 테이블 Query에 관한 Interface 정의 모듈
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.db;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.fmb.entity.Users;

public interface IUserQueryManager extends JpaRepository<Users, String> {
//public interface IUserQueryManager extends JpaRepository<Users, UUID> {
	
}
