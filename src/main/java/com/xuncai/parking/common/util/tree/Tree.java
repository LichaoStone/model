package com.xuncai.parking.common.util.tree;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tree<T> {

    private String id;

    private String key;

    private String icon;

    private String label;

    private String value;

    private String text;

    private String permission;

    private String type;

    private Integer order;

    private String path;

    private String component;

    private List<Tree<T>> children;

    private String parentId;

    private boolean hasParent = false;

    private boolean hasChildren = false;

    private Date createTime;

    private Date modifyTime;

    public void initChildren(){
        this.children = new ArrayList<>();
    }

}
