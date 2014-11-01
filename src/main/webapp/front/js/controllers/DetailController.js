demoApp.controller('DetailCtrl', function($scope, $http, $rootScope, $filter, $resource, $routeParams, empService){

    $scope.empService = empService;

    $scope.params = {};
    $scope.params.userCode = $routeParams.id;
    
    //监听参数的变更
    $scope.$watch('params', function (value) {
        query();
    }, true);

    /**
     * 查询后台的json数据
     */
    function query(){
        var params = angular.copy($scope.params);

        $resource('/data/api/findOne').get(params, function(response){
            $scope.employee = response || {};
        });
    }

});

