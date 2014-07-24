<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
<#assign classNameLowerCase = className?lower_case>   
<#assign pkJavaType = table.idColumn.javaType>   

package ${basepackage}.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ${basepackage}.pageModel.*;
import ${basepackage}.service.*;
@Controller
@RequestMapping("/${classNameLowerCase}")
public class ${className}Controller{
	
	private ${className}ServiceI ${classNameLower}Service;
	
	public ${className}ServiceI get${className}Service() {
		return ${classNameLower}Service;
	}

	@Autowired
	public void set${className}Service(${className}ServiceI ${classNameLower}Service) {
		this.${classNameLower}Service = ${classNameLower}Service;
	}
	
	/** binder用于bean属性的设置 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	/**
	 * 取得列表
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Json list(${className}Query ${classNameLower}Query) {
		Json j = new Json();
		try {
			j.setObj(this.${classNameLower}Service.find(${classNameLower}Query));
			j.setSuccess(true);
			j.setMsg("查询成功！");
		} catch (Exception e) {
			j.setMsg("查询失败，错误原因:"+e.getMessage()==null?"空指针异常":e.getMessage());
		}
		return j;
	}
	
	/**
	 * 添加
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(${className}Query ${classNameLower}Query) {
		Json j = new Json();
		try {
			${classNameLower}Service.add(${classNameLower}Query);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg("添加失败，错误原因:"+e.getMessage()==null?"空指针异常":e.getMessage());
		}
		return j;
	}
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(${className}Query ${classNameLower}Query) {
		Json j = new Json();
		try {
			${classNameLower}Service.delete(${classNameLower}Query);
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg("删除失败，错误原因:"+e.getMessage()==null?"空指针异常":e.getMessage());
		}
		return j;
	}
	/**
	 * 修改
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(${className}Query ${classNameLower}Query) {
		Json j = new Json();
		try {
			${classNameLower}Service.delete(${classNameLower}Query);
			j.setSuccess(true);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			j.setMsg("修改失败，错误原因:"+e.getMessage()==null?"空指针异常":e.getMessage());
		}
		return j;
	}
	
	/**
	 * 取得单条记录
	 * @return
	 */
	@RequestMapping("/getBy${className}")
	@ResponseBody
	public Json getBy${className}(${className}Query ${classNameLower}Query) {
		Json j = new Json();
		try {
			j.setObj(this.${classNameLower}Service.getBy${className}(${classNameLower}Query));
			j.setSuccess(true);
			j.setMsg("查询成功！");
		} catch (Exception e) {
			j.setMsg("查询失败，错误原因:"+e.getMessage()==null?"空指针异常":e.getMessage());
		}
		return j;
	}
	
	/**
	 * 页面跳转
	 * @return
	 */
	@RequestMapping("/${classNameLowerCase}")
	public String ${classNameLower}() {
		return "pages/${classNameLowerCase}";
	}
	
}

