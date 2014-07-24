<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.4.js"></script>
		<title>Insert title here</title>
		<script type="text/javascript">
			$(function($) {
				$("#tableContent",window.parent.document).html("");
				var innerHtml = "";
				<s:iterator value="tables" var="table" status="status">  
					innerHtml += "<tr height=\"30px\">";
					innerHtml += "	<input type=\"hidden\" name=\"tables[${status.index}].sqlName\" value=\"<s:property value="sqlName"/>\" />";
					innerHtml += "	<td class=\"listTableText\" width=\"10%\"><input type=\"checkbox\" name=\"tables[${status.index}].checkId\" id=\"tableName\" value=\"1\" /></td>";
					innerHtml += "	<td class=\"listTableText\" width=\"87%;\"><s:property value="sqlName"/></td>";
					innerHtml += "</tr>";
				</s:iterator>
				$("#tableContent",window.parent.document).append(innerHtml);
				window.parent.closeMask("loadTableDiv","fade");
			});
		</script>
	</head>
	<body>
		 <s:iterator value="tables" var="table" status="status">  
			<s:property value="sqlName"/>
		</s:iterator>
	</body>
</html>