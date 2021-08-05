/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    AttachmentService.java
 *  Description:  	Attachment 관련 서비스 Interface Impl
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.server.fmb.constant.Constant;
import com.server.fmb.db.IAttachmentQueryManager;
import com.server.fmb.entity.Attachments;
import com.server.fmb.entity.FileModel;
import com.server.fmb.service.IAttachmentService;
import com.server.fmb.service.IDomainService;
import com.server.fmb.service.IFileAbstract;
import com.server.fmb.service.IUserService;
import com.server.fmb.util.EnvUtil;
import com.server.fmb.util.FileUtil;
import com.server.fmb.util.FormatUtil;
import com.server.fmb.util.IdUtil;
import com.server.fmb.util.RestUtil;
import com.server.fmb.util.ValueUtil;
import com.server.fmb.websocket.UIWebsocketManager;

@Service
public class AttachmentService implements IAttachmentService {
	
	public static final String FILE_DIVISION_TEMPS = "Temps";
    public static final String FILE_DIVISION_IMAGES = "Images";
    public static final String FILE_DIVISION_FILES = "Files";
    public static final String FILE_DIVISION_PROFILES = "Profiles";

	
	@Autowired
	IAttachmentQueryManager attachmentQueryManager;

	@Autowired
	IDomainService domainService;
	
	@Autowired
	IUserService userService;

	@Autowired
	EnvUtil envUtil;
	
	@Autowired
	IFileAbstract fileConfiguration;
	
	
	@Override
	public List<Attachments> getAttachments(int start, int end) throws Exception{
		if(start == 0  && end ==0) {
			return attachmentQueryManager.findAll();
		}
		return attachmentQueryManager.getAttachments(start, end);
	}
	@Override
	public List<Attachments> getAttachmentsByCategory(String category,int start, int end) throws Exception{
		return attachmentQueryManager.getAttachmentsByCategory(category,start, end);
	}
	
	
	@Override
	public Attachments setAttachments(Map<String, String> attachmentMap) throws Exception{
		Attachments attachment = new Attachments();
		
		if(ValueUtil.isNotEmpty(attachmentMap.get("id"))){
			attachment = fetchAttachmentsById(attachmentMap.get("id"));
			return attachmentQueryManager.save(attachment);
		}
		else {
			attachment.setId(IdUtil.getUUIDString());
//			attachment.setId(IdUtil.getUUID());
			attachment.setCategory(attachmentMap.get("category"));
			attachment.setName(attachmentMap.get("name"));
			attachment.setDescription(attachmentMap.get("description"));
			attachment.setMimetype(attachmentMap.get("type"));
			attachment.setEncoding(envUtil.getProperty(Constant.ENCODING));
			attachment.setRefBy(attachmentMap.get("refBy"));
			attachment.setPath(attachmentMap.get("fileId"));
			attachment.setBulk(attachmentMap.get("size"));
//			attachment.setSize(attachmentMap.get("size"));
			attachment.setUpdatedAt(new Date());
			attachment.setCreatedAt(new Date());
			attachment.setDomainId(domainService.getDomain().getId());
			attachment.setUpdaterId(attachmentMap.get("userName"));
			attachment.setCreatorId(attachmentMap.get("userName"));
			return attachmentQueryManager.save(attachment);
		}
		
	}
	
	@Override
	public Attachments deleteAttachments(String attachmentId) throws Exception{
		Attachments attachment = fetchAttachmentsById(attachmentId);
		attachmentQueryManager.deleteById(attachmentId);
//		attachmentQueryManager.deleteById(UUID.fromString(attachmentId));
		return attachment;
	}
	@Override
	public void deleteAttachmentByRef(String ref) throws Exception{
		attachmentQueryManager.deleteAttachmentByRef(ref);
	}
	@Override
	public Attachments fetchAttachmentsById(String id) throws Exception{
		return (attachmentQueryManager.findById(id)).get();
//		return (attachmentQueryManager.findById(UUID.fromString(id))).get();
	}
	
	/**
	 * File 구분 폴더(division) 별로 날짜(월)을 구분해서 파일을 저장할지에 대한 boolean 값
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean useMonthlyFolder(String fileDivision) throws Exception {
		if (fileDivision.equals(FILE_DIVISION_TEMPS)) {
			return false;
		}
		return true;
	}
    
    /**
     * 파일 저장 위치
     */
    private String fileDirectory;

    /**
     * @return the fileDirectory
     */
    public String getFileDirectory() {
        return fileDirectory;
    }

    /**
     * @param fileDirectory
     *            the fileDirectory to set
     */
    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    /**
     * 파일 저장 공간을 생성하고 리턴한다. 
     * 
     * fileRootDivision : ex) companyId, projectId...
     * 
     * @param fileRootDivision
     * @param fileDivision
     * @param onMonthlyFolder
     * @return
     * @throws Exception
     */
    private File getFileRepository(String fileRootDivision, String fileDivision, boolean onMonthlyFolder) throws Exception {

        if (ValueUtil.isEmpty(this.fileDirectory)) {
        		this.fileDirectory = fileConfiguration.getRepositoryPath();
        		if (ValueUtil.isEmpty(this.fileDirectory)) {
        			throw new Exception("Attachment directory is not specified!");
        		}
        }
        
        // 파일 홈 디렉토리 선택
        String storageDir = this.fileDirectory;
        File storage = new File(storageDir);
        // 없다면 생성한다.
        if (!FileUtil.exists(storage)) {
            FileUtil.mkdir(storage);
        }    
        // 파일 루트 디렉토리 선택
        storageDir =  storageDir + File.separator + fileRootDivision;
        storage = new File(storageDir);
        if (!FileUtil.exists(storage)) {
            FileUtil.mkdir(storage);
        }
        // 파일 형태 구분에 따른 디렉토리 선택
        storageDir = storageDir + File.separator + fileDivision;
        storage = new File(storageDir);

        // 없다면 생성한다.
        if (!FileUtil.exists(storage)) {
            FileUtil.mkdir(storage);
        }
        if (onMonthlyFolder) {
            // 현재 년, 월 정보를 얻는다.
            Calendar currentDate = Calendar.getInstance();
            int year = currentDate.get(Calendar.YEAR);
            int month = currentDate.get(Calendar.MONTH) + 1;
            // 기본 파일 저장 디렉토리와 현재 년 정보로 파일 디렉토리를 설정한다.
            storageDir = storageDir + File.separator + "Y" + year;
            storage = new File(storageDir);
            // 없다면 생성한다.
            if (!FileUtil.exists(storage)) {
                FileUtil.mkdir(storage);
            }    
            // 기본 파일 저장 디렉토리와 현재 월 정보로 파일 디렉토리를 설정한다.
            storageDir = storageDir + File.separator + "M" + month;
            storage = new File(storageDir);
            // 만일 디렉토리가 없다면 생성한다.
            if (!FileUtil.exists(storage)) {
                FileUtil.mkdir(storage);
            }
        } else {
            storage = new File(storageDir);
        }
        return storage;
    }
    
    private void writeAjaxFile(HttpServletRequest request, HttpServletResponse response, FileModel formFile) throws Exception {

        PrintWriter writer = null;
        InputStream is = null;

        try {
            writer = response.getWriter();
        } catch (IOException ex) {
            throw new Exception(ex.getMessage());
        }
        String agentInfo = request.getHeader("User-Agent");
        try {
            if (agentInfo.indexOf("MSIE") > 0 || request instanceof MultipartHttpServletRequest) { //IE
                MultipartFile multipartFile = formFile.getMultipartFile();
                is = multipartFile.getInputStream();
            } else {
                is = request.getInputStream();
            }
            UIWebsocketManager ws = new UIWebsocketManager();
            Map<String, Object> message = new HashMap<String, Object>();
            message.put("key", "copingProgress");
            message.put("fileName", formFile.getFileName());
            message.put("fileSize", formFile.getFileSize());
    		FileOutputStream fos = new FileOutputStream(new File(formFile.getFilePath()));
	        long count = 0;
    		try {
    	    	byte[] buffer = new byte[FileUtil.DEFAULT_BUFFER_SIZE];
    	        long available = (long)message.get("fileSize");
    	        int n = 0;
    	        int lastProgress = 0;
    	        while (-1 != (n = is.read(buffer))) {
    	        	fos.write(buffer, 0, n);
    	            count += n;
    	            int progress = Math.round((float)count/(float)available * 100);
    	            if (progress > lastProgress) {
    	            	message.put("copingProgress", progress);
//    	            	ws.sendMessage(AuthUtil.getCurrentUserId(), FormatUtil.toJsonString(message));
    	            	ws.sendMessage(null, "userId", FormatUtil.toJsonString(message), null);
    	            	lastProgress = progress;
    	            }
    	        }    			
    	        if (count > Integer.MAX_VALUE) {
    	        }
    		} catch (Exception ex) {
    			ex.printStackTrace();
    		} finally {
    			fos.close();
    		}
            response.setStatus(HttpServletResponse.SC_OK);
            if (agentInfo.indexOf("MSIE") <= 0) {
                response.setHeader("Content-Type", "application/json");
            } else {
                response.setHeader("Content-Type", "text/html");
            }
            
            String encodingFilePath = StringUtils.replace(formFile.getFilePath(), "\\", "/");
            String encodingFullPathName = StringUtils.replace(formFile.getFileServerPath(), "\\", "/");
            
            if (agentInfo.indexOf("MSIE") <= 0) {
                writer.print("{\"status\": " + true + ", \"result\": {\"fileUrl\":\"" + formFile.getFileUrl(FILE_DIVISION_TEMPS) + "\", \"fileId\": \"" + formFile.getId() + "\", \"fileExtension\": \"" + formFile.getType() + "\", \"fileName\": \"" + formFile.getFileName() + "\", \"fullPathName\": \"" + encodingFullPathName + "\", \"fileSize\": " + formFile.getFileSize() + ", \"localFilePath\": \"" + encodingFilePath + "\"}}");
            } else {            
                writer.print("{\"status\": " + true + ", \"result\": {\"fileUrl\":\"" + formFile.getFileUrl(FILE_DIVISION_TEMPS) + "\", \"fileExtension\": \"" + formFile.getType() + "\", \"fileId\": " + formFile.getId() + "\", \"fullPathName\": \"" + formFile.getFileServerPath() + "\", \"fileSize\": " + formFile.getFileSize() + ", \"localFilePath\": \"" + encodingFilePath + "\"}}");
            }    
        } catch (FileNotFoundException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.print("{\"status\": false, \"result\": {\"fileId\": \"" + formFile.getId() + "\"}}");
            throw new Exception(ex.getMessage());
        } catch (IOException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.print("{\"status\": false, \"result\": {\"fileId\": \"" + formFile.getId() + "\"}}");
            throw new Exception(ex.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException ignored) {
                throw new Exception(ignored.getMessage());
            }
        }

        writer.flush();
        writer.close();
    }
    
    @Override
    public void uploadTempFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        FileModel formFile = new FileModel();

        String fileRootDivision = fileConfiguration.getRootDivision();

        String fileId = IdUtil.getUUIDString();
        String fileDivision = FILE_DIVISION_TEMPS;
        File repository = this.getFileRepository(fileRootDivision, fileDivision, useMonthlyFolder(fileDivision));
        String filePath = "";
        String fileServerPath = "";
        String extension = "";
        String fileName = "";
        String agentInfo = request.getHeader("User-Agent");
        if (agentInfo.indexOf("MSIE") > 0 || request instanceof MultipartHttpServletRequest) { 
	        	//IE
            List<FileModel> docList = new ArrayList<FileModel>();
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> filesMap = multipartRequest.getFileMap();
            for (String key : filesMap.keySet()) {
                MultipartFile mf = filesMap.get(key);
                FileModel doc = new FileModel();
                doc.setMultipartFile(mf);
                docList.add(doc);
            }
            formFile = docList.get(0);
            MultipartFile multipartFile = formFile.getMultipartFile();
            fileName = multipartFile.getOriginalFilename();
            formFile.setFileSize(multipartFile.getSize());
            formFile.setMultipartFile(multipartFile);
        } else {
            try {
            	JSONObject readBody = RestUtil.readRequestBody(request);
            	fileName = (String)readBody.get("fileName");
                //fileName = URLDecoder.decode(request.getHeader("X-File-Name"), "UTF-8");
            	long fileSize = (long)readBody.get("fileSize");
                formFile.setFileSize(fileSize);
            } catch (UnsupportedEncodingException ex) {
                throw ex;
            }
        }

        if (fileName.indexOf(File.separator) > 1) {
            fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
        }
        formFile.setFileName(fileName);
        extension = fileName.lastIndexOf(".") > -1 ? fileName.substring(fileName.lastIndexOf(".") + 1) : null;
        filePath = repository.getAbsolutePath() + File.separator + (String) fileId;

        fileServerPath = this.getFileDirectory() + fileRootDivision + File.separator + FILE_DIVISION_TEMPS + File.separator + fileId + "." + extension;
        formFile.setFileServerPath(fileServerPath);

        if (extension != null) {
            filePath = filePath + "." + extension;
        }

        formFile.setFilePath(filePath);
        formFile.setId(fileId);
        formFile.setType(extension != null ? extension.toLowerCase() : null);

        this.writeAjaxFile(request, response, formFile);
    }
    
    @Override
    public boolean deleteFile(String path) throws Exception {
        boolean result = false;

        String fileRootDivision = fileConfiguration.getRootDivision();
        String fileRepository = fileConfiguration.getRepositoryPath();
        
        String filePath = fileRepository + fileRootDivision + "/Temps/" + path;
        File file = new File(filePath);
        if (FileUtil.exists(file)) {
            FileUtil.delete(file);
            result = true;
        }
        return result;
    }
   
    @Override
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, String path, String fileName) throws Exception {
    	String repositoryPath = fileConfiguration.getRepositoryPath();

    	try {
	    	path = repositoryPath + File.separator + fileConfiguration.getRootDivision() + "/Temps/" + path;
			File file = new File(path);
			
			String userAgent = request.getHeader("User-Agent");
			boolean ie = userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("rv:11") > -1;
//			String fileName = null;
			
			if (ie) {
				fileName = URLEncoder.encode(fileName, "utf-8");
			} else {
				fileName = new String(fileName.getBytes("utf-8"),"iso-8859-1");
			}

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment;filename=\"" +fileName+"\";");

			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ServletOutputStream so = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(so);

			byte[] data = new byte[2048];
			int input = 0;
			while ((input=bis.read(data))!=-1) {
				bos.write(data,0,input);
				bos.flush();
			}

			if (bos!=null) bos.close();
			if (bis!=null) bis.close();
			if (so!=null) so.close();
			if (fis!=null) fis.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
	
}
