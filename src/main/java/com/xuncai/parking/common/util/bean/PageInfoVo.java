/**
* @Title: PageInfo.java
* @Package com.haikan.iptv.common.util.bean
* @Description: TODO
* @author mayi
* @date 2020年2月20日
* @version V1.0
*/
package com.xuncai.parking.common.util.bean;

import com.github.pagehelper.PageInfo;

/**
* @ClassName: PageInfo
* @Description: TODO
* @author
* @date 2020年2月20日
*
*/
public class PageInfoVo {
	
	public PageInfoVo(PageInfo<?> page) {
		this.isNextPage = page.isHasNextPage();
		this.pageStartRow = page.getStartRow();
		this.pageSize = page.getPageSize();
		this.listSize = page.getList().size();
		this.totalCount = page.getTotal();
	}
	//是否有下一页
	boolean isNextPage ;
	//开始行
	long pageStartRow ;
	//每页数据
	long pageSize ;
	//当前页数据
	long listSize ;
	//总行数
	long totalCount ;
	public boolean isNextPage() {
		return isNextPage;
	}
	public void setNextPage(boolean isNextPage) {
		this.isNextPage = isNextPage;
	}
	public long getPageStartRow() {
		return pageStartRow;
	}
	public void setPageStartRow(long pageStartRow) {
		this.pageStartRow = pageStartRow;
	}
	public long getPageSize() {
		return pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	public long getListSize() {
		return listSize;
	}
	public void setListSize(long listSize) {
		this.listSize = listSize;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
}
