package com.peiqi.admin.controller.system;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.peiqi.common.core.controller.BaseController;
import com.peiqi.common.utils.RedisUtil;

/**
 * 图片验证码（支持算术形式）
 * 
 * @author STILL
 */
@Controller
@RequestMapping("/captcha")
public class SysCaptchaController extends BaseController {
	@Resource(name = "captchaProducer")
	private Producer captchaProducer;

	@Resource(name = "captchaProducerMath")
	private Producer captchaProducerMath;

	/**
	 * 验证码生成
	 */
	@GetMapping(value = "/captchaImage")
	public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) {
		ServletOutputStream out = null;
		try {
			String sessionId = request.getSession().getId();
			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache");
			response.setContentType("image/jpeg");

			String type = request.getParameter("type");
			String capStr = null;
			String code = null;
			BufferedImage bi = null;
			if ("math".equals(type)) {
				String capText = captchaProducerMath.createText();
				capStr = capText.substring(0, capText.lastIndexOf("@"));
				code = capText.substring(capText.lastIndexOf("@") + 1);
				bi = captchaProducerMath.createImage(capStr);
			} else if ("char".equals(type)) {
				capStr = code = captchaProducer.createText();
				bi = captchaProducer.createImage(capStr);
			}
			this.cacheValidateCode(sessionId, code);
			out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			out.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 缓存验证码
	 * 
	 * @param sessionId		浏览器标识
	 * @param validateCode	验证码
	 */
	private void cacheValidateCode(String sessionId, String validateCode) {
		String key = sessionId + "_VALIDATE_CODE";
		RedisUtil.setEx(key, validateCode, 5, TimeUnit.MINUTES);
	}
}