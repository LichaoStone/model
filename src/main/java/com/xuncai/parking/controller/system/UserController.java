package com.xuncai.parking.controller.system;

import com.xuncai.parking.bean.system.user.query.UserQuery;
import com.xuncai.parking.common.util.bean.Result;
import com.xuncai.parking.controller.BaseController;
import com.xuncai.parking.service.system.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 用户管理Controller类
 * @ProjectName: parking
 * @Package: com.xuncai.parking.controller.system
 * @ClassName: UserController
 * @Author: zangt
 * @Description:
 * @Date: 2020/6/20 13:20
 * @Version: 1.0
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * 分页展示用户列表
     * @return
     */
    @GetMapping(value="/getUserList")
    @ResponseBody
    public Result getUserList(UserQuery userQuery) {
        return userService.getUserList(userQuery);
    }
}
