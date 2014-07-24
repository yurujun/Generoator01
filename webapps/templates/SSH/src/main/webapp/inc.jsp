<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${'$'}{pageContext.request.contextPath }"  scope="request" />
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!-- 设置网页图标-->
<%-- <link rel="shortcut icon" href="${'$'}{ctx}/css/images/logo/favicon.ico"> --%>
<!-- 设置地址栏图标-->
<%-- <link rel="icon" href="${'$'}{ctx}/css/images/logo/favicon.ico"> --%>
<script type="text/javascript" src="${'$'}{ctx}/jslib/jquery-easyui-1.3.6/jquery-1.8.0.min.js" charset="utf-8"></script>
<link rel="stylesheet" href="${'$'}{ctx}/jslib/jquery-easyui-1.3.6/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="${'$'}{ctx}/jslib/jquery-easyui-1.3.6/themes/icon.css" type="text/css"></link>
<script type="text/javascript" src="${'$'}{ctx}/jslib/jquery-easyui-1.3.6/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${'$'}{ctx}/jslib/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${'$'}{ctx}/jslib/sjxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
	var basePath="${'$'}{ctx}";
</script>
