$(function() {
	changeURL();
	$("#dataBaseType").change(function() {
		changeURL();
	});
	$("#dataBaseName").focusout(function() {
		changeURL();
	});
	$("#btn").click(function() {
		$("#processing").modal('toggle');
		sub();
	});
	$("#processing").modal({
		backdrop : 'static',
		show : false
	});
	$("#tip").modal({
		backdrop : 'static',
		show : false
	});
});

function changeURL() {
	var dataBaseType = $("#dataBaseType").val();
	var dataBaseName = $("#dataBaseName").val();
	var ip = $("#ip").val();
	if ($.trim(ip) == "") {
		ip = "localhost";
	}
	if (dataBaseType == "Mysql") {
		$("#url").val("jdbc:mysql://" + ip + ":3306/" + dataBaseName);
		$("#driver").val("com.mysql.jdbc.Driver");
	} else if (dataBaseType == "SQLServer2000") {
		$("#url").val("jdbc:microsoft:sqlserver://" + ip + ":1433;" + "DatabaseName=" + dataBaseName);
		$("#driver").val("com.microsoft.jdbc.sqlserver.SQLServerDriver");
	} else if (dataBaseType == "Oracle") {
		$("#url").val("jdbc:oracle:thin:@" + ip + ":1521:" + dataBaseName);
		$("#driver").val("oracle.jdbc.driver.OracleDriver");
	}
}

function sub() {
	$.ajax({
		url : "generatorServlet!generator",
		data : $("#basicForm").serialize(),
		dataType : "json",
		type : "post",
		success : function(r) {
			$("#basepackage").val("");
			$("#processing").modal("toggle");
			if (r.success) {
				$("#tipBody").html(r.msg + "&nbsp;<a href='" + r.obj.download + "'>" + r.obj.name + "</a>");
			} else {
				$("#tipBody").html(r.msg);
			}
			$("#tip").modal("toggle");
		},
		error : function(r) {
			$("#processing").modal("toggle");
			$("#tipBody").html("请求出错");
			$("#tip").modal("toggle");
		}
	});
}