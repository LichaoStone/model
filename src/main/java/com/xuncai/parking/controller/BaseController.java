package com.xuncai.parking.controller;

import com.xuncai.parking.bean.system.user.vo.UserVO;
import com.xuncai.parking.service.system.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/common")
public class BaseController {

	public static Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Resource
	public UserService userService;

	/**
	 * 获取登录用户
	 * @return
	 */
	public UserVO getLoginUser() {
		UserVO vo = null;
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser != null
				&& currentUser.getPrincipal() != null
				&& currentUser.getSession() != null) {
			String loginName = currentUser.getPrincipal().toString();
			vo = userService.getUserByLoginName(loginName).getData();
		}
		return vo;
	}

}
