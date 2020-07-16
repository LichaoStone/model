package com.xuncai.parking.common.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件类型是否符合要求
 */
public class FileTypeCheck {

	public static boolean checkFile(String allowParams,InputStream in,String filePath,String ext){
		
		boolean flag = CheckFile.checkExt(allowParams, ext);
		
        if(flag){
        	FileType fileRealType = null;
			try {
				fileRealType = FileTypeJudge.getType(in);
				System.out.println("fileRealType"+fileRealType);
			} catch (IOException e) {
				e.printStackTrace();
			}
        	if(null != fileRealType){
        		flag = CheckFile.checkExt(allowParams, fileRealType.toString());
        	}else{
        		flag = false;
        	}
        }
        
        if(flag){
		}else{
		}
        
		return flag;
	}
}

