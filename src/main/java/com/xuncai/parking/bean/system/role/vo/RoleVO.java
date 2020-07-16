package com.xuncai.parking.bean.system.role.vo;

import com.xuncai.parking.bean.system.role.RoleDO;
import lombok.Data;

/**
 * @ProjectName: parking
 * @Package: com.xuncai.parking.bean.system.role.vo
 * @ClassName: RoleVO
 * @Author: zangt
 * @Description:
 * @Date: 2020/6/22 21:45
 * @Version: 1.0
 */
@Data
public class RoleVO extends RoleDO {
    private String[] permissions;
}
