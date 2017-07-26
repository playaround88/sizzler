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
    <title>模板</title>
    <%@include file="css.jsp" %>
  </head>
  <body>
  	<%@include file="header.jsp" %>
  	
  	<div id="content">
  		<div class="container">
  			
  		</div>
  	</div>
  	
  	<%@include file="footer.jsp" %>
  	<%@include file="js.jsp" %>
    <script type="text/javascript">

	</script>
  </body>
</html>