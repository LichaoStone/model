package com.xuncai.parking.common.util;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 上传图片
 * @author lichao
 *
 */
public class UploadImage {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(UploadImage.class);
	
	/**
	 * 上传图片
	 * @param imgFile 上传图片
	 * @return 
	 * 		图片地址、图片高度和图片宽度
	 */
	public static Map<String,Object> uploadsPath(HttpServletRequest request,MultipartFile imgFile) throws IOException {
		
		String relativeOriginalFilePath = "";
		String absoluteOriginalFilePath= "";
		
		String uploadPathProperties = PropertiesUtil.getValue("uploadPathProperties");
		//获取上传图片的原名称
		String originalFileName = imgFile.getOriginalFilename();
		//获取上传图片的扩展名
		String extensionName = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).trim().toLowerCase();
		//根据系统时间重命名
		
		//long now = System.currentTimeMillis();
		String targetFileName = PkCreat.getTablePk()+"."+extensionName;

		//源文件上传相对路径
		relativeOriginalFilePath = uploadPathProperties;
		//源文件上传绝对路径
//		absoluteOriginalFilePath = request.getRealPath("/") + uploadPathProperties  + "/";
		absoluteOriginalFilePath = uploadPathProperties;

		int sourceWidth = 0;
		int sourceHeight = 0;
		InputStream inimg = null;
		BufferedImage sourceImg = null;
		//获取图片宽度
		
		try {
			inimg = imgFile.getInputStream();	
			sourceImg = javax.imageio.ImageIO.read(inimg); 
			sourceWidth = sourceImg.getWidth();
			sourceHeight = sourceImg.getHeight();
			logger.info("获取到的图片宽度：" + sourceWidth);
			logger.info("获取到的图片高度：" + sourceHeight);
		} catch (Exception e) {
			inimg.close();
			logger.error("获取图片流错误",e);
		}
			
		File absoluteImageFile = new File(absoluteOriginalFilePath);
		if(!absoluteImageFile.exists()){
			absoluteImageFile.mkdirs();
		}
		
		//将图片上传
		InputStream in = imgFile.getInputStream();	
		FileOutputStream out  = null;
		InputStream inst = null;
		boolean fileflag = FileTypeCheck.checkFile("jpg,png,jpeg,gif",in,absoluteOriginalFilePath+originalFileName,extensionName);
		System.out.println("图片上传" + fileflag);
		if(!fileflag){
			inimg.close();
			in.close();
			throw new IOException();
		}else{
			System.out.println("文件地址:"+absoluteOriginalFilePath + targetFileName);
			inst = imgFile.getInputStream();
			out = new FileOutputStream(absoluteOriginalFilePath + targetFileName);
			
			byte buffer[] = new byte[1024];
			int len = 0;
			while ((len = inst.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		}
		
		in.close();
		inst.close();
		out.close();
		inimg.close();
		
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("imgUrl",relativeOriginalFilePath+targetFileName);
		resultMap.put("imgWidth",sourceWidth);
		resultMap.put("imgHeight",sourceHeight);
		
		System.out.println("返回地址:"+relativeOriginalFilePath+targetFileName);
		return resultMap;	
	}


	/**
	 * 上传图片
	 * @param imgFile 上传图片
	 * @return
	 * 		图片地址、图片高度和图片宽度
	 */
	public  static String uploadImg(HttpServletRequest request,MultipartFile imgFile) throws IOException {

		String relativeOriginalFilePath=request.getRealPath("/")+PropertiesUtil.getValue("uploadPathProperties")+"/"+DateUtil.getCurrYear()+"/"+DateUtil.getCurrMonth()+"/";
		String absoluteOriginalFilePath=PropertiesUtil.getValue("uploadPathProperties")+"/"+DateUtil.getCurrYear()+"/"+DateUtil.getCurrMonth()+"/";

		logger.info("【图片上传路径】:绝对路径:"+relativeOriginalFilePath+",相对路径:"+absoluteOriginalFilePath);


		//获取上传图片的原名称
		String originalFileName = imgFile.getOriginalFilename();
		//获取上传图片的扩展名
		String extensionName = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).trim().toLowerCase();

		String targetFileName = PkCreat.getTablePk()+"."+extensionName;



		File absoluteImageFile = new File(relativeOriginalFilePath);
		if(!absoluteImageFile.exists()){
			absoluteImageFile.mkdirs();
		}

		//将图片上传
		InputStream in = imgFile.getInputStream();
		FileOutputStream out  = null;
		InputStream inst = null;
		boolean fileflag = FileTypeCheck.checkFile("jpg,png,jpeg",in,absoluteOriginalFilePath+originalFileName,extensionName);
		System.out.println("图片上传" + fileflag);
		if(!fileflag){
			in.close();
			throw new IOException();
		}else{
			System.out.println("文件地址:"+relativeOriginalFilePath +targetFileName);
			inst = imgFile.getInputStream();
			out = new FileOutputStream(relativeOriginalFilePath +targetFileName);

			byte buffer[] = new byte[1024];
			int len = 0;
			while ((len = inst.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		}

		in.close();
		inst.close();
		out.close();


		System.out.println("返回地址:"+absoluteOriginalFilePath+targetFileName);
		return absoluteOriginalFilePath+targetFileName;
	}
}
