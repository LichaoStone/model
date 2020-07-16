package com.xuncai.parking.bean.system.role;

import com.xuncai.parking.bean.BaseEntity;
import lombok.Data;

/**
 * @ProjectName: parking
 * @Package: com.xuncai.parking.bean.system.role
 * @ClassName: RoleDO
 * @Author: zangt
 * @Description:
 * @Date: 2020/6/22 21:43
 * @Version: 1.0
 */

@Data
public class RoleDO extends BaseEntity {
    private String roleKey;
    private String roleName;
    private String status;
    private String description;
    private String orderNum;
    private String createTime;
    private String updateTime;
    private String creator;
}
