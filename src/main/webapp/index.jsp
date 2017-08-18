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
    <title>CRM北方产品部</title>
    <%@include file="jsp/css.jsp" %>
    <link rel="stylesheet" type="text/css" href="css/page_index.css" />
  </head>
  <body>
	<div class="easyui-layout" style="height:100%;">
        <div id="banner" data-options="region:'north'">
        	<h1 style="margin:0 10px;">It's a sizzler!</h1>
        </div>
        <!-- 菜单 -->
        <div id="menu" data-options="region:'west',split:true" style="width:200px;">
        	<div class="easyui-accordion" style="height:100%;">
		        <div title="定时任务管理" style="overflow:auto;">
		        	<ul>
						<li><a data-options="jsp/qrtz/list.jsp" title="定时任务">定时任务</a></li>
		        		<li><a data-options="qrtz/task/log" title="执行日志">执行日志</a></li>
		        	</ul>
		        </div>
		        <div title="数据扫描管理" style="overflow:auto;">
		        	<ul>
		        		<li><a data-options="jsp/scan/ds.jsp" title="数据源管理">数据源管理</a></li>
		        		<li><a data-options="jsp/scan/importer.jsp" title="导入管理">导入管理</a></li>
		        		<li><a data-options="jsp/scan/exporter.jsp" title="导出管理">导出管理</a></li>
						<li><a data-options="jsp/scan/task.jsp" title="扫描任务管理">扫描任务管理</a></li>
					    <li><a data-options="jsp/scan/taskLog.jsp" title="执行日志">执行日志</a></li>
					</ul>
		        </div>
		        <div title="帮助" data-options="iconCls:'icon-help'" style="padding:10px;">
		        </div>
	        </div>
        </div>
        <div data-options="region:'center'">
        	<div id="window" class="easyui-tabs" style="width:100%;height:100%;">
        		<div title="Help" data-options="iconCls:'icon-help',closable:true" style="padding:10px">
		           	 <h2>定时、数据扫描任务管理平台</h2>
		           	 <p>企业中大量充斥着定时任务，数据扫描任务。通常这些任务会分散在不同服务器或者项目中，这带来了很多问题。如何有效的集中企业中大量分散的定时任务，数据扫描任务，就是此系统关注的。</p>
		           	 <h3>性能测试可以参考这里</h3>
		           	 <p>https://github.com/playaround88/etl-lib/blob/master/README.md</p>
		           	 <h3>企业中可能遇到的问题</h3>
		           	 <ul>
		           	 	<li>任务不可见，难以集中管理。</li>
		           	 	<li>任务可维护性低，修改通常需要改动代码。</li>
		           	 	<li>大量的定时、扫描开发，带来重复的工作量，开发成本高。</li>
		           	 	<li>程序内的定时任务，通常造成系统不能多节点运行。</li>
		           	 	<li>各个项目数据扫描任务，千差万别，可靠性低，容易引入bug。</li>
		           	 </ul>
		           	 <h3>sizzler平台的优点</h3>
		           	 <ul>
		           	 	<li>可启动多个节点，定时任务不会重复执行。</li>
		           	 	<li>按照规定的模式开发数据扫描，可以保证，并行情况下数据不会重复处理</li>
		           	 	<li>数据扫描任务提供多种常用形式的适配，可以不需要任何代码开发。</li>
		           	 </ul>
		           	 <h3>数据扫描分核心概念</h3>
		           	 <ul>
		           	 	<li>数据源：标识数据的来源，基础的配置。例如数据库的url，用户名密码等</li>
		           	 	<li>数据导入：标识具体的数据，关联具体数据源，并具体指定数据的位置。例如数据库的具体表</li>
		           	 	<li>数据导出：标识数据的处理单元，具体的数据处理通常是有用户端处理。比如一个URL地址，那么系统就会把数据post到具体的URL.</li>
		           	 	<li>线程池：数据的处理过程通常是很短暂的，为了能够公用线程，提高性能，这里使用线程池。</li>
		           	 	<li>监听：负责监听数据导入的数据，并且提交任务到线程池。这部分是核心的处理逻辑，代码已固定，不需改动。</li>
		           	 </ul>
		           	 <h3>支持的数据源</h3>
		           	 <ul>
			           	 <li>数据库，目前支持mysql，oracle，理论可以支持所有jdbc兼容的数据库。</li>
			           	 <li>redis</li>
			           	 <li>URL</li>
		           	 </ul>
		           	 <h3>配置即可使用的数据导入</h3>
		           	 <ul>
		           	 	<li>数据库表</li>
		           	 	<li>redis队列</li>
		           	 	<li>URL地址</li>
		           	 </ul>
		           	 <h3>配置即可使用的数据导出</h3>
		           	 <ul>
		           	 	<li>redis队列</li>
		           	 	<li>URL地址</li>
		           	 </ul>
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




