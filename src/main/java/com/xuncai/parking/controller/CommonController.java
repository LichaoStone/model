/**
* @Title: CommonController.java
* @Description: TODO
* @author
* @date 2020年2月18日
* @version V1.0
*/
package com.xuncai.parking.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xuncai.parking.common.util.DateTimeUtils;
import com.xuncai.parking.common.util.ImageUtil;
import com.xuncai.parking.common.util.StringUtils;
import com.xuncai.parking.common.util.bean.Result;
import com.xuncai.parking.common.util.constant.Constants;

/**
* @ClassName: CommonController
* @Description: TODO
* @author
* @date 2020年2月18日
*
*/
@Controller
public class CommonController {

	@RequestMapping("/upload_image")
	@ResponseBody
	public Result  uploadImage(HttpServletRequest request,HttpServletResponse response,@RequestParam(required = false,name="file") MultipartFile file){
		Result result = new Result();
		if (file != null && file.getSize() > 0) {
			try {
				/**
				 * 获得原始文件名
				 */
				String fileName = file.getOriginalFilename();
				System.out.println(file.getSize());
				/**
				 * 获得文件格式
				 */
				String suffix = "jpg" ;
				if (StringUtils.isNotBlank(fileName)) {
					suffix = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
				}
				/**
				 * 图片宽，不需要缩放为0
				 */
				int width = 0 ;
				/**
				 * path 图片路径
				 */
				String uploadImagePath =  Constants.UPLOAD_IMAGE_PATH+DateTimeUtils.getTimeTomMonth(new Date());;
				String path = ImageUtil.zoom(request,file.getBytes(),width, suffix,uploadImagePath);
				result.setCode(200);;
				result.setMsg("文件上传成功");
				result.setData(path);
			} catch (Exception e) {
				e.printStackTrace();
				result.setCode(500);
				result.setMsg("上传文件失败，请重新上传");
			}	
		}else {
			result.setCode(500);
			result.setMsg("请选择上传文件");
		}
		return result ;
	}
}
