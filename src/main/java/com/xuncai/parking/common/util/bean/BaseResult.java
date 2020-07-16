/**
* @Title: BaseResult.java
* @Package com.haikan.iptv.common.util.bean
* @Description: TODO
* @author mayi
* @date 2020年2月20日
* @version V1.0
*/
package com.xuncai.parking.common.util.bean;

/**
* @ClassName: BaseResult
* @Description: TODO
* @author
* @date 2020年2月20日
*
*/
public class BaseResult {
	
	Integer code = 200;
	
	Object results ;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Object getResults() {
		return results;
	}
	public void setResults(Object results) {
		this.results = results;
	}
	
}
