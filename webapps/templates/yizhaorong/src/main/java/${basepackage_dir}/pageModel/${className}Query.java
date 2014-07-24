<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.pageModel;

import java.io.Serializable;
import java.util.*;
import ${basepackage}.pageModel.*;
public class ${className}Query  implements Serializable {
    private static final long serialVersionUID = 1L;
    
	<@generateFields/>
	<@generateProperties/>
}

<#macro generateFields>

	<#list table.columns as column>
	/** ${column.columnAlias} */
	<#if column.isDateTimeColumn && !column.contains("begin,start,end")>
	private ${column.possibleShortJavaType} ${column.columnNameLower};
	private ${column.possibleShortJavaType} ${column.columnNameLower}Begin;
	private ${column.possibleShortJavaType} ${column.columnNameLower}End;
	<#else>
	private ${column.possibleShortJavaType} ${column.columnNameLower};
	</#if>
	</#list>
	private String order;// 按什么排序(asc,desc)

	private Integer page;// 当前页

	private Integer rows;// 一页显示多少数据

	private String sort;// 排序字段名
</#macro>

<#macro generateProperties>
	<#list table.columns as column>
	<#if column.isDateTimeColumn && !column.contains("begin,start,end")>
	public ${column.possibleShortJavaType} get${column.columnName}() {
		return this.${column.columnNameLower}Begin;
	}
	
	public void set${column.columnName}(${column.possibleShortJavaType} ${column.columnNameLower}) {
		this.${column.columnNameLower} = ${column.columnNameLower};
	}	
	public ${column.possibleShortJavaType} get${column.columnName}Begin() {
		return this.${column.columnNameLower}Begin;
	}
	
	public void set${column.columnName}Begin(${column.possibleShortJavaType} ${column.columnNameLower}) {
		this.${column.columnNameLower}Begin = ${column.columnNameLower};
	}	
	
	public ${column.possibleShortJavaType} get${column.columnName}End() {
		return this.${column.columnNameLower}End;
	}
	
	public void set${column.columnName}End(${column.possibleShortJavaType} ${column.columnNameLower}) {
		this.${column.columnNameLower}End = ${column.columnNameLower};
	}
	
	<#else>
	public ${column.possibleShortJavaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	
	public void set${column.columnName}(${column.possibleShortJavaType} ${column.columnNameLower}) {
		this.${column.columnNameLower} = ${column.columnNameLower};
	}
	
	</#if>	
	</#list>
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
</#macro>



