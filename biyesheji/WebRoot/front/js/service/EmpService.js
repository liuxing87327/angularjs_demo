/**
 * 用户新增和编辑相关的操作
 */
demoApp.factory('empService', function($http){
	var empService = {};
	
	//添加数据
	empService.add = function($scope, params, callback){
		$http.post('/db/add', params).success(function(response){
    		if(response.status == "ok"){
    			callback();
    		}
    	});
	}
	
	//更新数据
	empService.update = function($scope, params, callback){
		$http.post('/db/update', params).success(function(response){
    		if(response.status == "ok"){
    			callback();
    		}
    	});
	}
	
	//删除数据
	empService.remove = function($scope, params, callback){
		$http.post('/db/delete', params).success(function(response){
    		if(response.status == "ok"){
    			callback();
    		}
    	});
	}
	
	//查询列表
	empService.query = function($scope, params, callback){
		$http.get('/db/query', {params:params}).success(function(response){
    		callback($scope, response);
    	});
	}
	
	return empService;
});