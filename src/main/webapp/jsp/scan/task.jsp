<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0" />
<title>扫描任务管理</title>
<%@include file="../css.jsp"%>
<style type="text/css">
	.dialog-form{
		padding:10px;
	}
	.dialog-form div{
		padding:5px 0px;
	}
</style>
</head>
<body>
	<%@include file="../header.jsp"%>

	<div id="content">
		<div class="container">
			<div id="tools">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'lnr lnr-pencil',plain:true" onclick="create();">新建</a>   
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'lnr lnr-highlight',plain:true" onclick="update();">修改</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'lnr lnr-trash',plain:true" onclick="del();">删除</a>
			</div>
			<table id="listTab"></table>
			
			<!-- 创建扫描任务dialog -->
			<div id="newDialog" class="easyui-dialog" title="扫描任务编辑" style="width:500px;height:400px;"   
        		data-options="iconCls:'lnr lnr-enter-down',resizable:false,modal:true,closed:true,
        			buttons:[{
        				text:'保存',
        				iconCls:'lnr lnr-bug',
        				plain:true,
        				handler:save,
        			}]">   
			    <form id="newForm" class="dialog-form" method="post">
			    	<input type="hidden" name="id" value="0" />
			    	<div>   
				        <label for="name">任务名称:</label><br />
				        <input class="easyui-validatebox" type="text" name="name" data-options="required:true" />   
				    </div>
				    <div>   
				        <label for="group">任务分组:</label><br />
				        <input class="easyui-validatebox" type="text" name="group" data-options="required:true" />   
				    </div>
				    <div>   
				        <label for="type">任务类型:</label><br />
				        <select name="type">
				        	<option value="dynmic">动态线程池</option>
				        	<option value="fixed">固定线程池</option>
				        </select>   
				    </div>
				    <div>   
				        <label for="state">任务初试状态:</label><br />
				        <select name="state">
				        	<option value="RUNNING">运行</option>
				        	<option value="PAUSED">暂停</option>
				        </select>   
				    </div>
				    <div>   
				        <label for="poolSize">线程池大小:</label><br />
				        <input class="easyui-validatebox" type="text" name="poolSize" data-options="required:true" />   
				    </div>
				    <div>   
				        <label for="fetchSize">每次数据加载数量:</label><br />
				        <input class="easyui-validatebox" type="text" name="fetchSize" data-options="required:true" />   
				    </div>
				    <div>   
				        <label for="sleepTime">休眠时间:</label><br />
				        <input class="easyui-validatebox" type="text" name="sleepTime" data-options="required:true" />   
				    </div>
				    
				    <div>
				    	<label for="impId">选择数据导入:</label><br />
				    	<input id="impId" class="easyui-combobox" name="impId" value="">  
				    </div>
				    <div>
				    	<label for="expId">选择数据导出:</label><br />
				    	<input id="expId" class="easyui-combobox" name="expId" value="">  
				    </div>
				    <div>   
				        <label for="description">扫描任务描述:</label><br />
				        <textarea name="description" rows="3"></textarea>
				    </div>
			    </form>
			</div>  
			<!-- end 扫描任务 -->
			
		</div>
	</div>

	<%@include file="../footer.jsp"%>
	<%@include file="../js.jsp"%>
	<script type="text/javascript">
		$(function() {
			$('#listTab').datagrid({
				url : 'scan/task/list',
				pagination:true,
				rownumbers:true,
				singleSelect:true,
				fitColumns:true,
				pageSize:20,
				pageList:[10,20,30,40,50],
				toolbar: '#tools',
				idField: 'ID',
				columns : [ [{
					field : 'ID',
					title : '编码',
					hidden:true,
				}, {
					field : 'TASK_NAME',
					title : '任务名称',
				}, {
					field : 'TASK_GROUP',
					title : '任务分组',
				}, {
					field : 'TASK_TYPE',
					title : '任务类型',
				}, {
					field : 'POOL_SIZE',
					title : '线程池大小',
				}, {
					field : 'FETCH_SIZE',
					title : '每次加载量',
				}, {
					field : 'SLEEP_TIME',
					title : '休眠时间',
				}, {
					field : 'TASK_STATE',
					title : '状态',
				}, {
					field : 'CTIME',
					title : '创建时间',
					formatter: function(value,row,index){
						if(value==0){
							return value;
						}
						var d=new Date(value)
						return d.format("yyyy-MM-dd hh:mm:ss");
					}
				},{
					field : 'aa',
					title : '操作',
					formatter: function(value,row,index){
						var btns='';
						if(row['TASK_STATE']=='PAUSED'){
							btns+='<input type="button" value="恢复" onclick="resume('+index+');" />';
						}else if(row['TASK_STATE']=='RUNNING'){
							btns+='<input type="button" value="暂停" onclick="pause('+index+');" />';
						}
						return btns;
					}
				}, {
					field : 'DESCRIPTION',
					title : '描述',
				}] ]
			});//表格格式化
			
			//下拉菜单初始化
			$('#impId').combobox({
				url:'scan/importer/list',
				valueField:'id',    
			    textField:'impName'
			});
			//下拉菜单初始化
			$('#expId').combobox({
				url:'scan/exporter/list',
				valueField:'id',    
			    textField:'expName'
			});
		});
		
			
		function create(){
			$('#newDialog').dialog('open');
		}
		function save(){
			$('#newForm').form('submit',{
				url:'scan/task/save',
				onSubmit:function(param){
				},
				success:function(data){
					var data = eval('(' + data + ')');
		            alert(data.message); 
			        if (data.success){
			            $('#newDialog').dialog('close');
			            $('#listTab').datagrid('reload');
			        }
				}
			});
		}
		function del(){
			var row=$('#listTab').datagrid('getSelected');
			if(!row){
				alert('未选中行');
				return;
			}
			$.post('scan/task/del',{
				id:row['ID']
			},function(data){
				alert(data.message);
				if(data.success){
					$('#listTab').datagrid('reload');
				}
			},'json');
		}
	</script>
</body>
</html>