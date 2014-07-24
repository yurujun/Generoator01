<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameLowerCase = className?lower_case>
package ${basepackage}.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import ${basepackage}.pageModel.*;
import ${basepackage}.service.*;

@Action(value = "${classNameLower}Action", results = { @Result(name = "${classNameLower}", location = "/pages/${classNameLowerCase}.jsp")})
public class ${className}Action extends BaseAction implements ModelDriven<${className}Query> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(${className}Action.class);

	private static final long serialVersionUID = 1L;
	private ${className}ServiceI ${classNameLower}Service;

	private ${className}Query ${classNameLower}Query = new ${className}Query();

	public ${className}ServiceI get${className}Service() {
		return ${classNameLower}Service;
	}

	@Autowired
	public void set${className}Service(${className}ServiceI ${classNameLower}Service) {
		this.${classNameLower}Service = ${classNameLower}Service;
	}

	public String ${classNameLower}() {
		return "${classNameLower}";
	}

	/**
	 * 取得列表
	 */
	public void find() {
		Json j = new Json();
		try {
			j.setObj(this.${classNameLower}Service.find(${classNameLower}Query));
			j.setMsg("取得列表成功");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.info(e.getMessage());
			j.setMsg("取得列表失败");
		}
		writeJson(j);
	}

	/**
	 * 添加
	 */
	public void add() {
		Json j = new Json();
		try {
			this.${classNameLower}Service.add(${classNameLower}Query);
			j.setMsg("添加成功");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.info(e.getMessage());
			j.setMsg("添加失败");
		}
		writeJson(j);
	}

	/**
	 * 删除
	 */
	public void delete() {
		Json j = new Json();
		try {
			this.${classNameLower}Service.delete(${classNameLower}Query);
			j.setSuccess(true);
			j.setMsg("删除成功");
		} catch (Exception e) {
			logger.info(e.getMessage());
			j.setMsg("删除失败");
		}
		writeJson(j);
	}
	/**
	 * 修改
	 */
	public void edit() {
		Json j = new Json();
		try {
			this.${classNameLower}Service.edit(${classNameLower}Query);
			j.setSuccess(true);
			j.setMsg("修改成功");
		} catch (Exception e) {
			logger.info(e.getMessage());
			j.setMsg("修改失败");
		}
		writeJson(j);
	}
	
	/**
	 * 取得一条数据
	 */
	public void getBy${className}(){
		Json j = new Json();
		try {
			j.setObj(this.${classNameLower}Service.getBy${className}(${classNameLower}Query));
			j.setMsg("获取数据成功");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.info(e.getMessage());
			j.setMsg("获取数据失败");
		}
		writeJson(j);
	}

	@Override
	public ${className}Query getModel() {
		return ${classNameLower}Query;
	}
}
