package com.xuncai.parking.common.util;

import java.io.IOException;
import java.io.InputStream;

public final class FileTypeJudge {  
    
    /** 
     * Constructor 
     */  
    private FileTypeJudge() {}  
      
    /** 
     * 将文件头转换成16进制字符串 
     *  
     * @param 原生byte 
     * @return 16进制字符串 
     */  
    private static String bytesToHexString(byte[] src){  
          
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {     
            return null;     
        }     
        for (int i = 0; i < src.length; i++) {     
            int v = src[i] & 0xFF;     
            String hv = Integer.toHexString(v);     
            if (hv.length() < 2) {     
                stringBuilder.append(0);
            }     
            stringBuilder.append(hv);
        }     
        return stringBuilder.toString();     
    }  
     
    /** 
     * 得到文件头 
     *  
     * @param filePath 文件路径 
     * @return 文件头 
     * @throws IOException 
     */  
    private static String getFileContent(InputStream in) throws IOException {  
          
        byte[] b = new byte[28];  
          
        try {
            in.read(b, 0, 28);  
        } catch (IOException e) {  
            e.printStackTrace();  
            throw e;  
        } finally {  
            if (in != null) {  
                try {  
                	in.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw e;  
                }  
            }  
        }  
        return bytesToHexString(b);  
    }  
      
    /** 
     * 判断文件类型 
     *  
     * @param filePath 文件路径 
     * @return 文件类型 
     */  
    public static FileType getType(InputStream in) throws IOException {  
          
        String fileHead = getFileContent(in);  
          
        if (fileHead == null || fileHead.length() == 0) {  
            return null;  
        }  
          
        fileHead = fileHead.toUpperCase();  
        System.out.println("fileHead:"+fileHead);
        FileType[] fileTypes = FileType.values();  
          
        for (FileType type : fileTypes) {  
            if (fileHead.startsWith(type.getValue())) {  
                return type;  
            }  
        }  
          
        return null;  
    }  
}  
