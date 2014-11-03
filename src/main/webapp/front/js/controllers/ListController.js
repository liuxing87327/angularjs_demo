demoApp.controller('ListCtrl', function($scope, $rootScope, $location, $filter, urlParseService, Employee){

    $scope.employee = {};
    
    $scope.searchParams = $.extend({}, angular.copy($scope.searchParams), $location.search());
    $scope.keyword = $scope.searchParams.keyword;

    $scope.search = function(){
        $scope.searchParams.keyword = $scope.keyword;
    };

    $scope.$watch('searchParams', function (newValue, oldValue) {
        if (!oldValue) {
            return false;
        }

        if(newValue !== oldValue){
            var searchParams = urlParseService.buildSearch($scope.searchParams);
            $location.search(searchParams);
        }else{
        	query();
        }
    }, true);
    
    $scope.$on('employee.finished', function(d, data) {
    	if (!data) {
            return false;
        }

        query();
    });
    
    $scope.initDate = function(){
    	if(window.confirm("确定要删除旧数据，重新初始1000条数据？")){
            Employee.initDate({}, function(response){
                console.log("数据已经重置！");
                query();
            });
    	}
    }
    
    $scope.update = function(id){
        Employee.findOne({'id': id}, function(response){
            $scope.employee.editEmployee = response || {};
            $scope.employee.editEmployee.createdAt = $filter('date')($scope.employee.editEmployee.createdAt, 'yyyy-MM-dd');
            $scope.employee.showPopup = true;
        });
    }

    $scope.remove = function(id){
    	if(window.confirm("确定要删除此人员吗？")){
            Employee.remove({'id':id}, function(response){
                query();
            });
    	}
    };
    
    function query(){
    	$scope.loadStatus = true;
    	
        Employee.query($scope.searchParams, function(response){
            $scope.list = response.paginate || {};

            if(Number($scope.searchParams.pageNo) > $scope.list.totalPage){
                $scope.searchParams.pageNo = $scope.list.totalPage;
            }

            $scope.loadStatus = false;

        });
    }
    
});

