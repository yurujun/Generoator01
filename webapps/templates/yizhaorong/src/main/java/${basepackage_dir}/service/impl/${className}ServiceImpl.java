<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${basepackage}.dao.BaseDaoI;
import ${basepackage}.model.*;
import ${basepackage}.pageModel.DataGrid;
import ${basepackage}.pageModel.*;
import ${basepackage}.service.${className}ServiceI;

@Service("${classNameLower}service")
public class ${className}ServiceImpl implements ${className}ServiceI {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(${className}ServiceImpl.class);

	private BaseDaoI<${className}> ${classNameLower}Dao;

	public BaseDaoI<${className}> get${className}Dao() {
		return ${classNameLower}Dao;
	}

	@Autowired
	public void set${className}Dao(BaseDaoI<${className}> ${classNameLower}Dao) {
		this.${classNameLower}Dao = ${classNameLower}Dao;
	}

	@Override
	public void add(${className}Query ${classNameLower}Query) throws Exception {
		${className} ${classNameLower} = new ${className}();
		BeanUtils.copyProperties(${classNameLower}Query, ${classNameLower});
		${classNameLower}Dao.save(${classNameLower});
	}

	@Override
	public DataGrid<${className}Query> find(${className}Query ${classNameLower}Query) throws Exception {
		StringBuffer hql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("from ${className} t ");
		addWhere(${classNameLower}Query, hql, params);
		DataGrid<${className}Query> ${classNameLower}Querys = new DataGrid<${className}Query>();
		List<${className}> list = ${classNameLower}Dao.find(hql, params);
		List<${className}Query> list1 = new ArrayList<${className}Query>();
		for (${className} tt : list) {
			${className}Query t = new ${className}Query();
			BeanUtils.copyProperties(tt, t);
			list1.add(t);
		}
		${classNameLower}Querys.setRows(list1);
		${classNameLower}Querys.setTotal(total(hql, params));
		return ${classNameLower}Querys;
	}

	private void addWhere(${className}Query ${classNameLower}Query, StringBuffer hql, Map<String, Object> params) throws Exception {
		String order = ${classNameLower}Query.getOrder();
		String sort = ${classNameLower}Query.getSort();
		hql.append(" where 1=1 ");
		<#list table.columns as column>
		<#if column.possibleShortJavaType == "String">
		if (${classNameLower}Query.get${column.columnName}() != null && !${classNameLower}Query.get${column.columnName}().trim().equals("")) {
			hql.append(" and t.${column.columnNameLower} = :${column.columnNameLower} ");
			params.put("${column.columnNameLower}", ${classNameLower}Query.get${column.columnName}().trim());
		}
		<#else>
		if (${classNameLower}Query.get${column.columnName}() != null) {
			hql.append(" and t.${column.columnNameLower} = :${column.columnNameLower} ");
			params.put("${column.columnNameLower}", ${classNameLower}Query.get${column.columnName}());
		}
		</#if>
		</#list>
		if (sort != null && order != null) {
			hql.append(" order by " + sort + " " + order + "");
		}
	}

	private Long total(StringBuffer hql, Map<String, Object> params) throws Exception {
		hql.insert(0, "select count(*) ");
		Long total = this.${classNameLower}Dao.count(hql, params);
		return total;
	}

	@Override
	public void delete(${className}Query ${classNameLower}Query) throws Exception {
		${className} ${classNameLower} = new ${className}();
		BeanUtils.copyProperties(${classNameLower}Query, ${classNameLower});
		${classNameLower}Dao.delete(${classNameLower});
	}

	@Override
	public void edit(${className}Query ${classNameLower}Query) throws Exception {
		${className} t = new ${className}();
		BeanUtils.copyProperties(${classNameLower}Query, t);
		this.${classNameLower}Dao.update(t);
	}

	/**
	 * 
	 */
	@Override
	public ${className} getBy${className}(${className}Query ${classNameLower}Query) throws Exception{
		StringBuffer hql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("from ${className} t ");
		hql.append("where 1=1 ");
		<#list table.columns as column>
		<#if column.pk>
		<#if column.possibleShortJavaType=="String">
		if (${classNameLower}Query.get${column.columnName}() != null && !${classNameLower}Query.get${column.columnName}().trim().equals("")) {
			hql.append(" and t.${column.columnNameLower} = :${column.columnNameLower} ");
			params.put("${column.columnNameLower}", ${classNameLower}Query.get${column.columnName}().trim());
		}
		<#else>
		if (${classNameLower}Query.get${column.columnName}() != null) {
			hql.append(" and t.${column.columnNameLower} = :${column.columnNameLower} ");
			params.put("${column.columnNameLower}", ${classNameLower}Query.get${column.columnName}());
		}
		</#if>
		</#if>
		</#list>
		return ${classNameLower}Dao.get(hql,params);
	}

}
