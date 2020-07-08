package com.peiqi.admin.controller.system;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.peiqi.common.core.controller.BaseController;
import com.peiqi.common.core.domain.AjaxResult;
import com.peiqi.common.exception.user.CaptchaException;
import com.peiqi.common.utils.RedisUtil;
import com.peiqi.common.utils.ServletUtils;
import com.peiqi.common.utils.StringUtils;
import com.peiqi.system.domain.SysUser;
import com.peiqi.system.service.ISysUserService;

/**
 * 登录验证
 * 
 * @author STILL
 */
@Controller
public class SysLoginController extends BaseController {
	@Resource
	private ISysUserService sysUserService;

	@GetMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		// 如果是Ajax请求，返回Json字符串。
		if (ServletUtils.isAjaxRequest(request)) {
			return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
		}

		return "login";
	}

	/**
	 * 登录
	 * 
	 * @param username     用户名
	 * @param password     密码
	 * @param rememberMe   是否记住
	 * @param validateCode 验证码
	 * @return
	 */
	@PostMapping("/login")
	@ResponseBody
	public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe, String validateCode) {
		try {
			_validateCode(validateCode);
			_validatePwd(username, password);
			return success();
		} catch (AuthenticationException e) {
			String msg = "用户或密码错误";
			if (StringUtils.isNotEmpty(e.getMessage())) {
				msg = e.getMessage();
			}
			return error(msg);
		} catch (CaptchaException e) {
			String msg = "验证码错误";
			if (StringUtils.isNotEmpty(e.getMessage())) {
				msg = e.getMessage();
			}
			return error(msg);
		}
	}

	/**
	 * 验证密码
	 * 
	 * @param username 登录账户
	 * @param password 密码
	 * @throws AuthenticationException
	 */
	private void _validatePwd(String username, String password) throws AuthenticationException {
		SysUser sysUser = sysUserService.selectUserByLoginName(username);
		if (sysUser == null) {
			throw new AuthenticationException();
		}
		String hex = new Md5Hash(username + password + sysUser.getSalt()).toHex();
		if (!hex.equals(sysUser.getPassword())) {
			throw new AuthenticationException();
		}
	}

	/**
	 * 验证码验证
	 * 
	 * @param validateCode 验证码
	 * @throws CaptchaException
	 */
	private void _validateCode(String validateCode) throws CaptchaException {
		String sessionId = ServletUtils.getSession().getId();
		String _validateCode = RedisUtil.get(sessionId + "_VALIDATE_CODE");
		if (!validateCode.equals(_validateCode)) {
			throw new CaptchaException();
		}

	}

	@GetMapping("/unauth")
	public String unauth() {
		return "error/unauth";
	}
}
