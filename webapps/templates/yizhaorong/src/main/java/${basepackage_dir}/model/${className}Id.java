${gg.setIgnoreOutput(table.pkCount <= 1)}

<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.model;

import javax.persistence.*;
@Embeddable
public class ${className}Id implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
<#list table.columns as column>
	<#if column.pk>
	private ${column.javaType} ${column.columnNameLower};
	</#if>
</#list>

<@generateConstructor className+"Id"/>
	
<#list table.columns as column>
	<#if column.pk>
	
	public void set${column.columnName}(${column.javaType} value) {
		this.${column.columnNameLower} = value;
	}
	
	<#if column.isDateTimeColumn>
	@Temporal(TemporalType.DATE)
	</#if>
	@Column(name = "${column.sqlName}", unique = ${column.unique?string}, nullable = ${column.nullable?string}, insertable = true, updatable = true, length = ${column.size})	
	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	</#if>
</#list>

}