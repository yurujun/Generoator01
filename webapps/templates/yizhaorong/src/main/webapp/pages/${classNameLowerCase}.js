<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>   
var searchForm;
var editRow = undefined;
var datagrid;
${'$'}(function() {
	searchForm = ${'$'}('#searchForm').form();
	datagrid = ${'$'}('#datagrid').datagrid({
		url : basePath + '/${classNameLowerCase}/find.action',
		iconCls : 'icon-save',
		pagination : true,
		pagePosition : 'bottom',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40 ],
		fit : true,
		fitColumns : false,
		nowrap : false,
		border : false,
		loadFilter: function(data){
			if (data.obj){
				return data.obj;
			} else {
				return data;
			}
		},
		<#list table.columns as column>
		<#if column.pk>
		idField : '${column.columnNameLower}',
		sortName : '${column.columnNameLower}',
		<#break>
		</#if>
		</#list>
		sortOrder : 'desc',
		columns : [ [
		<#list table.columns as column>
		{
			title : '${column.columnNameLower}',
			field : '${column.columnNameLower}',
			width : 150,
			sortable : true,
			editor : {
				<#if column.isDateTimeColumn>
				type : 'datetimebox',
				<#else>
				type : 'validatebox',
				</#if>
				options : {
					required : true,
					missingMessage : '请输入${column.columnNameLower}'
				}
			}
		}<#if column_has_next>,</#if><#--判断是否是最后一个元素-->
		</#list>
		] ],
		toolbar : [ {
			text : '增加',
			iconCls : 'icon-add',
			handler : function() {
				add();
			}
		}, '-', {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				del();
			}
		}, '-', {
			text : '修改',
			iconCls : 'icon-edit',
			handler : function() {
				edit();
			}
		}, '-', {
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				if (editRow != undefined) {
					datagrid.datagrid('endEdit', editRow);
				}
			}
		}, '-', {
			text : '取消编辑',
			iconCls : 'icon-undo',
			handler : function() {
				datagrid.datagrid('unselectAll');
				datagrid.datagrid('rejectChanges');
				editRow = undefined;
			}
		}, '-', {
			text : '取消选中',
			iconCls : 'icon-undo',
			handler : function() {
				datagrid.datagrid('unselectAll');
			}
		}, '-' ],
		onDblClickRow : function(rowIndex, rowData) {
			if (editRow != undefined) {
				datagrid.datagrid('endEdit', editRow);
			}

			if (editRow == undefined) {
				datagrid.datagrid('beginEdit', rowIndex);
				editRow = rowIndex;
				datagrid.datagrid('unselectAll');
			}
		},
		onAfterEdit : function(rowIndex, rowData, changes) {
			var inserted = datagrid.datagrid('getChanges', 'inserted');
			var updated = datagrid.datagrid('getChanges', 'updated');
			if (inserted.length < 1 && updated.length < 1) {
				editRow = undefined;
				datagrid.datagrid('unselectAll');
				return;
			}

			var url = '';
			if (inserted.length > 0) {
				url = '/${classNameLowerCase}/add';
			}
			if (updated.length > 0) {
				url = '/${classNameLowerCase}/edit';
			}
			${'$'}.ajax({
				url : url,
				data : rowData,
				dataType : 'json',
				type:"post",
				success : function(r) {
					if (r.success) {
						datagrid.datagrid('acceptChanges');
						${'$'}.messager.show({
							msg : r.msg,
							title : '成功'
						});
						editRow = undefined;
						datagrid.datagrid('reload');
					} else {
						/* datagrid.datagrid('rejectChanges'); */
						datagrid.datagrid('beginEdit', editRow);
						${'$'}.messager.alert('错误', r.msg, 'error');
					}
					datagrid.datagrid('unselectAll');
				}
			});

		},
		onRowContextMenu : function(e, rowIndex, rowData) {
			e.preventDefault();
			${'$'}(this).datagrid('unselectAll');
			${'$'}(this).datagrid('selectRow', rowIndex);
			${'$'}('#menu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		}
	});
});

function _search() {
	datagrid.datagrid('load', sjx.serializeObject(searchForm));
}
function cleanSearch() {
	datagrid.datagrid('load', {});
	searchForm.find('input').val('');
}
function add() {
	if (editRow != undefined) {
		datagrid.datagrid('endEdit', editRow);
	}

	if (editRow == undefined) {
		datagrid.datagrid('unselectAll');
		var row = {
			id : sjx.UUID()
		};
		datagrid.datagrid('appendRow', row);
		editRow = datagrid.datagrid('getRows').length - 1;
		datagrid.datagrid('selectRow', editRow);
		datagrid.datagrid('beginEdit', editRow);
	}
}
function del() {
	if (editRow != undefined) {
		datagrid.datagrid('endEdit', editRow);
		return;
	}
	var rows = datagrid.datagrid('getSelections');
	var ids = [];
	if (rows.length > 0) {
		${'$'}.messager.confirm('请确认', '您要删除当前所选项目？', function(r) {
			if (r) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push("'" + rows[i].id + "'");
				}
				${'$'}.ajax({
					url : '/${classNameLowerCase}/delete',
					data : {
						ids : ids.join(',')
					},
					dataType : 'json',
					success : function(response) {
						datagrid.datagrid('load');
						datagrid.datagrid('unselectAll');
						${'$'}.messager.show({
							title : '提示',
							msg : '删除成功！'
						});
					}
				});
			}
		});
	} else {
		${'$'}.messager.alert('提示', '请选择要删除的记录！', 'error');
	}
}
function edit() {
	var rows = datagrid.datagrid('getSelections');
	if (rows.length == 1) {
		if (editRow != undefined) {
			datagrid.datagrid('endEdit', editRow);
		}

		if (editRow == undefined) {
			// changeEditorEditRow();/*改变editor*/
			editRow = datagrid.datagrid('getRowIndex', rows[0]);
			datagrid.datagrid('beginEdit', editRow);
			datagrid.datagrid('unselectAll');
		}
	} else {
		${'$'}.messager.show({
			msg : '请选择一项进行修改！',
			title : '错误'
		});
	}
}