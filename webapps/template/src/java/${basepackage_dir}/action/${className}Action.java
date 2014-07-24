<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
<#assign classNameLowerCase = className?lower_case>   

package ${basepackage}.action;


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
public class ${className}Action{
	
	private ${className}ServiceI ${classNameLower}Service;
	
	public ${className}ServiceI get${className}Service() {
		return ${classNameLower}Service;
	}

	@Autowired
	public void set${className}Service(${className}ServiceI ${classNameLower}Service) {
		this.${classNameLower}Service = ${classNameLower}Service;
	}
	
	/** binder����bean���Ե����� */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	/**
	 * ȡ���б�
	 * @return
	 */
	@RequestMapping("/find")
	@ResponseBody
	public Json find(${className}Query ${classNameLower}Query) {
		Json j = new Json();
		try {
			j.setObj(this.${classNameLower}Service.find(${classNameLower}Query));
			j.setSuccess(true);
			j.setMsg("��ѯ�ɹ���");
		} catch (Exception e) {
			j.setMsg("��ѯʧ�ܣ�����ԭ��:"+e.getMessage()==null?"��ָ���쳣":e.getMessage());
		}
		return j;
	}
	
	/**
	 * ���
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(${className}Query ${classNameLower}Query) {
		Json j = new Json();
		try {
			${classNameLower}Service.add(${classNameLower}Query);
			j.setSuccess(true);
			j.setMsg("��ӳɹ���");
		} catch (Exception e) {
			j.setMsg("���ʧ�ܣ�����ԭ��:"+e.getMessage()==null?"��ָ���쳣":e.getMessage());
		}
		return j;
	}
	/**
	 * ɾ��
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(${className}Query ${classNameLower}Query) {
		Json j = new Json();
		try {
			${classNameLower}Service.delete(${classNameLower}Query);
			j.setSuccess(true);
			j.setMsg("ɾ���ɹ���");
		} catch (Exception e) {
			j.setMsg("ɾ��ʧ�ܣ�����ԭ��:"+e.getMessage()==null?"��ָ���쳣":e.getMessage());
		}
		return j;
	}
	/**
	 * �޸�
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(${className}Query ${classNameLower}Query) {
		Json j = new Json();
		try {
			${classNameLower}Service.delete(${classNameLower}Query);
			j.setSuccess(true);
			j.setMsg("�޸ĳɹ���");
		} catch (Exception e) {
			j.setMsg("�޸�ʧ�ܣ�����ԭ��:"+e.getMessage()==null?"��ָ���쳣":e.getMessage());
		}
		return j;
	}
	
	/**
	 * ȡ�õ�����¼
	 * @return
	 */
	@RequestMapping("/getBy${className}")
	@ResponseBody
	public Json getBy${className}(${className}Query ${classNameLower}Query) {
		Json j = new Json();
		try {
			j.setObj(this.${classNameLower}Service.getBy${className}(${classNameLower}Query));
			j.setSuccess(true);
			j.setMsg("��ѯ�ɹ���");
		} catch (Exception e) {
			j.setMsg("��ѯʧ�ܣ�����ԭ��:"+e.getMessage()==null?"��ָ���쳣":e.getMessage());
		}
		return j;
	}
	
	/**
	 * ҳ����ת
	 * @return
	 */
	@RequestMapping("/${classNameLowerCase}")
	public String ${classNameLower}() {
		return "pages//${classNameLowerCase}";
	}
	
}

