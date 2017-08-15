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
<title>扫描数据源管理</title>
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
			
			<!-- 创建数据源dialog -->
			<div id="newDialog" class="easyui-dialog" title="新建数据源" style="width:500px;height:400px;"   
        		data-options="iconCls:'lnr lnr-database',resizable:false,modal:true,closed:true,
        			buttons:[{
        				text:'保存',
        				iconCls:'lnr lnr-bug',
        				plain:true,
        				handler:save,
        			}]">   
			    <form id="newForm" class="dialog-form" method="post">
			    	<input type="hidden" name="id" value="0" />
			    	<div>   
				        <label for="dsName">数据源名称:</label><br />
				        <input class="easyui-validatebox" type="text" name="dsName" data-options="required:true" />   
				    </div>   
				    <div>   
				        <label for="dsType">数据源类型:</label><br />
				        <select name="dsType" onchange="changeType(this);">
				        	<option value="" selected="selected">请选择</option>
				        	<option value="db">数据库</option>
				        	<option value="redis">redis</option>
				        	<option value="url">URL</option>
				        </select> 
				    </div>
				    <div>   
				        <label for="description">数据源描述:</label><br />
				        <textarea name="description" rows="3"></textarea>
				    </div>
			    </form>
			</div>  
			<!-- end 创建数据源 -->
			
		</div>
	</div>

	<%@include file="../footer.jsp"%>
	<%@include file="../js.jsp"%>
	<script type="text/javascript">
		$(function() {
			$('#listTab').datagrid({
				url : 'scan/ds/listpage',
				pagination:true,
				rownumbers:true,
				singleSelect:true,
				fitColumns:true,
				pageSize:20,
				pageList:[10,20,30,40,50],
				toolbar: '#tools',
				idField: 'id',
				columns : [ [{
					field : 'id',
					title : '数据源编码',
					hidden:true,
				}, {
					field : 'dsName',
					title : '数据源名称',
				}, {
					field : 'dsType',
					title : '类型',
				}, {
					field : 'description',
					title : '描述',
				}, {
					field : 'ctime',
					title : '创建时间',
					formatter: function(value,row,index){
						if(value==0){
							return value;
						}
						var d=new Date(value)
						return d.format("yyyy-MM-dd hh:mm:ss");
					}
				}] ]
			});
		});
		var dbPropForm='<form id="propForm" class="dialog-form">'
			+'<div>'
				+'<label for="driver">数据库类型:</label><br />'
				+'<select name="driver">'
				+'<option value="com.mysql.jdbc.Driver">mysql</option>'
				+'<option value="oracle.jdbc.driver.OracleDriver">oracle</option>'
				+'</select>'
			+'</div>'
			+'<div>'
				+'<label for="dbUrl">数据库连接:</label><br />'
				+'<input class="easyui-validatebox" type="text" name="dbUrl" data-options="required:true" />'
			+'</div>'
			+'<div>'
				+'<label for="user">用户名:</label><br />'
				+'<input class="easyui-validatebox" type="text" name="user" data-options="required:true" />'
			+'</div>'
				+'<div>'
				+'<label for="password">密码:</label><br />'
				+'<input class="easyui-validatebox" type="text" name="password" data-options="required:true" />'
			+'</div>'
			+'</form>';
			
		var redisPropForm='<form id="propForm" class="dialog-form">'
			+'<div>'
				+'<label for="host">redis主机地址:</label><br />'
				+'<input class="easyui-validatebox" type="text" name="host" data-options="required:true" />'
			+'</div>'
			+'<div>'
				+'<label for="port">端口号:</label><br />'
				+'<input class="easyui-validatebox" type="text" name="port" data-options="required:true" />'
			+'</div>'
				+'<div>'
				+'<label for="password">密码:</label><br />'
				+'<input class="easyui-validatebox" type="text" name="password" data-options="required:false" />'
			+'</div>'
			+'</form>';
		var urlPropForm='<form id="propForm" class="dialog-form">'
			+'<div>'
				+'<label for="baseUrl">资源url:</label><br />'
				+'<input class="easyui-validatebox" type="text" name="baseUrl" data-options="required:true" />'
			+'</div>'
			+'</form>';
		
		function create(){
			//
			clearForm();
			$('#propForm').remove();
			
			//
			$('#newDialog').dialog('open');
		}
		function changeType(target){
			var type=$(target).val();

			$('#propForm').remove();
			if(type=='db'){
				$('#newDialog').append(dbPropForm);
			}else if(type=='redis'){
				$('#newDialog').append(redisPropForm);
			}else if(type=='url'){
				$('#newDialog').append(urlPropForm);
			}
		}
		
		function save(){
			$('#newForm').form('submit',{
				url:'scan/ds/save',
				onSubmit:function(param){
					// json序列化propFrom
					var props=JSON.stringify($('#propForm').serializeJson());
					param["props"]=props;
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
			$.post('scan/ds/del',{
				id:row['id']
			},function(data){
				alert(data.message);
				if(data.success){
					$('#listTab').datagrid('reload');
				}
			},'json');
		}
		
		function update(){
			var row=$('#listTab').datagrid('getSelected');
			if(!row){
				alert('未选中行');
				return;
			}
			fillForm(row);
			
			$('#newDialog').dialog('open');
		}
		
		function clearForm(){
			$('#newForm input').val('');
			$('#newForm input[name="id"]').val('0');
			$('#newForm textarea').val('');
		}
		
		function fillForm(row){
			$('#newForm').fillForm(row);
			$('#newForm [name="dsType"]').trigger('change')
			var props=JSON.parse(row['props']);
			$('#propForm').fillForm(props);
		}
	</script>
</body>
</html>