<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case> 
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript" src="${'$'}{ctx}/pages/${classNameLowerCase }.js" charset="utf-8"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:'false',title:'查询条件'" style="height: 60px; overflow: hidden;" align="left">
		<form id="searchForm">
			<table>
				<tr>
					<#list table.columns as column>
					<#if column.pk>
					<th>${column.columnNameLower}：</th>
					<td><input name="${column.columnNameLower}" /></td>
					<#break>
					</#if>
					</#list>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" onclick="_search();" data-options="plain:true,iconCls:'icon-search'">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" onclick="cleanSearch();" data-options="plain:true,iconCls:'icon-cancel'">清空</a></td>
				</tr>
			</table>

		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="datagrid"></table>
	</div>
	<div id="menu" class="easyui-menu" style="width: 120px; display: none;">
		<div onclick="add();" data-options="iconCls:'icon-add'">增加</div>
		<div onclick="del();" data-options="iconCls:'icon-remove'">删除</div>
		<div onclick="edit();" data-options="iconCls:'icon-edit'">编辑</div>
	</div>
</body>
</html>