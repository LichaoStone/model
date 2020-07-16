package com.xuncai.parking.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: parking
 * @Package: com.xuncai.parking.bean
 * @ClassName: BaseEntity
 * @Author: zangt
 * @Description: bean基础类
 * @Date: 2020/6/22 16:04
 * @Version: 1.0
 */

public class BaseEntity implements Serializable {
    /**
     * 每页数据条数
     */
    private Integer pageSize;

    /**
     * 当前页数
     */
    private Integer offset;

    public Integer getPageSize() {
        return pageSize==null?10:pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    public Integer getOffset() {
        return offset==null?0:offset;
    }
    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
