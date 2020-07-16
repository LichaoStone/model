package com.xuncai.parking.common.util;

/**
 * 文件检测
 * @author
 * 2013-11-27
 */
public class CheckFile {

	/**
	 * 检测文件格式
	 * @param  允许的格式数组
	 * @return 是否允许
	 */
	public static boolean checkExt(String allowParam,String ext){
		
		boolean flag = false;
		
		String[] allowArray = null;
		if(null != allowParam){
			if(null != allowParam.trim()){
				allowArray = allowParam.trim().split(",");
			}
		}
		
		if(null != allowArray){
			if(allowArray.length > 0){
				for (int i = 0; i < allowArray.length; i++) {
					String allowExt = allowArray[i];
					if(null != allowExt){
						allowExt = allowExt.trim().toLowerCase();
						if(null != ext){
							ext = ext.trim().toLowerCase();
						}
						if(allowExt.equals(ext)){
							flag = true;
							break;
						}
					}
				}
			}
		}
		
		return flag;
	}
}
