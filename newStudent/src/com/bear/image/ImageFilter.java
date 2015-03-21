package com.bear.image;

import com.mindate.plugin.image.handler.ImageHandler;
import com.mindate.plugin.image.util.FileUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 图片处理核心过滤器
 * 
 * @author rex
 * 
 */
public class ImageFilter implements Filter {

	private int contextPathLength;

	private ImageHandler imgHandler = ImageHandler.single();

	/**
	 * init
	 */
	public void init(FilterConfig config) throws ServletException {
		String contextPath = config.getServletContext().getContextPath();
		contextPathLength = (contextPath == null || "/".equals(contextPath) ? 0 : contextPath.length());
	}

	/**
	 * dofilter
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String target = request.getRequestURI().replaceFirst(request.getContextPath(), "");
		if (contextPathLength != 0) {
			target = request.getRequestURI().substring(contextPathLength);
		}
		String fileName = target.substring(target.lastIndexOf("/") + 1);
		if (isImg(FileUtil.getSuffix(fileName)) && null != request.getQueryString()) {
			res = imgHandler.handler(target, request, response);
		}
		chain.doFilter(req, res);
	}

	/**
	 * 是否是图片类型
	 * 
	 * @param fileName
	 * @return
	 */
	private boolean isImg(String fileName) {
		Pattern p = Pattern.compile("^(jpeg|jpg|png|gif|bmp|webp|TMP|JPG|JPEG|PNG|tmd)([\\w-./?%&=]*)?$");
		Matcher m = p.matcher(fileName);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

}
