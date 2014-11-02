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
        		
                empService.add(params, function(){
        			scope.popupParams.showPopup = false;
        			
        			//使用状态位标记处理完毕，父类监听
        			scope.popupParams.finished = true;
        			
        			//发送处理完毕的通知给父类
        			scope.$emit('employee.finished', true);
        			
                }, function(data){
                	scope.popupParams.message = "保存失败!" + data.message;
                }, function(){
                	scope.popupParams.message = "保存失败，请重试！";
                });
            }
            
            //保存编辑
            scope.update = function() {
            	var validator = $('#formEdit').validate();
        		if(!validator.validateForm()){
        			return false;
        		};

        		var params = angular.copy(scope.popupParams.editEmployee);
        		
                empService.update(params, function(){
        			scope.popupParams.showPopup = false;
        			
        			//使用状态位标记处理完毕，父类监听
        			scope.popupParams.finished = true;
        			
        			//发送处理完毕的通知给父类
        			scope.$emit('employee.finished', true);
        			
                }, function(data){
                	scope.popupParams.message = "保存失败!" + data.message;
                }, function(){
                	scope.popupParams.message = "保存失败，请重试！";
                });
            }
         }
    };
});