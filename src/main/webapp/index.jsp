<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>AngularJS-Demo</title>
<link type="text/css" rel="stylesheet" href="http://public.dui.dooioo.com/public/css/main.css" />
<link type="text/css" rel="stylesheet" href="http://public.dui.dooioo.com/public/css/header.css" />
<script type="text/javascript" src="http://public.dui.dooioo.com/public/js/lib/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="http://public.dui.dooioo.com/public/js/fns.js"></script>

<script type="text/javascript">
	var headerParameters = 
		{ 	empNo:'83290',
			empName:'刘兴',
			password:'sf51170c65c3a68a9af6ff1c3d27efe8',
			env:'test'
		};
</script>
</head>

<body>
<!--头部开始-->
<div id="new_header"></div>
<script type="text/javascript" src="http://public.dui.dooioo.com/public/js/header.js"></script>
<!--头部结束-->

<base href="/angularjs-demo/">
<div class="container" ng-app="demoApp">
	
	<div ng-view></div>
	
	<!-- 人员新增和编辑的弹层 begin -->
    <script	type="text/ng-template" id="popupEmployeeTpl">
		<div class="popLayer bubbleBox" id="employee_layer_form">
          <div class="bubbleBoxTitle clearfix">
              <h1>{{popupParams.editEmployee.userCode && '编辑' || '新增'}}人员信息</h1>
              <div class="cls"><a href="javascript:;" class="closePopBox xx" ng-click="popupParams.showPopup = false"></a></div>
          </div>
          <div class="bubbleBoxCon pd_20">
            <form id="formEdit">
                <table width="100%" cellspacing="0" cellpadding="0" border="0" class="tableFormNew mt_5">
                    <tr>
                        <td class="tdTitle" width="150">姓名：</td>
                        <td class="request" width="20">●</td>
                        <td><input type="text" class="txtNew" value="" ng-model="popupParams.editEmployee.userName" name="userName" rule="required" maxlength="20" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdTitle">部门：</td>
                        <td class="request">●</td>
                        <td>
                            <select name="org" ng-model="popupParams.editEmployee.orgName" ng-options="orgName as orgName for orgName in $parent.constants.orgNames" rule="required">
                                <option value="">请选择</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdTitle">岗位：</td>
                        <td class="request">●</td>
                        <td>
                            <select name="position" ng-model="popupParams.editEmployee.position" ng-options="position as position for position in $parent.constants.positions" rule="required">
                                <option value="">请选择</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdTitle">入职日期：</td>
                        <td class="request">●</td>
                        <td>
                            <input type="text" class="txtDate" ng-model="$parent.popupParams.editEmployee.createdAt" date-picker readonly="readonly" rule="required"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdTitle">状态：</td>
                        <td class="request">●</td>
                        <td>
                            <label>
                                <input type="radio" value="正式" ng-model="popupParams.editEmployee.status" name="status" rule="required" groupby="status" />正式
                            </label>
                            <label class="ml_20">
                                <input type="radio" value="试用" ng-model="popupParams.editEmployee.status" name="status" rule="required" groupby="status" />试用
                            </label>
                            <label class="ml_20">
                                <input type="radio" value="离职" ng-model="popupParams.editEmployee.status" name="status" rule="required" groupby="status" />离职
                            </label>
                        </td>
                    </tr>
                </table>
            </form>
          </div>
          <div class="bubbleBoxBtn">
			  <span class="bold red in_block mt_5 mr_20">{{popupParams.message}}</span>
              <a href="javascript:;" class="btn-small btn-silver in_block" ng-click="popupParams.showPopup = false">关闭</a>
			  <a href="javascript:;" class="btn-small btn-blue in_block" id="btn_submit" ng-click="save()">提交</a>
          </div>
        </div>
    </script>
    <!-- 点评弹层 end -->
	
	<!-- footer begin -->
	<div class="footer clearFix">
		©2014  德佑地产 版本号:2.1.6
	</div>
	<!-- footer end -->
</div>

<script type="text/javascript" src="http://public.dui.dooioo.com/public/js/WdatePicker.js"></script>
<script type="text/javascript" src="http://public.dui.dooioo.com/public/js/validation.js"></script>

<script type="text/javascript" src="http://public.dui.dooioo.com/public/js/lib/angular.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular-resource.js"></script>

<script type="text/javascript" src="http://public.dui.dooioo.com/public/js/angular/directive/directive.js"></script>
<script type="text/javascript" src="http://public.dui.dooioo.com/public/js/paginate.js"></script>
<script type="text/javascript" src="/front/js/DemoApp.js"></script>
<script type="text/javascript" src="/front/js/constant/Constants.js"></script>
<script type="text/javascript" src="/front/js/controllers/ListController.js"></script>
<script type="text/javascript" src="/front/js/controllers/DetailController.js"></script>
<script type="text/javascript" src="/front/js/service/EmpService.js"></script>
<script type="text/javascript" src="/front/js/service/UrlParseService.js"></script>
<script type="text/javascript" src="/front/js/directive/EmployeeDirective_v2.js"></script>

</body>
</html>