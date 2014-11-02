demoApp.controller('ListCtrl', function($scope, $http, $rootScope, $location, $filter, constants, urlParseService, empService){

    $scope.constants = constants;
    $scope.empService = empService;
    
    $scope.employee = {};
    
    $scope.params = {};
    $scope.params.status = "";
    
    //点击搜索触发
    $scope.search = function(){
        $scope.params.keyword = $scope.keyword;
    };

    //初始化参数
    $scope.params = $.extend({}, angular.copy($scope.params), $location.search());
    $scope.keyword = angular.copy($scope.params.keyword);

    //监听筛选参数的变更
    $scope.$watch('params', function (newValue,oldValue) {
        if (!oldValue) {
            return false;
        }

        if(newValue !== oldValue){
            var params = urlParseService.buildSearch(angular.copy($scope.params));
            $location.search(params);
        }else{
        	query();
        }
    }, true);
    
    //监听员工操作结果参数的变更（状态位的方式）
    $scope.$watch('employee.finished', function (value) {
//        if (!value) {
//            return false;
//        }
//
//        query();
    });
    
    //监听员工操作结果参数的变更（消息通知的方式）
    $scope.$on('employee.finished', function(d, data) {
    	console.log('收到employee组件发来的消息：' + data);
    	
    	if (!data) {
            return false;
        }

        query();
    });
    
    //初始化数据
    $scope.initDate = function(){
    	if(window.confirm("确定要删除旧数据，重新初始1000条数据？")){
    		var params = {};

            $scope.empService.initDate(params, function(response){
                console.log("数据已经重置！");
                query();
            });
    	}
    }
    
    /**
     * 弹层编辑
     */
    $scope.toEmployeeUpdate = function(id){
    	for(var index in $scope.list.pageList){
    		var employee = $scope.list.pageList[index];
    		if(employee.userCode == id){
    			$scope.employee.editEmployee = angular.copy(employee);
    			$scope.employee.editEmployee.createdAt = $filter('date')($scope.employee.editEmployee.createdAt, 'yyyy-MM-dd');
    			$scope.employee.oper = "update";
    			break;
    		}
    	}
    }

    /**
     * 删除操作
     */
    $scope.remove = function(id){
    	if(window.confirm("确定要删除此人员吗？")){
            $scope.empService.remove(id, function(response){
                query();
            });
    	}
    };
    
    /**
     * 查询后台的jsonList数据
     */
    function query(){
    	$scope.loadStatus = true;
    	
        var params = angular.copy($scope.params);
        
        $scope.empService.query(params, function(response){
            $scope.list = response.paginate || {};

            if(Number($scope.params.pageNo) > $scope.list.totalPage){
                $scope.params.pageNo = $scope.list.totalPage;
            }

            $scope.loadStatus = false;

        });
    }
    
});

