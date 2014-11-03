/**
 * 详情页
 */
demoApp.controller('DetailCtrl', function($scope, $routeParams, Employee){

    Employee.findOne({'id': $routeParams.id}, function(response){
        $scope.employee = response || {};
    });

});

