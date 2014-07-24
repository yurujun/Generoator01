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
				alert("sss");
				$("#tableContent",window.parent.document).html("");
				window.parent.closeMask("generatorLoadingDiv","fade");
			});
		</script>
	</head>
	<body>
	</body>
</html>