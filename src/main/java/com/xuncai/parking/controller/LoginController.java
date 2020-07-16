package com.xuncai.parking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.xuncai.parking.bean.system.user.query.UserQuery;
import com.xuncai.parking.bean.system.user.vo.UserVO;
import com.xuncai.parking.common.util.*;
import com.xuncai.parking.common.util.bean.ActiveUser;
import com.xuncai.parking.common.util.bean.ServiceResult;
import com.xuncai.parking.common.util.constant.Constants;
import com.xuncai.parking.common.util.constant.HttpConstant;
import com.xuncai.parking.common.util.constant.RedisKeyConstants;
import com.xuncai.parking.common.util.bean.Result;
import com.xuncai.parking.config.properties.IptvProperties;
import com.xuncai.parking.config.redis.RedisService;
import com.xuncai.parking.config.shiro.JWTToken;
import com.xuncai.parking.config.shiro.util.JWTUtil;
import com.xuncai.parking.config.shiro.util.ShiroUtils;
import com.xuncai.parking.service.system.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 登录Controller类
 */
@Controller
@Slf4j
@RequestMapping("/login")
public class LoginController extends BaseController {

	@Resource
	private UserService userService;

	@Resource
	private RedisService redisService;

	@Resource
	private IptvProperties properties;

	@Resource
	private ObjectMapper mapper;

	/**
	 * 登录
	 * @param loginName
	 * @param pwd
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Result doLogin(String loginName, String pwd, HttpServletRequest request) {
		Result result = new Result();

		try {
			//检查用户名
			if (StringUtils.isBlank(loginName)) {
				result.setCode(HttpConstant.CODE.SUCCESS);
				result.setRet(HttpConstant.RET.LOGIN_NAME_NULL);
				result.setMsg(HttpConstant.MESSAGES.LOGIN_NAME_NULL_INFO);
				return result;
			}

			//检查密码
			if (StringUtils.isBlank(pwd)) {
				result.setCode(HttpConstant.CODE.SUCCESS);
				result.setRet(HttpConstant.RET.LOGIN_NAME_NULL);
				result.setMsg(HttpConstant.MESSAGES.LOGIN_NAME_NULL_INFO);
				return result;
			}

			//判定账户是否被锁定
			if (redisService.exists(Constants.USER.REDIS_PASSWORD_ERROR_KEY+loginName)) {
				if (Constants.USER.USER_LOGIN_TIMES <= Integer.parseInt(redisService.get(Constants.USER.REDIS_PASSWORD_ERROR_KEY+loginName).toString())) {
					result.setCode(HttpConstant.CODE.SUCCESS);
					result.setRet(HttpConstant.RET.LOGIN_USER_LOCK);
					result.setMsg(HttpConstant.MESSAGES.LOGIN_USER_LOCK_INFO);
					return result;
				}
			}

			UserQuery userQuery=new UserQuery();
			userQuery.setLoginName(loginName);
			userQuery.setPwd(pwd);

			// 校验用户
			ServiceResult<UserVO> serviceResult = userService.checkLoginUser(userQuery);
			if (!serviceResult.isOk()) {
				//用户密码错误
				if (Constants.USER.PASSWORD_ERROR_CODE.equals(serviceResult.getMsgCode())) {
					if (redisService.exists(Constants.USER.REDIS_PASSWORD_ERROR_KEY+loginName)) {
						redisService.incrBy(Constants.USER.REDIS_PASSWORD_ERROR_KEY+loginName, 1);
					}else {
						redisService.set(Constants.USER.REDIS_PASSWORD_ERROR_KEY+loginName, "1",60*60*24L);
					}
				}

				result.setCode(HttpConstant.CODE.SUCCESS);
				result.setRet(HttpConstant.RET.LOGIN_INFO_ERROR);
				result.setMsg(HttpConstant.MESSAGES.LOGIN_INFO_ERROR_INFO);
				return result;
			}

			// 清空密码错误缓存信息
			redisService.del(Constants.USER.REDIS_PASSWORD_ERROR_KEY+loginName);

			// JWT 生成token
			String token = FebsUtil.encryptToken(JWTUtil.sign(loginName, MD5Util.MD5(pwd+Constants.USER.PASSWORD_ADD_WORD)));
			LocalDateTime expireTime = LocalDateTime.now().plusSeconds(properties.getShiro().getJwtTimeOut());
			String expireTimeStr = DateTimeUtils.formatFullTime(expireTime);
			JWTToken jwtToken = new JWTToken(token, expireTimeStr);

			// 用户信息存入redis
			this.saveTokenToRedis(serviceResult.getData(),jwtToken,request);

			// 生成前端需要的信息
			Map<String, Object> userInfo = this.generateUserInfo(jwtToken,serviceResult.getData());
			return new Result(userInfo);
		}catch (Exception e){
			log.error("登录出错:",e);
		}

		return result;
	}

	/**
	 * 存入redis
	 * @param user
	 * @param token
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String saveTokenToRedis(UserVO user, JWTToken token, HttpServletRequest request) throws Exception {
		String ip = IPUtil.getIpAddr(request);

		// 构建在线用户
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUsername(user.getUserName());
		activeUser.setIp(ip);
		activeUser.setToken(token.getToken());
		//	activeUser.setLoginAddress(AddressUtil.getCityInfo(ip));

		// zset 存储登录用户，score 为过期时间戳
		this.redisService.zadd(RedisKeyConstants.ACTIVE_USERS_ZSET_PREFIX, Double.valueOf(token.getExipreAt()), mapper.writeValueAsString(activeUser));
		// redis 中存储这个加密 token，key = 前缀 + 加密 token + .ip
		this.redisService.set(RedisKeyConstants.TOKEN_CACHE_PREFIX + token.getToken() + "."+ ip, token.getToken(), properties.getShiro().getJwtTimeOut() * 1000);

		log.info("activeUserId="+activeUser.getId());
		return activeUser.getId();
	}


	/**
	 * 生成前端需要的用户信息，包括：
	 * 1. token
	 * 2. Vue Router
	 * 3. 用户角色
	 * 4. 用户权限
	 * 5. 前端系统个性化配置信息
	 *
	 * @param token token
	 * @param user  用户信息
	 * @return UserInfo
	 */
	private Map<String, Object> generateUserInfo(JWTToken token, UserVO user) {
		String username = user.getUserName();
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("token", token.getToken());
		userInfo.put("exipreTime", token.getExipreAt());

		UserQuery userQuery=new UserQuery();
		userQuery.setUserKey(user.getUserKey());

		// 用户所有角色
		Set<String> roles = userService.getUserRoles(userQuery);
		userInfo.put("roles", roles);

		// 用户所有权限: 菜单、操作权限
		Set<String> permissions = userService.getUserPermission(userQuery);
		userInfo.put("permissions",permissions);

		// 用户所有数据资源权限
		// UserConfig userConfig = this.userManager.getUserConfig(String.valueOf(user.getUserKey()));
		// userInfo.put("config", userConfig);

		user.setPwd("it's a secret");
		userInfo.put("user",user);
		return userInfo;
	}

	/**
	 * 登出
	 * @param request
	 * @return
	 */
	@RequestMapping("/logout")
	@ResponseBody
	public Result doLogin(HttpServletRequest request) {
		ShiroUtils.logout();
		return new Result();
	}
}