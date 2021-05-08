/*
 *  Copyright (c) 2020 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    IFileAbstract.java
 *  Description:  	파일관리를 위한 추상 Interface 정의 모듈
 *  Authors:        Y.S. Jung
 *  Update History:
 *                  2020.03.14 : Created by Y.S. Jung
 *
 */
package com.server.fmb.service;

/**
 * 
 * @author kmyu
 *
 */
public interface IFileAbstract {

	/**
	 * File 저장 폴더를 나누는 기준값을 리턴하는 구현체 필요 (ex : companyId, projectId)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getRootDivision() throws Exception;

	/**
	 * File 저장 폴더경로를 리턴한다. 
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getRepositoryPath();

	/**
	 * 이미지 서버의 Context를 리턴한다.. 
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getImageServerContext();
	
	/**
	 * 서버의 URL을 리턴한다.. 
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getServerUrl();

	/**
	 * 이미지 서버의 URL을 리턴한다.. 
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getImageServerUrl();

	/**
	 * Temps 이미지 서버의 URL을 리턴한다.. 
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getImageServerTempsUrl();

	public String getImageServerProfileUrl();
	
	public boolean isGcsEnabled();
}
