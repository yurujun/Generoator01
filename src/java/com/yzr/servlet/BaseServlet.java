package com.yzr.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param resp
	 */
	public void writeJson(Object object, HttpServletResponse resp) {
		try {
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			resp.setContentType("text/html;charset=utf-8");
			System.out.println(json);
			resp.getWriter().write(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
