var demoApp = angular.module('demoApp', ['dui.directive', 'myDemo.directive', 'ngResource']);

demoApp.config(function($routeProvider, $locationProvider, $httpProvider) {
    $locationProvider.html5Mode(true);
    $routeProvider.when('/detail/:id', {
				        templateUrl: '/front/view/detail.html',
				        controller: 'DetailCtrl',
				        title: '详情'
				    }).
				    when('/list', {
				        templateUrl: '/front/view/list.html',
				        controller: 'ListCtrl',
				        title: '列表'
				    }).
				    otherwise({
				        redirectTo: '/list'
				    });

    $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';


    var param = function(obj) {
		var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
	
		for (name in obj) {
			value = obj[name];
	
			if (value instanceof Array) {
				for (i = 0; i < value.length; ++i) {
					subValue = value[i];
					fullSubName = name + '[' + i + ']';
					innerObj = {};
					innerObj[fullSubName] = subValue;
					query += param(innerObj) + '&';
				}
			} else if (value instanceof Object) {
				for (subName in value) {
					subValue = value[subName];
					fullSubName = name + '[' + subName + ']';
					innerObj = {};
					innerObj[fullSubName] = subValue;
					query += param(innerObj) + '&';
				}
			} else if (value !== undefined && value !== null)
				query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
		}
	
		return query.length ? query.substr(0, query.length - 1) : query;
	};

    $httpProvider.defaults.transformRequest = [function(data) {
    	if(angular.isObject(data) && String(data) !== '[object File]'){
    		return param(data);
    	}
    	
    	return data;
    }];
    
});

demoApp.run(function($rootScope, $http, constants){
	$rootScope.mainUrl = '/angularjs-demo/';//主请求
    $rootScope.constants = constants;
	$rootScope.$on('$routeChangeStart', function(event, next, current){
        document.getElementsByTagName("title").item(0).innerText = (next.$$route.title + " - 人员管理 - 德佑地产");
	});
	
});
