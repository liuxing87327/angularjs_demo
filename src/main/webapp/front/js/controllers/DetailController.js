/**
 * 详情页
 */
demoApp.controller('DetailCtrl', function($scope, $routeParams, Employee){

    Employee.get({'id': $routeParams.id}, function(response){
        $scope.employee = response || {};
    });

});

