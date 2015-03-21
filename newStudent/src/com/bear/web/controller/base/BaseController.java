package com.bear.web.controller.base;

import com.bear.web.config.WebConfig;
import com.bear.web.enums.ResultCode;
import com.bear.web.service.TipService;
import com.bear.web.utils.MD5;
import com.bear.web.xmpp.HttpTookit;
import com.jfinal.core.Controller;


import java.util.Map;

public class BaseController extends Controller {

	/**
	 * 下载头像
	 * 
	 * @param url
	 * @param dir
	 */
	public void downloadImage(final String url, final String dir) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				HttpTookit.downloadFromUrl(url, dir);
			}
		};
		thread.start();
	}

	public void sendErrorJSP(String message) {
		setAttr("message", message);
		renderJsp("/admin/error.jsp");
	}

	/**
	 * 发送回复给客户端
	 * 
	 * @param params
	 */
	public void sendResult(int code, String message, Map params) {
		setAttr("code", code);
		setAttr("message", message);
		if (params != null)
			setAttrs(params);
		renderJson();
	}

	/**
	 * 非空参数检测 当存在非空参数为空时返回 401
	 * 
	 * @param arg
	 */
	public boolean checkParaNULL(String... arg) {
		if (arg != null) {
			for (int i = 0; i < arg.length; i++) {
				if (null == arg[i] || "".equals(arg[i])) {
					sendResult(ResultCode.CODE_401.code, TipService.getKey(ResultCode.CODE_401.msg, this), null);
					return false;
				}
			}
		}
		return true;
	}

	public boolean stringIsNull(String arg) {
		if (null == arg || "".equals(arg)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断获取的数字是否为-1
	 * 
	 * @param num
	 * @return
	 */
	public boolean checkNum(int... num) {
		if (num != null) {
			for (int i = 0; i < num.length; i++) {
				if (num[i] == -1) {
					sendResult(ResultCode.CODE_401.code, ResultCode.CODE_401.msg, null);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 创建Token
	 * 
	 * @param uuid
	 * @param timestamp
	 * @return
	 */
	public String createToken(String uuid, long timestamp) {
		MD5 md5 = new MD5();
		return md5.getMD5ofStr(uuid + System.nanoTime() + "geetion");
	}

	/**
	 * 创建密码
	 * 
	 * @param uuid
	 * @return
	 */
	public String createPassword(String uuid) {
		MD5 md5 = new MD5();
		return md5.getMD5ofStr(uuid);
	}

	@Override
	public Controller setAttrs(Map<String, Object> attrMap) {
		for (Map.Entry<String, Object> entry : attrMap.entrySet()) {
			if (entry.getClass().getName().equals(String.class.getName())) {
				String strValue = (String) entry.getValue();
				if (strValue.startsWith("/upload/")) {
					entry.setValue(WebConfig.DOMAIN + strValue);
				}
			}
			getRequest().setAttribute(entry.getKey(), entry.getValue());
		}
		return this;
	}

}
