package com.generator.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.provider.db.table.TableFactory;
import cn.org.rapid_framework.generator.provider.db.table.model.Table;

import com.opensymphony.xwork2.ActionSupport;
import com.yzr.utils.XMLUtil;

public class GeneratorAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private String dataBaseType;
	
	private String username;
	
	private String password;
	
	private String url;
	
	private String driver;
	
	private String basepackage;
	
	private List<Table> tables;
	
	private String projectLocation;
	
	/**
	 * ��ȡ���ݿ��е����ݱ�
	 * @return
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public String getTableList(){
		try{
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
			String realPath = ServletActionContext.getServletContext().getRealPath("/");
			String dir = realPath + "/out/" + System.currentTimeMillis();
			map.put("outRoot", dir);
			XMLUtil.modify(map);
			GeneratorFacade g = new GeneratorFacade();
			g.printAllTableNames(); // ��ӡ���ݿ��еı�����
			//g.deleteOutRootDir(); // ɾ�������������Ŀ¼
			// g.generateByTable("table_name","template");
			// //ͨ�����ݿ�������ļ�,templateΪģ��ĸ�Ŀ¼
			tables = TableFactory.getInstance().getAllTables();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * ���ɴ���
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String generatorCode(){
		try{
			//���ݿ������е����ݱ�
			List<Table> tableList = new ArrayList<Table>(); 
			tableList = TableFactory.getInstance().getAllTables();
			//��Ҫ���ɴ���ı�,���û���ǰ̨ҳ��ѡ��ı�
			List<Table> generatoredTableList = new ArrayList<Table>();
			for(Table table : tables){
				if(table.getCheckId() != null){
					//���checkId==1,˵���û�ѡ���˸ñ�,Ȼ�������ݿ��еı�������ƵıȽ�,����û�ѡ��ı��������ݿ��еı�������,������ݿ��еĸ��ű���ӵ�������
					if(table.getCheckId().equals("1")){
						for(Table temp : tableList){
							if(temp.getSqlName().equals(table.getSqlName())){
								generatoredTableList.add(temp);
							}
						}
					}
				}
			}
			GeneratorFacade g = new GeneratorFacade();
			String realPath = ServletActionContext.getServletContext().getRealPath("/");
			g.generateByAllTable(realPath + "/template",generatoredTableList); // �Զ��������ݿ��е����б������ļ�,templateΪģ��ĸ�Ŀ¼
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "generatorSuccess";
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public List<Table> getTables() {
		return tables;
	}

	public String getDataBaseType() {
		return dataBaseType;
	}

	public void setDataBaseType(String dataBaseType) {
		this.dataBaseType = dataBaseType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getBasepackage() {
		return basepackage;
	}

	public void setBasepackage(String basepackage) {
		this.basepackage = basepackage;
	}

	public void setProjectLocation(String projectLocation) {
		this.projectLocation = projectLocation;
	}

	public String getProjectLocation() {
		return projectLocation;
	}

}
