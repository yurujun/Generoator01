<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import ${basepackage}.model.*;
import ${basepackage}.pageModel.DataGrid;
import ${basepackage}.pageModel.*;

public interface ${className}ServiceI{
	/**
	 * @param ${classNameLower}Query
	 */
	public void add(${className}Query ${classNameLower}Query) throws Exception;

	/**
	 * @param ${classNameLower}Query
	 * @return
	 */
	public DataGrid<${className}Query> find(${className}Query ${classNameLower}Query) throws Exception;
	
	/**
	 * @param ${classNameLower}Query
	 */
	public void delete(${className}Query ${classNameLower}Query) throws Exception;
	
	/**
	 * @param ${classNameLower}Query
	 */
	public void edit(${className}Query ${classNameLower}Query) throws Exception;

	/**
	 * @param ${classNameLower}Query
	 */
	public ${className} getBy${className}(${className}Query ${classNameLower}Query) throws Exception;
}
