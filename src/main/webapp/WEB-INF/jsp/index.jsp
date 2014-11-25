<%--
 功能说明：index.jsp
 作者：liuxing(2014-11-14 01:44)
 修改者：liuxing(2014-11-14 01:44)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>AngularJS-Demo</title>
    <link type="text/css" rel="stylesheet" href="http://public.dui.dooioo.com/public/css/main.css?v=${version}" />
    <link type="text/css" rel="stylesheet" href="http://public.dui.dooioo.com/public/css/header.css?v=${version}" />
    <script type="text/javascript" src="http://public.dui.dooioo.com/public/js/lib/jquery-1.6.2.min.js?v=${version}"></script>
    <script type="text/javascript" src="http://public.dui.dooioo.com/public/js/fns.js?v=${version}"></script>

    <script type="text/javascript">
        var headerParameters =
        { 	empNo:'12345',
            empName:'test',
            password:'sf51170c65c3a68a9af6ff1c3d27efe8',
            env:'test'
        };
    </script>
</head>

<body>
<!--头部开始-->
<div id="new_header"></div>
<script type="text/javascript" src="http://public.dui.dooioo.com/public/js/header.js?v=${version}"></script>
<!--头部结束-->

<base href="/employee/">
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
        ©2014 德佑地产
    </div>
    <!-- footer end -->
</div>



<script type="text/javascript" src="http://public.dui.dooioo.com/public/js/WdatePicker.js?v=${version}"></script>
<script type="text/javascript" src="http://public.dui.dooioo.com/public/js/validation.js?v=${version}"></script>

<script type="text/javascript" src="http://public.dui.dooioo.com/public/js/lib/angular.min.js?v=${version}"></script>
<script type="text/javascript" src="/static/front/js/lib/angular-resource.js?v=${version}"></script>

<script type="text/javascript" src="http://public.dui.dooioo.com/public/js/angular/directive/directive.js?v=${version}"></script>
<script type="text/javascript" src="http://public.dui.dooioo.com/public/js/paginate.js?v=${version}"></script>
<script type="text/javascript" src="/static/front/js/DemoApp.js?v=${version}"></script>
<script type="text/javascript" src="/static/front/js/constant/Constants.js?v=${version}"></script>
<script type="text/javascript" src="/static/front/js/controllers/ListController.js?v=${version}"></script>
<script type="text/javascript" src="/static/front/js/controllers/DetailController.js?v=${version}"></script>
<script type="text/javascript" src="/static/front/js/service/EmpService.js?v=${version}"></script>
<script type="text/javascript" src="/static/front/js/service/UrlParseService.js?v=${version}"></script>
<script type="text/javascript" src="/static/front/js/directive/EmployeeDirective.js?v=${version}"></script>

</body>
</html>