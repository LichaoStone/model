package com.xuncai.parking.bean.system.permission.vo;

import com.xuncai.parking.bean.system.permission.PermissionDO;
import lombok.Data;

/**
 * @ProjectName: parking
 * @Package: com.xuncai.parking.bean.system.permission.vo
 * @ClassName: PermissionVO
 * @Author: zangt
 * @Description:
 * @Date: 2020/6/22 22:26
 * @Version: 1.0
 */
@Data
public class PermissionVO extends PermissionDO {
    private String roleKey;
}
