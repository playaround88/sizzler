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
<title>模板</title>
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
				<a href="javascript:void(0)" class="easyui-menubutton" data-options="menu:'#mm',iconCls:'lnr lnr-pencil'">新建</a>   
				<div id="mm" style="width:150px;">   
				    <div data-options="iconCls:'lnr lnr-clock'" onclick="newSimpleTask();">简单任务</div>   
				    <div class="menu-sep"></div>   
				    <div data-options="iconCls:'lnr lnr-calendar-full'" onclick="newCronTask();">Cron任务</div>   
				</div> 
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'lnr lnr-trash',plain:true" onclick="unscheduleTask();">删除</a>
			</div>
			<table id="taskTab"></table>
			
			<!-- 创建简单任务dialog -->
			<div id="stDialog" class="easyui-dialog" title="新建简单任务" style="width:400px;height:320px;"   
        		data-options="iconCls:'lnr lnr-clock',resizable:false,modal:true,closed:true,
        			buttons:[{
        				text:'运行任务',
        				iconCls:'lnr lnr-bug',
        				plain:true,
        				handler:saveSimpleTask,
        			}]">   
			    <form id="stForm" class="dialog-form" method="post">
			    	<div>   
				        <label for="jobName">任务名称:</label>   
				        <input class="easyui-validatebox" type="text" name="jobName" data-options="required:true" />   
				    </div>   
				    <div>   
				        <label for="group">任务分组:</label>   
				        <input class="easyui-validatebox" type="text" name="group" data-options="required:true" />   
				    </div>
				    <div>   
				        <label for="notify">通知地址:</label>   
				        <input class="easyui-validatebox" type="text" name="notify" data-options="required:true,validType:'url'" />   
				    </div>
				    <div>   
				        <label for="intervalInSeconds">时间间隔:</label>   
				        <input class="easyui-validatebox" type="text" name="intervalInSeconds" data-options="required:true" />   
				    </div>
				    <div>   
				        <label for="description">任务描述:</label>
				        <textarea name="description" rows="3"></textarea>
				    </div>
			    </form>  
			</div>  
			<!-- end 创建简单任务 -->
			<!-- 创建cron任务dialog -->
			<div id="ctDialog" class="easyui-dialog" title="新建cron任务" style="width:400px;height:320px;"   
        		data-options="iconCls:'lnr lnr-calendar-full',resizable:false,modal:true,closed:true,
        			buttons:[{
        				text:'运行任务',
        				iconCls:'lnr lnr-bug',
        				plain:true,
        				handler:saveCronTask,
        			}]">   
			    <form id="ctForm" method="post">
			    	<div>   
				        <label for="jobName">任务名称:</label>   
				        <input class="easyui-validatebox" type="text" name="jobName" data-options="required:true" />   
				    </div>   
				    <div>   
				        <label for="group">任务分组:</label>   
				        <input class="easyui-validatebox" type="text" name="group" data-options="required:true" />   
				    </div>
				    <div>   
				        <label for="notify">通知地址:</label>   
				        <input class="easyui-validatebox" type="text" name="notify" data-options="required:true,validType:'url'" />   
				    </div>
				    <div>   
				        <label for="cronExpression">cron表达式:</label>   
				        <input class="easyui-validatebox" type="text" name="cronExpression" data-options="required:true" />   
				    </div>
				    <div>   
				        <label for="description">任务描述:</label>
				        <textarea name="description" rows="3"></textarea>
				    </div>
			    </form>    
			</div>  
			<!-- end 创建cron任务 -->
		</div>
	</div>

	<%@include file="../footer.jsp"%>
	<%@include file="../js.jsp"%>
	<script type="text/javascript">
		function newSimpleTask(){
			$('#stDialog').dialog('open');
		}
		function saveSimpleTask(){
			$('#stForm').form('submit',{
				url:'qrtz/task/add/simple',
				onSubmit:function(param){
					
				},
				success:function(data){
					var data = eval('(' + data + ')');
			        if (data.success){    
			            alert(data.message); 
			            $('#stDialog').dialog('close');
			            $('#taskTab').datagrid('reload');
			        }else{
			        	alert(data.message);
			        }  
				}
			});
		}
		function newCronTask(){
			$('#ctDialog').dialog('open');
		}
		function saveCronTask(){
			$('#stForm').form('submit',{
				url:'qrtz/task/add/cron',
				onSubmit:function(param){
					
				},
				success:function(data){
					var data = eval('(' + data + ')');
			        if (data.success){    
			            alert(data.message); 
			            $('#stDialog').dialog('close');
			            $('#taskTab').datagrid('reload');
			        }else{
			        	alert(data.message);
			        }  
				}
			});
		}
		function unscheduleTask(){
			var row=$('#taskTab').datagrid('getSelected');
			if(!row){
				alert('未选中行');
				return;
			}
			$.post('qrtz/task/unschedule',{
				jobName:row['JOB_NAME'],
				group:row['JOB_GROUP']
			},function(data){
				alert(data.message);
				if(data.success){
					$('#taskTab').datagrid('reload');
				}
			},'json');
		}
		function pauseTask(index){
			$('#taskTab').datagrid('selectRow',index);
			var row=$('#taskTab').datagrid('getSelected');
			if(!row){
				alert('未选中行');
				return;
			}
			$.post('qrtz/task/pause',{
				jobName:row['JOB_NAME'],
				group:row['JOB_GROUP']
			},function(data){
				alert(data.message);
				if(data.success){
					$('#taskTab').datagrid('reload');
				}
			},'json');
		}
		function resumeTask(index){
			$('#taskTab').datagrid('selectRow',index);
			var row=$('#taskTab').datagrid('getSelected');
			if(!row){
				alert('未选中行');
				return;
			}
			$.post('qrtz/task/resume',{
				jobName:row['JOB_NAME'],
				group:row['JOB_GROUP']
			},function(data){
				alert(data.message);
				if(data.success){
					$('#taskTab').datagrid('reload');
				}
			},'json');
		}
		function triggerTask(index){
			$('#taskTab').datagrid('selectRow',index);
			var row=$('#taskTab').datagrid('getSelected');
			console.log(row);
			if(!row){
				alert('未选中行');
				return;
			}
			$.post('qrtz/task/trigger',{
				jobName:row['JOB_NAME'],
				group:row['JOB_GROUP']
			},function(data){
				alert(data.message);
			},'json');
		}
		$(function() {
			$('#taskTab').datagrid({
				url : 'qrtz/task/list',
				pagination:true,
				rownumbers:true,
				singleSelect:true,
				fitColumns:true,
				pageSize:20,
				pageList:[10,20,30,40,50],
				toolbar: '#tools',
				columns : [ [ {
					field : 'JOB_NAME',
					title : '任务名称',
				}, {
					field : 'JOB_GROUP',
					title : '任务分组',
				}, {
					field : 'NEXT_FIRE_TIME',
					title : '下次执行时间',
					formatter: function(value,row,index){
						if(value==0){
							return value;
						}
						var d=new Date(value)
						return d.format("yyyy-MM-dd hh:mm:ss");
					}
				}, {
					field : 'PREV_FIRE_TIME',
					title : '上次执行时间',
					formatter: function(value,row,index){
						if(value==0){
							return value;
						}
						var d=new Date(value)
						return d.format("yyyy-MM-dd hh:mm:ss");
					}
				}, {
					field : 'TRIGGER_STATE',
					title : '状态',
				}, {
					field : 'TRIGGER_TYPE',
					title : '触发类型',
				}, {
					field : 'START_TIME',
					title : '开始时间',
					formatter: function(value,row,index){
						if(value==0){
							return value;
						}
						var d=new Date(value)
						return d.format("yyyy-MM-dd hh:mm:ss");
					}
				}, {
					field : 'END_TIME',
					title : '结束时间',
					formatter: function(value,row,index){
						if(value==0){
							return value;
						}
						var d=new Date(value)
						return d.format("yyyy-MM-dd hh:mm:ss");
					}
				}, {
					field : 'OP',
					title : '操作',
					formatter: function(value,row,index){
						var btns='<input type="button" value="执行" onclick="triggerTask('+index+');" />';
						if(row['TRIGGER_STATE']=='PAUSED'){
							btns+='<input type="button" value="恢复" onclick="resumeTask('+index+');" />';
						}else if(row['TRIGGER_STATE']=='ACQUIRED'){
							btns+='<input type="button" value="暂停" onclick="pauseTask('+index+');" />';
						}
						return btns;
					}
				}, {
					field : 'DESCRIPTION',
					title : '任务描述',
				} ] ]
			});
		});
	</script>
</body>
</html>