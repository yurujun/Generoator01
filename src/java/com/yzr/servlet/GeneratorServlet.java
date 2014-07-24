package com.yzr.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.yzr.utils.XMLUtil;
import com.yzr.utils.ZipUtils;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;
import cn.org.rapid_framework.generator.provider.db.DataSourceProvider;


public class GeneratorServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dispatcher(req, resp);
		System.out.println(req.getParameter("dataBaseType"));
		req.setAttribute("test", "test");
	}

	/**
	 * 分发器，用于判断用户请求的是哪一个方法
	 * 
	 * @param req
	 * @param resp
	 */
	private void dispatcher(HttpServletRequest req, HttpServletResponse resp) {
		String method = req.getServletPath().substring(req.getServletPath().lastIndexOf("!") + 1);
		if (method.equalsIgnoreCase("generator")) {
			this.generator(req, resp);
		}
	}

	private void generator(HttpServletRequest req, HttpServletResponse resp) {
		Json j = new Json();
		j.setMsg("生成失败");
		try {
			String dataBaseType = req.getParameter("dataBaseType");
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String url = req.getParameter("url");
			String driver = req.getParameter("driver");
			String basepackage = req.getParameter("basepackage");
			if (basepackage == null || basepackage.trim().equals("")) {
				basepackage = "com.demo";
			}
			if (dataBaseType.toUpperCase().equals("MYSQL")) {
				url += "?useUnicode=true&amp;characterEncoding=UTF-8";
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("jdbc.username", username);
			map.put("jdbc.password", password);
			map.put("jdbc.url", url);
			map.put("jdbc.driver", driver);
			map.put("basepackage", basepackage);
			String realPath = req.getSession().getServletContext().getRealPath("/");
			String dir = realPath + "/out/" + System.currentTimeMillis();
			map.put("outRoot", dir);
			XMLUtil.modify(map);
			DataSourceProvider.connection = null;
			DataSourceProvider.dataSource = null;
			GeneratorProperties.reload();
			GeneratorFacade g = new GeneratorFacade();
			// g.printAllTableNames(); // 打印数据库中的表名称
			g.deleteOutRootDir(); // 删除生成器的输出目录
			// g.generateByTable("table_name","template");
			// //通过数据库表生成文件,template为模板的根目录
			//g.generateByAllTable(realPath + "/template"); // 自动搜索数据库中的所有表并生成文件,template为模板的根目录
			// g.generateByClass(Blog.class,"template_clazz");
			// g.deleteByTable("table_name", "template"); //删除生成的文件
			FileUtils.copyDirectory(new File(realPath + "/easyui/jslib"), new File(dir + "/src/main/webapp/jslib"));
			ZipUtils zip = new ZipUtils();
			zip.doZip(dir);
			File file = new File(dir);
			map.put("download", req.getContextPath() + "/out/" + file.getName() + ".zip");
			map.put("name", file.getName() + ".zip");
			if (file.exists()) {
				FileUtils.deleteDirectory(file);
			}
			j.setObj(map);
			j.setSuccess(true);
			j.setMsg("生成成功");
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		writeJson(j, resp);
	}
}
