package com.bear.web.admin.interceptor;

import com.bear.web.admin.interceptor.base.BaseInterceptor;
import com.bear.web.controller.base.BaseController;
import com.bear.web.model.AdminUser;
import com.bear.web.service.AdminUserService;
import com.bear.web.utils.MD5;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;


public class LoginInterceptor extends BaseInterceptor implements Interceptor {
	private AdminUserService userService = new AdminUserService();
	private MD5 md5 = new MD5();

	@Override
	public void intercept(ActionInvocation ai) {
		boolean isValidate = false;
		AdminUser user = ai.getController().getSessionAttr("user");
		if (user != null && (userService.login(user.getStr("username"), user.getStr("password")) != null)) {
			isValidate = true;
		}
		if (isValidate) {
			ai.invoke();
		} else {
			BaseController base = (BaseController) ai.getController();
			base.sendErrorJSP("授权失败，请重新登陆");
		}
	}
}
