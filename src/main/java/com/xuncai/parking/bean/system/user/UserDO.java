package com.xuncai.parking.bean.system.user;

import com.xuncai.parking.bean.BaseEntity;
import lombok.Data;

/**
 * @ProjectName: parking
 * @Package: com.xuncai.parking.bean.system.user
 * @ClassName: UserDo
 * @Author: zangt
 * @Description: sys_user
 * @Date: 2020/6/22 16:02
 * @Version: 1.0
 */
@Data
public class UserDO extends BaseEntity {
    private String userKey;
    private String createTime;
    private String updateTime;
    private String email;
    private String creator;
    private String age;
    private String phone;
    private String telphone;
    private String userName;
    private String nickName;
    private String pwd;
    private String sex;
    private String birthday;
    private String headImgUrl;
    private String status;
    private String userType;
    private String jobNumber;
    private String remark;
    private String loginTime;
    private String loginName;
}
