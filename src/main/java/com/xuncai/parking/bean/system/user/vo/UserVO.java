package com.xuncai.parking.bean.system.user.vo;

import com.xuncai.parking.bean.system.user.UserDO;
import lombok.Data;

/**
 * @ProjectName: parking
 * @Package: com.xuncai.parking.bean.system.user.vo
 * @ClassName: UserVO
 * @Author: zangt
 * @Description: sys_user用户表vo
 * @Date: 2020/6/22 16:20
 * @Version: 1.0
 */
@Data
public class UserVO extends UserDO {
    private String roleKey;
    private String roleName;
    private String permissionCode;
}
