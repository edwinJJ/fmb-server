/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:   	IDomainQueryManager.java
 *  Description:  	Domains 데이터베이스 테이블 Query에 관한 Interface 정의 모듈
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.db;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.fmb.entity.Domains;

public interface IDomainQueryManager extends JpaRepository<Domains, String>  {
//public interface IDomainQueryManager extends JpaRepository<Domains, UUID>  {

}
