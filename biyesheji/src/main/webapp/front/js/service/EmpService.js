/**
 * 用户新增和编辑相关的操作
 */
demoApp.factory('empService', function($http){
	var empService = {};
	
	//初始数据
	empService.initDate = function(params, callback){
		$http.post('/db/initDate', params).success(function(response){
    		if(response.status == "ok"){
    			callback();
    		}
    	});
	}
	
	//添加数据
	empService.add = function(params, okCallback, failCallback, errorCallback){
		$http.post('/db/add', params).success(function(response){
    		if(response.status == "ok"){
    			okCallback(response);
    		}else{
    			failCallback(response);
    		}
    	}).error(function(){
    		errorCallback();
    	});;
	}
	
	//更新数据
	empService.update = function(params, okCallback, failCallback, errorCallback){
		$http.post('/db/update', params).success(function(response){
    		if(response.status == "ok"){
    			okCallback(response);
    		}else{
    			failCallback(response);
    		}
    	}).error(function(){
    		errorCallback();
    	});;
	}
	
	//删除数据
	empService.remove = function(params, callback){
		$http.post('/db/delete', params).success(function(response){
    		if(response.status == "ok"){
    			callback(response);
    		}
    	});
	}
	
	//查询列表
	empService.query = function(params, callback){
		$http.get('/db/query', {params:params}).success(function(response){
    		callback(response);
    	});
	}
	
	return empService;
});