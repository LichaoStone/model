package com.xuncai.parking.common.util.bean;

import java.io.Serializable;
import java.util.Map;

/**
* @author
* @version 创建时间：2017年10月16日 下午5:38:37
* 
*/
public class JsonResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6888026705391652090L;
	/**
	 * 操作结果（成功或失败）
	 */
	boolean isOk = true;
	/**
	 * 操作提示信息
	 */
	String message;
	/**
	 * 备注
	 */
	String comment;
	/**
	 * 数据
	 */
	Object data;
	
	String ret = "200";
	
	Map<String, Object> dataMap ;
	
	Object list ;
	
	Long totalCount ;
	
	public JsonResult() {}
	
	public JsonResult(boolean ok) {
		this.isOk = ok;
	}

	public boolean isOk() {
		return isOk;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment =  comment;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Object getList() {
		return list;
	}

	public void setList(Object list) {
		this.list = list;
	}

	
}
