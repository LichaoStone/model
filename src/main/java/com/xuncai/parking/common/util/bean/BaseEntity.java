package com.xuncai.parking.common.util.bean;

import java.io.Serializable;

public class BaseEntity implements Serializable{
	private static final long serialVersionUID = -8677119705368134413L;
	
	/** 查询条件展开收缩状态 0:收缩 1:展开 */
	private String searchCollapseStatus = "0";
	
	/**页数**/
	private String pageNumber;
	/**每页多少条**/
	private String pageSize;
	/**开始时间**/
	private String beginTime;
	/**结束时间**/
	private String endTime;
	/**搜索类型**/
	private String searchNameType;
	/**模糊搜索内容**/
	private String searchName;
	
	/**
	 * 返回成功失败结果
	 */
	private String result;
	/**返回成功或失败信息**/
	private String resultInfo;

	private String id;
	
	
	public String getSearchCollapseStatus() {
		return searchCollapseStatus;
	}
	public void setSearchCollapseStatus(String searchCollapseStatus) {
		this.searchCollapseStatus = searchCollapseStatus;
	}
	
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getSearchNameType() {
		return searchNameType;
	}
	public void setSearchNameType(String searchNameType) {
		this.searchNameType = searchNameType;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getResultInfo() {
		return resultInfo;
	}
	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
