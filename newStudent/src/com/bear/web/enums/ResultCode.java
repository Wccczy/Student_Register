package com.bear.web.enums;

public enum ResultCode {
	CODE_401(401, "非法请求，参数错误"),
	CODE_402(402, "未授权，token过期或者无效"),
	CODE_403(403, "Method使用错误，请查看API"),
	CODE_404(404, "未找到访问地址"),
	CODE_500(500, "系统内部错误"),
	CODE_200(200, "访问成功");
	
	public int code;
	public String msg;

	ResultCode(int code, String msg) {
		this.msg = msg;
		this.code = code;
	}
}
