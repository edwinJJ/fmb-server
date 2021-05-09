/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    FileModel.java
 *  Description:  	파일관리를 위한 정의 클래스
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.entity;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.server.fmb.util.IdUtil;
import com.server.fmb.util.ValueUtil;

public class FileModel {

    public static final String prefix = "file";
    public static final String prefix_file_group = "fg";
    public static final String postfix_thumbnail = "_tn";
    public static int MAX_PROFLIE_SIZE = 120;
    public static int THUMBNAIL_SIZE = 300;

    private String id;

    private String type;

    private String fileName;

    private String filePath;

    private long fileSize;

    private int imageWidth;

    private int imageHeight;

    private String deleted;

    private String groupId;
	
    private String targetId;
        
    private String fileServerPath;

    private MultipartFile multipartFile;
    
    private InputStream inputStream;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    
    public String getFileServerPath() {
		return fileServerPath;
	}
	
	public int getThumbnailHeight() {
		if (this.imageHeight == 0 || this.imageWidth == 0) {
			return 0;
		}
		if (Math.max(this.imageHeight, this.imageWidth) <= FileModel.THUMBNAIL_SIZE) {
			return this.imageHeight;
		}
		if (this.imageHeight >= this.imageWidth) {
			return FileModel.THUMBNAIL_SIZE;
		}
		return new Double(this.imageHeight * ((0.0 + FileModel.THUMBNAIL_SIZE)/this.imageWidth)).intValue();
	}	
	public int getThumbnailWidth() {
		if (this.imageHeight == 0 || this.imageWidth == 0) {
			return 0;
		}
		if (Math.max(this.imageHeight, this.imageWidth) <= FileModel.THUMBNAIL_SIZE) {
			return this.imageWidth;
		}
		if (this.imageWidth >= this.imageHeight) {
			return FileModel.THUMBNAIL_SIZE;
		}
		return new Double(this.imageWidth * ((0.0 + FileModel.THUMBNAIL_SIZE)/this.imageHeight)).intValue();
	}	
	
	public void setFileServerPath(String fileServerPath) {
		this.fileServerPath = fileServerPath;
	}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setMultiPartInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    
    public InputStream getMultiPartInputStream() {
        return inputStream;
    }
    
    public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
    
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    
	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getFileFullPath(String subPath) {
        
        String fileName = this.getFileName();
        String fileServerPath = this.getFileServerPath();
        if (ValueUtil.isEmpty(subPath)) {
            return fileServerPath + File.separator + fileName;
        } else {
            return fileServerPath + File.separator + subPath + File.separator + fileName;
        }
    }
	
	public String getFileUrl(String fileDivision) {
		String fileUrl = "";
//        IFileAbstract fileAbstract = BeanUtil.get(IFileAbstract.class);
//        if (ValueUtil.isNotEmpty(fileAbstract)) {
//        		if (ValueUtil.isEqual(fileDivision, FileManagerImpl.FILE_DIVISION_TEMPS) && FileUtil.imageExtensions.contains(this.getType().toUpperCase())) {
//        			fileUrl = fileAbstract.getImageServerTempsUrl() + this.getId() + "." + this.getType();
//        		}
//        }
        return fileUrl;
	}
	
	public static String createFileId() {
//		return IdUtil.createId(FileModel.prefix);
		return IdUtil.getUUIDString();
	}
	
	public static String createGroupId() {
//		return IdUtil.createId(FileModel.prefix_file_group);
		return IdUtil.getUUIDString();
	}
	
	public static boolean isFileModelId(String id) {
		if (ValueUtil.isEmpty(id)) {
			return false;
		}
		return id.startsWith(prefix);
	}

	public static boolean isFileGroupId(String id) {
		if (ValueUtil.isEmpty(id)) {
			return false;
		}
		return id.startsWith(prefix_file_group);
	}

}
