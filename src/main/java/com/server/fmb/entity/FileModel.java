/*
 *  Copyright (c) 2020 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    FileModel.java
 *  Description:  	파일관리를 위한 데이터베이스 테이블 정의 클래스
 *  Authors:        Y.S. Jung
 *  Update History:
 *                  2020.03.14 : Created by Y.S. Jung
 *
 */
package com.server.fmb.entity;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.server.fmb.util.IdUtil;
import com.server.fmb.util.ValueUtil;

//import com.miflow.annotation.Column;
//import com.miflow.annotation.Index;
//import com.miflow.annotation.Table;
//import com.miflow.constant.Constant;
//import com.miflow.file.impl.FileManagerImpl;
//import com.miflow.model.BaseObject;
//import com.miflow.util.BeanUtil;
//import com.miflow.util.FileUtil;
//import com.miflow.util.ValueUtil;
//import com.miflow.workflow.util.IdUtil;

//@Table (name="mf_file", indexes= {
//		@Index(name = "ix_mf_file_0", columnList = "id")
//		,@Index(name = "ix_mf_file_1", columnList = "group_id")
//		,@Index(name = "ix_mf_file_2", columnList = "creation_user")
//		,@Index(name = "ix_mf_file_4", columnList = "type")
//		,@Index(name = "ix_mf_file_5", columnList = "file_name")
//})
//public class FileModel  extends BaseObject implements Serializable {
public class FileModel {

    public static final String prefix = "file";
    public static final String prefix_file_group = "fg";
    public static final String postfix_thumbnail = "_tn";
    public static int MAX_PROFLIE_SIZE = 120;
    public static int THUMBNAIL_SIZE = 300;

//	@Column (name="id", type=Constant.PG_COL_CHARACTER_VARYING, length=50, nullable=false, primaryKey=true)
    private String id;

//	@Column (name="type", type=Constant.PG_COL_CHARACTER_VARYING, length=10)
    private String type;

//	@Column (name="file_name", type=Constant.PG_COL_CHARACTER_VARYING, length=255)
    private String fileName;

//	@Column (name="file_path", type=Constant.PG_COL_CHARACTER_VARYING, length=500)
    private String filePath;

//	@Column (name="file_size", type=Constant.PG_COL_INTEGER)
    private long fileSize;

//	@Column (name="image_width", type=Constant.PG_COL_INTEGER)
    private int imageWidth;

//	@Column (name="image_height", type=Constant.PG_COL_INTEGER)
    private int imageHeight;

//	@Column (name="deleted", type=Constant.PG_COL_CHARACTER_VARYING, length=1)	
    private String deleted;

//	@Column (name="group_id", type=Constant.PG_COL_CHARACTER_VARYING, length=50)
    private String groupId;
	
//	@Column (name="target_id", type=Constant.PG_COL_CHARACTER_VARYING, length=50)
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

    
//	public String getImageUrl() {
//    	return BeanUtil.get(IFileAbstract.class).getServerUrl() + this.filePath;
//	}
//
//	public String getFileUrl() {
//    	return BeanUtil.get(IFileAbstract.class).getServerUrl() + this.filePath;
//	}
//
//	public String getThumbnailUrl() {
//    	return BeanUtil.get(IFileAbstract.class).getServerUrl() + this.filePath.replace("."+ this.type, FileModel.postfix_thumbnail + "." + this.type);
//	}
	
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
