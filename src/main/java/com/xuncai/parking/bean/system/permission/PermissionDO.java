package com.xuncai.parking.bean.system.permission;

import com.xuncai.parking.bean.BaseEntity;
import lombok.Data;

/**
 * @ProjectName: parking
 * @Package: com.xuncai.parking.bean.system.permission
 * @ClassName: PermissionDO
 * @Author: zangt
 * @Description:
 * @Date: 2020/6/22 22:26
 * @Version: 1.0
 */
@Data
public class PermissionDO extends BaseEntity {
    private String permissionKey;
    private String permissionId;
    private String permissionCode;
    private String parentId;
    private String name;
    private String url;
    private String orderNum;
    private String icon;
    private String level;
    private String type;
    private String description;
    private String createTime;
    private String updateTime;
    private String creator;
}
