/**
 * 提取指令块，比如该功能操作需要展示不同的模板时候，则实现不同的组件
 */
(function(){
	var myDemoDirective = angular.module('myDemo.directive', []);
	
	/**
	 * 新增人员和编辑人员的组件（指令）
	 */
	myDemoDirective.directive('employee', function($http, Employee, constants){
	    return {
	        restrict:'E',
	        templateUrl: 'popupEmployeeTpl',
	        scope:{
	            popupParams:'='
	        },
	        link: function(scope){
	
	            scope.$watch('popupParams.showPopup', function(value) {
	            	if(value){
	            		showPopupDiv($('#employee_layer_form'));
	            	}else{
	            		hidePopupDiv($('#employee_layer_form'));
	            		scope.popupParams.editEmployee = {};
                        scope.popupParams.message = '';
	            	}
	            });

	            scope.save = function() {
	            	var validator = $('#formEdit').validate();
	        		if(!validator.validateForm()){
	        			return false;
	        		};

                    var callback = function(response){
                        if(response.status == "ok"){
                            scope.popupParams.showPopup = false;
                            scope.$emit('employee.finished', true);
                        }else{
                            scope.popupParams.message = "保存失败!" + response.message;
                        }
                    }

	        		var requestParams = scope.popupParams.editEmployee;

					if (requestParams.userCode && requestParams.userCode != '') {
						Employee.save(requestParams, function (response) {
							callback(response);
						});
					} else {
						Employee.add(requestParams, function (response) {
							callback(response);
						});
					}
	            }
	            
	         }
	    };
	});

}());