package com.xuncai.parking.bean.system.role;

import com.xuncai.parking.bean.BaseEntity;
import lombok.Data;

/**
 * @ProjectName: parking
 * @Package: com.xuncai.parking.bean.system.role
 * @ClassName: RolePermissionDO
 * @Author: zangt
 * @Description:
 * @Date: 2020/6/23 5:02
 * @Version: 1.0
 */
@Data
public class RolePermissionDO extends BaseEntity {
    private String rolePermissionKey;
    private String roleKey;
    private String permissionKey;
}
