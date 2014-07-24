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
		<title>代码生成向导</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath %>js/bootstrap/css/bootstrap.min.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath %>css/main.css" />
		<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.4.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/scrollable.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/sonic.js"></script>
		<script type="text/javascript">
			$(function() {

				var loaders = [{
					width: 100,
					height: 100,
					stepsPerFrame: 1,
					trailLength: 1,
					pointDistance: .02,
					fps: 30,
					fillColor: '#05E2FF',
					step: function(point, index) {
						this._.beginPath();
						this._.moveTo(point.x, point.y);
						this._.arc(point.x, point.y, index * 7, 0, Math.PI*2, false);
						this._.closePath();
						this._.fill();

					},
					path: [
						['arc', 50, 50, 30, 0, 360]
					]

					}

				];
				
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
				
				$("#wizard").scrollable( {
					onSeek : function(event, i) {
						$("#status li").removeClass("active").eq(i).addClass("active");
						if(i == 1){
							closeMask("loadTableDiv","fade");
							/**显示第二步div之前,先用遮罩将div遮住,等到后台数据以获取,再关闭该阴影*/
							var loadingDiv, a, container = document.getElementById('loadTableDiv');
							for (var i = -1, l = loaders.length; ++i < l;) {
								loadingDiv = document.getElementById('loadTableSubDiv');
								loadingDiv.className = 'l';
								a = new Sonic(loaders[i]);
								loadingDiv.appendChild(a.canvas);
								//container.appendChild(loadingDiv);
								a.canvas.style.marginTop = (150 - a.fullHeight) / 2 + 'px';
								a.canvas.style.marginLeft = (150 - a.fullWidth) / 2 + 'px';
								a.play();
							}
							showMask("loadTableDiv","fade");
						}

						if(i == 2){
							
							var loadingDiv, a, container = document.getElementById('generatorLoadingDiv');
							for (var i = -1, l = loaders.length; ++i < l;) {
								loadingDiv = document.getElementById('generatorLoadingSubDiv');
								loadingDiv.className = 'l';
								a = new Sonic(loaders[i]);
								loadingDiv.appendChild(a.canvas);
								//container.appendChild(loadingDiv);
								a.canvas.style.marginTop = (150 - a.fullHeight) / 2 + 'px';
								a.canvas.style.marginLeft = (150 - a.fullWidth) / 2 + 'px';
								a.play();
							}
							showMask("generatorLoadingDiv","fade");
						}
					},
					onBeforeSeek : function(event, i) {
						if (i == 1) {
							$("#firstSetpForm").attr("target","listTables").attr("action","generatorAction!getTableList.action").submit();
						} else if (i == 2) {
							$("#secondSetpForm").attr("target","generatorFinish").attr("action","generatorAction!generatorCode.action").submit();
						} else if (i == 3) {
						
						}
					}
				});
			});
			
			//弹出隐藏层
			function showMask(show_div,bg_div){
				document.getElementById(show_div).style.display='block';
				document.getElementById(bg_div).style.display='block' ;
				var bgdiv = document.getElementById(bg_div);
				bgdiv.style.width = document.body.scrollWidth;
				// bgdiv.style.height = $(document).height();
				$("#"+bg_div).height($(document).height());
			};

			//关闭弹出层
			function closeMask(show_div,bg_div){
				document.getElementById(show_div).style.display='none';
				document.getElementById(bg_div).style.display='none';
			};

			//全选
			function checkSelectedAll(obj){
				if(document.all["tableName"] == undefined){
					return false;
				}

				var length = document.all["tableName"].length;

				if(obj.checked == true){
					if(typeof(length) == "undefined"){
						if(document.all["tableName"].disabled == false){
							document.all["tableName"].checked = true;
						}
					}else{
						for(var i=0;i<length;i++){
							if(document.all["tableName"][i].disabled == false){
								document.all["tableName"][i].checked = true;
							}
						}
					}
				} else {
					if(typeof(length) == "undefined"){
						document.all["tableName"].checked = false;
					}else{
						for(var i=0;i<length;i++){
							document.all["tableName"][i].checked = false;
						}
					}
				}
			}

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

		</script>
	</head>

	<body>

		<div id="main">
			<h2 class="top_title">
				<a href="#">代码生成向导</a>
			</h2>
			<div id="wizard">
				<ul id="status">
					<li class="active">
						<strong>1.</strong>输入数据库信息
					</li>
					<li>
						<strong>2.</strong>选择数据表
					</li>
					<li>
						<strong>3.</strong>生成代码
					</li>
				</ul>

				<div class="items">
					<form method="post" id="firstSetpForm">
						<div class="page">
							<div class="scrollDiv" style="overflow-x: auto;overflow-y: scroll;height: 350px;" > 
								<p>
									<label>选择数据库类型：</label>
									<select name="dataBaseType" id="dataBaseType" class="span4">
										<option value="Mysql">Mysql</option>
										<option value="SQLServer2000">SQLServer2000</option>
										<option value="Oracle">Oracle</option>
									</select>
								</p>
								<p>
									<label>数据库:</label>
									<input type="text" id="dataBaseName" name="dataBaseName" class="input-xlarge" placeholder="输入数据库名称"/>
								</p>
								<p>
									<label>用户名:</label>
									<input type="text" id="username" name="username" class="input-xlarge" placeholder="输入数据库用户名"/>
								</p>
								<p>
									<label>密码:</label>
									<input type="password" id="password" name="password" class="input-xlarge" placeholder="输入数据库密码"/>
								</p>
								<p>
									<label>IP地址:</label>
									<input type="text" id="ip" name="ip" class="input-xlarge"placeholder="输入数据库IP地址" />
								</p>
								<p>
									<label>地址:</label>
									<input type="text" name="url" id="url"  class="input-xlarge"/>
								</p>
								<p>
									<label>驱动:</label>
									<input type="text" name="driver" id="driver" class="input-xlarge"/>
								</p>
								<p>
									<label>包名:</label>
									<input type="text" name="basepackage" id="basepackage" class="input-xlarge" placeholder="输入包名" />
								</p>
								<p>	
									<label>选择后端框架:</label>
									<select class="span4">
										<option>spring+hibernate+Struts2</option>
										<option>spring+hibernate+springMVC</option>
										<option>spring+MyBatis+Struts2</option>
										<option>spring+MyBatis+springMVC</option>
										<option>spring+IBatis+Struts2</option>
										<option>spring+IBatis+springMVC</option>
									</select>
								</p>
								<p>
									<label>选择前端框架:</label>
									<select class="span4">
										<option>easyui</option>
										<option>extjs</option>
										<option>bootstrap</option>
									</select> 
								</p>
								<br />
							</div>
							<div class="btn_nav">
								<input type="button" class="btn btn-primary right" value="下一步&raquo;" />
							</div>
						</div>
					</form>
					
					<div class="page" id="secondStepDiv">
						<iframe name="listTables" id="listTables" style="display:none;"></iframe>
						<div class="scrollDiv"  style="overflow-x: auto;overflow-y: scroll;height: 350px;" > 
							<form method="post" id="secondSetpForm">
								<table  border="0" cellpadding="0" cellspacing="0" width="100%" style="border-collapse: collapse">
									<thead>
										<tr height="30px;">
											<th class="listTableHead" width="10%;" style="border-bottom: none;">
												<input type="checkbox" name="deleteListAll" onclick="checkSelectedAll(this)" />
											</th>
											<th class="listTableHead" width="87%;" style="border-bottom: none;">
												<nobr>表名</nobr>
											</th>
										</tr>
									</thead>
								</table>
								
								<table  border="0" cellpadding="0" cellspacing="0" width="100%" id="tableContent" style="border-collapse:collapse">
								</table>
							</form>
						</div>
						<div class="btn_nav">
							<!-- <input type="button" class="btn btn-primary left" style="float: left"
								value="&laquo;上一步" />  -->
							<input type="button" class="btn btn-primary right" value="开始生成" />
						</div>
					</div>
					<div class="page">
						<iframe name="generatorFinish" id="generatorFinish" style="display:none;"></iframe>
						<h3>
							所有代码生成完毕
							<br />
							<em>点击<a href="D:\tomcat\apache-tomcat-6.0.18\webapps\generator01\out\1406101146703" target="_blank">这里</a>打开项目。</em>
						</h3>
						<br />
						<br />
						<br />
						<div class="btn_nav">
							<!-- <input type="button" class="btn btn-primary left" style="float: left"
								value="&laquo;上一步" />  -->
							<input type="button" class="btn btn-primary right" value="完成" />
						</div>
					</div>
				</div>
				<div id="fade" class="black_overlay"></div>
				<div id="loadTableDiv" class="white_content">
					<div id="loadTableSubDiv" align="center" style="padding-top: 6px;">
						<label style="color: white">
							<h2>正在查询数据表，请稍等...</h2>
						</label>
					</div>
				</div>
				<div id="generatorLoadingDiv" class="white_content">
					<div id="generatorLoadingSubDiv" align="center" style="padding-top: 6px;">
						<label style="color: white">
							<h1 >正在努力生成代码中...</h1>
						</label>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
