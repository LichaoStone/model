/**
* @Title: Result.java
* @Package com.haikan.iptv.common.util.bean
* @Description: TODO
* @author mayi
* @date 2020年2月20日
* @version V1.0
*/
package com.xuncai.parking.common.util.bean;

import java.util.List;

import com.github.pagehelper.PageInfo;

/**
* @ClassName: Result
* @Description: TODO
* @author
* @date 2020年2月20日
*
*/
public class Result {
	
	public Result() {}
	public Result(Object data) {
		this.data = data ;
	}
	public Result(PageInfo<?> page,List<?> list) {
		this.data = list;
		this.pageInfo = new PageInfoVo(page);
	}
	public Result(String msg,Object data) {
		this.msg = msg ;
		this.data = data ;
	}
	public Result(Integer code ,Integer ret,String msg,Object data) {
		this.code = code ;
		this.ret = ret ;
		this.msg = msg ;
		this.data = data ;
	}
	
	public Result(Integer code ,Integer ret,String msg,PageInfo<Object> page,List<Object> list) {
		this.code = code ;
		this.ret = ret ;
		this.msg = msg ;
		this.data = list ;
		this.pageInfo = new PageInfoVo(page);
	}
	//业务码
	Integer code = 200 ;
	//参数
	Integer ret = 200 ;
	//提示信息
	String msg = "成功";
	//数据
	Object data ;
	//分页信息
	PageInfoVo pageInfo ;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getRet() {
		return ret;
	}
	public void setRet(Integer ret) {
		this.ret = ret;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public PageInfoVo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoVo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
}
