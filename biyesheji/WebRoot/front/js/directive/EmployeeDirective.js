/**
 * 新增人员和编辑人员的组件（指令）
 */
demoApp.directive('employee', function($http, empService){
    return {
        restrict:'E',
        templateUrl: 'popupEmployeeTpl',
        scope:{
            popupParams:'=',
            constants:'='
        },
        link: function(scope){

            scope.$watch('popupParams.showPopup', function(value) {
            	if(value){
            		showPopupDiv($('#employee_layer_form'));
            	}else{
            		hidePopupDiv($('#employee_layer_form'));
            		scope.popupParams.oper = '';
            		scope.popupParams.finished = false;
            		scope.popupParams.editEmployee = {};
            	}
            });
            
            scope.$watch('popupParams.oper', function(value) {
            	if(!value){
            		return false;
            	}
            	
            	if(value == 'add'){
            		scope.popupParams.operValue = "新增";
            		scope.popupParams.showPopup = true;
            	}else if(value == 'update'){
            		scope.popupParams.operValue = "编辑";
            		scope.popupParams.showPopup = true;
            	}
            });
            
            //保存新增
            scope.add = function() {
            	var validator = $('#formEdit').validate();
        		if(!validator.validateForm()){
        			return false;
        		};
        		
        		var params = angular.copy(scope.popupParams.editEmployee);
        		
        		var okCallback = function(){
        			scope.popupParams.showPopup = false;
        			scope.popupParams.finished = true;
                };
                
                var failCallback = function(data){
                	scope.popupParams.message = "保存失败!" + data.message;
                };
                
                var errorCallback = function(){
                	scope.popupParams.message = "保存失败，请重试！";
                };
                
                empService.add(params, okCallback, failCallback, errorCallback);
            }
            
            //保存编辑
            scope.update = function() {
            	var validator = $('#formEdit').validate();
        		if(!validator.validateForm()){
        			return false;
        		};

        		var params = angular.copy(scope.popupParams.editEmployee);
        		
        		var okCallback = function(){
        			scope.popupParams.showPopup = false;
        			scope.popupParams.finished = true;
                };
                
                var failCallback = function(data){
                	scope.popupParams.message = "保存失败!" + data.message;
                };
                
                var errorCallback = function(){
                	scope.popupParams.message = "保存失败，请重试！";
                };
                
                empService.update(params, okCallback, failCallback, errorCallback);
            }
         }
    };
});