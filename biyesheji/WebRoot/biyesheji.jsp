<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>列表页示例 - DUI标准规范 - 德佑地产</title>
<link type="text/css" rel="stylesheet" href="http://dui.dooioo.com/public/css/main.css" />
<link type="text/css" rel="stylesheet" href="http://dui.dooioo.com/public/css/header.css" />
<script type="text/javascript" src="http://dui.dooioo.com/public/js/lib/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="http://dui.dooioo.com/public/js/fns.js"></script>

<script type="text/javascript">
	var headerParameters = 
		{ 	empNo:'83290',
			empName:'刘兴',
			password:'sf51170c65c3a68a9af6ff1c3d27efe8',
			env:'production'
		};
</script>
</head>

<body>
<!--头部开始-->
<div id="new_header"></div>
<script type="text/javascript" src="http://dui.dooioo.com/public/js/header.js"></script>
<!--头部结束-->

<base href="/biyesheji/">
<div class="container" ng-app="demoApp">
	
	<div ng-view></div>

	<!-- footer begin -->
	<div class="footer clearFix">
		©2014  德佑地产 版本号:2.1.6
	</div>
	<!-- footer end -->
</div>

<script type="text/javascript" src="http://dui.dooioo.com/public/js/WdatePicker.js"></script>
<script type="text/javascript" src="http://dui.dooioo.com/public/js/validation.js"></script>

<script type="text/javascript" src="http://dui.dooioo.com/public/js/lib/angular.min.js"></script>
<script type="text/javascript" src="http://dui.dooioo.com/public/js/angular/directive/directive.js"></script>
<script type="text/javascript" src="http://dui.dooioo.com/public/js/paginate.js"></script>
<script type="text/javascript" src="/front/js/DemoApp.js"></script>
<script type="text/javascript" src="/front/js/constant/Constants.js"></script>
<script type="text/javascript" src="/front/js/controllers/ListController.js"></script>
<script type="text/javascript" src="/front/js/controllers/DetailController.js"></script>
<script type="text/javascript" src="/front/js/service/EmpService.js"></script>
<script type="text/javascript" src="/front/js/service/UrlParseService.js"></script>
</body>
</html>