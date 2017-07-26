<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
  <head>
  	<base href="<%=basePath%>">
  	<meta charset="utf-8" />
  	<meta name="viewport" content="width=device-width,initial-scale=1.0" />
    <title>CRM北方产品自动生成</title>
    <%@include file="jsp/css.jsp" %>
    <link rel="stylesheet" type="text/css" href="css/page_index.css" />
  </head>
  <body>
	<div class="easyui-layout" style="height:100%;">
        <div id="banner" data-options="region:'north'">
        	<h1 style="color:white;margin-left:20px;">It's a sizzler!</h1>
        </div>
        <!-- 菜单 -->
        <div id="menu" data-options="region:'west',split:true" style="width:200px;">
        	<div class="easyui-accordion" style="height:100%;">
		        <div title="定时任务管理" style="overflow:auto;">
		        	<ul>
		        		<li><a data-options="qrtz/group" title="分组管理">分组管理</a></li>
						<li><a data-options="qrtz/job" title="定时任务">定时任务</a></li>
		        		<li><a data-options="qrtz/job/log" title="执行日志">执行日志</a></li>
		        	</ul>
		        </div>
		        <div title="数据扫描管理" style="overflow:auto;">
		        	<ul>
		        		<li><a data-options="scan/datasource" title="数据源管理">数据源管理</a></li>
		        		<li><a data-options="scan/import" title="导入管理">导入管理</a></li>
		        		<li><a data-options="scan/export" title="导出管理">导出管理</a></li>
		        		<li><a data-options="scan/group" title="分组管理">分组管理</a></li>
						<li><a data-options="scan/task" title="扫描任务管理">扫描任务管理</a></li>
					    <li><a data-options="scan/task/log" title="执行日志">执行日志</a></li>
					</ul>
		        </div>
		        <div title="帮助" data-options="iconCls:'icon-help'" style="padding:10px;">
		        </div>
	        </div>
        </div>
        <div data-options="region:'center'">
        	<div id="window" class="easyui-tabs" style="width:100%;height:100%;">
        		<div title="Help" data-options="iconCls:'icon-help',closable:true" style="padding:10px">
		            This is the help content.
		        </div>
        	</div>
        </div>
    </div>
	
	
  	<%@include file="jsp/js.jsp" %>
    <script type="text/javascript">
    	$(function(){
    		$('#menu .easyui-accordion ul a').click(function(){
    			var title=$(this).attr('title');
    			var href=$(this).attr('data-options');
    			var tab=$('#window').tabs('getTab',title);
    			if(tab){
    				$('#window').tabs('select',title);
    			}else{
    				$('#window').tabs('add',{
    					title:title,
    				    content:'<iframe class="plat" src="'+href+'" />',
    				    closable:true,
    				});
    			}
    		});
    	});
    
	</script>
  </body>
</html>




