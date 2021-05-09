/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    FileUtil.java
 *  Description:  	File에 관련된 다양한 기능들을 제공하는 클래스 
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.util;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.server.fmb.constant.Constant;
import com.server.fmb.exception.InvalidArgumentException;


public class FileUtil extends FileUtils {
	/*
	 * all method from Commons.IO.FileUtils
	 */
	
    public static final int DEFAULT_BUFFER_SIZE = 1024 * 4;    

	public static JSONObject readJsonObjectFromFile(String filePath) throws Exception {
        JSONParser jsonParser = new JSONParser();
        
        try (FileReader reader = new FileReader(filePath))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            return (JSONObject)obj;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } catch (ParseException e) {
            e.printStackTrace();
            throw e;
        }
    }
	public static void writeJsonObjectToFile(String filePath, JSONObject jsonObject) throws Exception {
        try (FileWriter file = new FileWriter(filePath)) {
        	 
            file.write(jsonObject.toJSONString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }		
	}
	public static String[] getFileList(String filePath) {
        if (ValueUtil.isEmpty(filePath)) {
            return null;
        }
		File dir = new File(filePath);
		if (dir == null || dir.list() == null) {
			return null;
		}
		return dir.list();
    }
	
	public static int getFileListSize(String filePath) {
        if (ValueUtil.isEmpty(filePath)) {
            return 0;
        }
		File dir = new File(filePath);
		if (dir == null || dir.list() == null) {
			return 0;
		}
		return dir.list().length;
    }
	
    public static String encodeFileName(HttpServletRequest request, String fileName) {
        if (request == null || fileName == null) {
            return null;
        }
        try {
            String browser = request.getHeader("User-Agent");
            if (browser.contains("MSIE") || browser.contains("Trident")) {               
                return URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
            } else {               
                return new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
        } catch (Exception ex) {
            return fileName;
        }
    }
    
    public static boolean isTempId(String id) {
        if (id == null) {
            return false;
        }
        if (id.indexOf(Constant.FILE_ID_PREFIX_TEMP) != -1) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean isCompressedExtension(String extension) {
        if (ValueUtil.isEmpty(extension)) {
            return false;
        }
        if (compressedExtensions.contains(extension.toUpperCase())) {
        		return true;
        }
        return false;
    }

    public static final ArrayList<String> compressedExtensions = new ArrayList<String>() {{
	    add("ZIP");
		add("RAR");
    }};
    
    public static boolean isImageExtension(String extension) {
	    	if (ValueUtil.isEmpty(extension)) {
	    		return false;
	    	}
	    	if (imageExtensions.contains(extension.toUpperCase())) {
	    		return true;
	    	}
	    	return false;
    }
    public static boolean exists(File file) throws Exception{
    	if (file == null) {
    		throw new InvalidArgumentException();
    	}
		return file.exists();
    }
    
    public static boolean mkdir(File file) throws Exception {
    	if (file == null) {
    		throw new InvalidArgumentException();
    	}
		return file.mkdir();
    }
    
    public static boolean delete(File file) throws Exception {
    	if (file == null) {
    		throw new InvalidArgumentException();
    	}
		return file.delete();
    }
    
    public static long copy(File file, OutputStream out) throws Exception {
    	if (file == null || out == null) {
    		throw new InvalidArgumentException();
    	}
		int length = 0;
        byte[] bbuf = new byte[4096];
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        try {
	        while ((in != null) && ((length = in.read(bbuf)) != -1)) {
	            out.write(bbuf,0,length);
	        }	        	
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
	        in.close();	        	
        }
        return file.length();
    }
    
    public static void copy(File fromFile, File toFile) throws Exception {
    	if (fromFile == null || toFile == null) {
    		throw new InvalidArgumentException();
    	}
        FileInputStream is = new FileInputStream(fromFile);
        FileOutputStream os = new FileOutputStream(toFile);
        IOUtils.copy(is, os);
        is.close();
        os.close();
    }
    
    public static BufferedImage getImageRead(File file) throws Exception {
    	if (file == null) {
    		throw new InvalidArgumentException();
    	}
		return ImageIO.read(file);    	
    }
    
    public static boolean writeImage(BufferedImage image, String extention, File file) throws Exception {
    	if (image == null || extention == null || file == null) {
    		throw new InvalidArgumentException();
    	}
		return ImageIO.write(image, extention, file);
    }
    
    public static final ArrayList<String> videoExtensions = new ArrayList<String>() {{
    	add("MP4");
    	add("MP3");
    	add("MDMOV");
    	add("MOV");
    	add("MOVIE");
    }};
    
    public static final ArrayList<String> imageExtensions = new ArrayList<String>() {{
	    add("BMP");
		add("RLE");
		add("JPG");
		add("JPEG");
		add("JPE");
		add("JFIF");
		add("GIF");
		add("PNG");
		add("PDD");
		add("PSD");
		add("TIF");
		add("RAW");
		add("AI");
		add("EPS");
		add("SVG");
		add("SVGZ");
		add("IFF");
		add("FPX");
		add("FRM");
		add("PCX");
		add("PCT");
		add("PIC");
		add("PXR");
		add("SCT");
		add("TGA");
		add("VDA");
		add("ICB");
		add("VST");
	}};
    
}
