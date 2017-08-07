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
<title>扫描导出管理</title>
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
			
			<!-- 创建数据导入dialog -->
			<div id="newDialog" class="easyui-dialog" title="导出编辑" style="width:500px;height:400px;"   
        		data-options="iconCls:'lnr lnr-exit-up',resizable:false,modal:true,closed:true,
        			buttons:[{
        				text:'保存',
        				iconCls:'lnr lnr-bug',
        				plain:true,
        				handler:save,
        			}]">   
			    <form id="newForm" class="dialog-form" method="post">
			    	<input type="hidden" name="id" value="0" />
			    	<div>   
				        <label for="expName">数据导出名称:</label><br />
				        <input class="easyui-validatebox" type="text" name="expName" data-options="required:true" />   
				    </div>
				    <div>
				    	<label for="dsId">数据源:</label><br />
				    	<input id="dsId" class="easyui-combobox" name="dsId" value="">  
				    </div>  
				    <div>   
				        <label for="description">数据源描述:</label><br />
				        <textarea name="description" rows="3"></textarea>
				    </div>
			    </form>
			</div>  
			<!-- end 创建数据导入 -->
			
		</div>
	</div>

	<%@include file="../footer.jsp"%>
	<%@include file="../js.jsp"%>
	<script type="text/javascript">
		$(function() {
			$('#listTab').datagrid({
				url : 'scan/exporter/listpage',
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
					title : '编码',
					hidden:true,
				}, {
					field : 'expName',
					title : '数据导入名称',
				}, {
					field : 'description',
					title : '描述',
				}, {
					field : 'a',
					title : '数据源名称',
					formatter: function(value,row,index){
						return row.dataSource.DS_NAME;
					}
				}, {
					field : 'b',
					title : '数据源类型',
					formatter: function(value,row,index){
						return row.dataSource.DS_TYPE;
					}
				}, {
					field : 'c',
					title : '数据源描述',
					formatter: function(value,row,index){
						return row.dataSource.DESCRIPTION;
					}
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
			});//表格格式化
			
			//下拉菜单初始化
			$('#dsId').combobox({
				url:'scan/ds/list',
				valueField:'ID',    
			    textField:'DS_NAME',
			    onSelect:function(rec){
			    	var type=rec["DS_TYPE"];
			    	$('#propForm').remove();
					if(type=='db'){
						$('#newDialog').append(dbPropForm);
					}else if(type=='redis'){
						$('#newDialog').append(redisPropForm);
					}else if(type=='url'){
						$('#newDialog').append(urlPropForm);
					}
			    	
			    }
			});
		});
		
		var dbPropForm='<form id="propForm" class="dialog-form">'
			+'<div>'
				+'<label for="tabName">表名称:</label><br />'
				+'<input class="easyui-validatebox" type="text" name="tabName" data-options="required:true" />'
			+'</div>'
			+'</form>';
		
		var redisPropForm='<form id="propForm" class="dialog-form">'
			+'<div>'
				+'<label for="queue">队列名称:</label><br />'
				+'<input class="easyui-validatebox" type="text" name="queue" data-options="required:true" />'
			+'</div>'
			+'</form>';
			
		var urlPropForm='<form id="propForm" class="dialog-form">'
			+'<div>'
				+'<label for="loadUri">数据处理uri:</label><br />'
				+'<input class="easyui-validatebox" type="text" name="loadUri" data-options="required:true" />'
			+'</div>'
			+'</form>';
			
		function create(){
			//
			$('#propForm').remove();
			//
			$('#newDialog').dialog('open');
		}
		function save(){
			$('#newForm').form('submit',{
				url:'scan/exporter/save',
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
			$.post('scan/exporter/del',{
				id:row['id']
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