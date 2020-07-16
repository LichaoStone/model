package com.xuncai.parking.common.util.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
* @author 半步 E-mail:renzhichaoshaer@163.com
* @version 创建时间：2017年10月16日 下午5:38:37
* service返回值包装对象
* @param <T>
*/
public class ServiceResult<T> implements Serializable {

	private static final long serialVersionUID = -6652076509848001811L;

	private boolean isOk = false;
	
	/**
	 *  msg 状态0表示异常、1表示正确
	 */
	private int msgType = 0;
	
	/**
	 * 备注
	 */
	private String comment;

	/**
	 * 消息code
	 */
	private String msgCode;
	/**
	 * 消息格式化参数
	 */
	private Object[] msgParams;	
	/**
	 * 数据
	 */
	private T data;
	/**
	 * 数据集合
	 */
	private List<T> dataList;
	/**
	 * 数据Map
	 */
	private Map<String, Object> dataMap;
	
	public ServiceResult() {}
	
	public ServiceResult(boolean ok) {
		this.isOk = ok;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}
	
	public int getMsgType(){
		return this.msgType;
	}

	public String getMsgCode() {
		return msgCode;
	}

	/**
	 * 根据msg第二位字母取得msg类型
	 * @param msgCode
	 */
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
		try {
			char state = msgCode.charAt(1);
			switch (state) {
			case 'I':
				this.msgType = 1;
				break;
			case 'Q':
				this.msgType = 1;
				break;
			case 'E':
				this.msgType = 0;
				break;
				
			default:
				this.msgType = 0;
				break;
			}
			
		} catch (Exception e) {
			this.msgType = 0;
		}
	}
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Object[] getMsgParams() {
		return msgParams;
	}

	public void setMsgParams(Object... msgParams) {
		this.msgParams = msgParams;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

}
