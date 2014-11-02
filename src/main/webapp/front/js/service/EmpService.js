demoApp.factory('Employee', function ($resource) {
    var actions = {
        initDate: {method: 'PUT', params: {}},
        add: {method: 'POST', params: {}},
        remove: {method: 'DELETE', params: {}},
        update: {method: 'POST', params: {}},
        query: {method: 'GET', params: {}, isArray: false},
        findOne: {method: 'GET', params: {}}
    };

    return $resource('/data/api/employee/:id', {id: '@userCode'}, actions);
});

/**
 * 用户新增和编辑相关的操作
 */
demoApp.factory('empService', function($http, $resource, Employee){
	var empService = {};
	
	//初始数据
//	empService.initDate = function(params, callback){
//		$http.post('/data/api/initDate', params).success(function(response){
//    		if(response.status == "ok"){
//    			callback();
//    		}
//    	});
//	}
	
	//添加数据
//	empService.add = function(params, okCallback, failCallback, errorCallback){
//		$http.post('/data/api/add', params).success(function(response){
//    		if(response.status == "ok"){
//    			okCallback(response);
//    		}else{
//    			failCallback(response);
//    		}
//    	}).error(function(){
//    		errorCallback();
//    	});;
//	}

	//删除数据
//	empService.remove = function(params, callback){
//		$http.post('/data/api/delete', params).success(function(response){
//    		if(response.status == "ok"){
//    			callback(response);
//    		}
//    	});
//	}

    //更新数据
//    empService.update = function(params, okCallback, failCallback, errorCallback){
//        $http.post('/data/api/update', params).success(function(response){
//            if(response.status == "ok"){
//                okCallback(response);
//            }else{
//                failCallback(response);
//            }
//        }).error(function(){
//            errorCallback();
//        });;
//    }
	
	//查询列表
//	empService.query = function(params, callback){
//		$http.get('/data/api/query', {params:params}).success(function(response){
//    		callback(response);
//    	});
//	}

    //=================================使用resource的方式==========================================

    //初始数据
    empService.initDate = function (params, callback) {
        Employee.initDate(params, function (response) {
            callback(response);
        });
    }

    //删除数据
    empService.remove = function (userCode, callback) {
        Employee.remove({'id': userCode}, function (response) {
            callback(response);
        });
    }

    //添加数据
    empService.add = function (params, callback) {
        Employee.add(params, function (response) {
            callback(response);
        });
    }

    //更新数据
    empService.update = function (params, callback) {
        Employee.update(params, function (response) {
            callback(response);
        });
    }

    //查询列表
    empService.query = function (params, callback) {
        Employee.query(params, function (response) {
            callback(response);
        });
    }

    //查询详情
    empService.findOne = function (userCode, callback) {
        Employee.findOne({'id': userCode}, function (response) {
            callback(response);
        });
    }
	
	return empService;
});