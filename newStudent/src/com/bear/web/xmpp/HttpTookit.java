package com.bear.web.xmpp;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpTookit {
	/**
	 * 从URL中下载文件
	 * 
	 * @param url
	 * @param dir
	 * @return
	 */
	public static boolean downloadFromUrl(String url, String dir) {
		try {
			URL httpurl = new URL(url);
			String fileName = "head.jpg";
			System.out.println(fileName);
			File f = new File(dir + fileName);
			FileUtils.copyURLToFile(httpurl, f);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 从URL中获取文件名
	 * 
	 * @param url
	 * @return
	 */
	public static String getFileNameFromUrl(String url) {
		String name = new Long(System.currentTimeMillis()).toString() + ".X";
		int index = url.lastIndexOf("/");
		if (index > 0) {
			name = url.substring(index + 1);
			if (name.trim().length() > 0) {
				return name;
			}
		}
		return name;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, Map<String, String> paramSet) {
		String result = "";
		String param = "";
		BufferedReader in = null;
		try {
			if (paramSet != null) {
				for (Entry<String, String> set : paramSet.entrySet()) {
					param += set.getKey() + "=" + set.getValue() + "&";
				}
				param = param.substring(0, param.length() - 1);
			}
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("Accept", "	text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("Connection", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, Map<String, String> paramSet) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		String param = "";
		try {
			if (paramSet != null) {
				for (Entry<String, String> set : paramSet.entrySet()) {
					param += set.getKey() + "=" + set.getValue() + "&";
				}
			}
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) connection;
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.10) Gecko/20100914 Firefox/3.6.10");
			// 发送POST请求必须设置如下两行
			httpConn.setDoOutput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(httpConn.getOutputStream());
			// 发送请求参数
			if (!"".equals(param))
				out.print(param.substring(0, param.length() - 1));
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String urlConnectionPost(String tourl, Map<String, String> paramSet) {
		BufferedReader in = null;
		OutputStreamWriter wr = null;
		URL url;
		String data = "";
		String result = "";
		try {
			url = new URL(tourl);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(1000 * 5);
			// 当存在post的值时，才打开OutputStreamWriter
			if (paramSet != null) {
				for (Entry<String, String> set : paramSet.entrySet()) {
					data += set.getKey() + "=" + set.getValue() + "&";
				}
				data = data.substring(0, data.length() - 1);
			}
			if (!"".equals(data) && data.toString().trim().length() > 0) {
				wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
				wr.write(data.toString());
				wr.flush();
			}
			// Get the response
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (wr != null) {
					wr.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
}
