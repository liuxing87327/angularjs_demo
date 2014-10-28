demoApp.controller('ListCtrl', function($scope, $http, $rootScope, $location, $filter, constants, urlParseService, empService){

    document.getElementsByTagName("title").item(0).innerText = ("人员管理 - 德佑地产");

    $scope.constants = constants;
    $scope.empService = empService;
    $scope.editEmployee = {};
    
    $scope.params = {};
    $scope.params.status = "";
    
    //点击搜索触发
    $scope.search = function(){
        $scope.params.keyword = $scope.keyword;
    };

    //初始化参数
    $scope.params = $.extend({}, angular.copy($scope.params), $location.search());

    //监听参数的变更
    $scope.$watch('params', function (newValue,oldValue) {
        if (!oldValue) {
            return false;
        }

        if(newValue !== oldValue){
            var params = urlParseService.buildSearch(angular.copy($scope.params));
            $location.search(params);
        }else{
            query();
        }
    }, true);

    //删除操作
    $scope.remove = function(id){
    	if(window.confirm("确定要删除此人员吗？")){
    		remove(id);
    	}
    };
    
    //弹层编辑
    $scope.toUpdate = function(id){
    	for(var index in $scope.list.pageList){
    		var employee = $scope.list.pageList[index];
    		if(employee.userCode == id){
    			$scope.editEmployee = angular.copy(employee);
    			$scope.editEmployee.operValue = "编辑";
    			$scope.editEmployee.createdAt = $filter('date')($scope.editEmployee.createdAt, 'yyyy-MM-dd');
    		}
    	}
    	
    	showPopupDiv($('#layer_edit'));
    }
    
    //弹层新增
    $scope.toAdd = function(){
    	$scope.editEmployee = {};    	
    	$scope.editEmployee.operValue = "新增";
    	showPopupDiv($('#layer_edit'));
    }
    
    //保存数据
    $scope.save = function(){
    	var validator = $('#formEdit').validate();
		if(validator.validateForm()){
			addOrUpdate();
		};
    }

    /**
     * 判断编辑还是新增
     */
    function addOrUpdate(){
    	if($scope.editEmployee.userCode > 0){
    		update();
    	}else{
    		add();
    	}
    }
    
    /**
     * 查询后台的jsonList数据
     */
    function query(){
    	$scope.loadStatus = true;
    	
        var params = angular.copy($scope.params);
        
        var callback = function($scope, response){
        	$scope.list = response.paginate || {};

    		if(Number($scope.params.pageNo) > $scope.list.totalPage){
    			$scope.params.pageNo = $scope.list.totalPage;
            }

    		$scope.loadStatus = false;
    		
        };
        
        $scope.empService.query($scope, params, callback);
    }
    
    /**
     * 删除数据
     */
    function remove(id){
    	var params = {};
    	params.userCode = id;
    	
    	var callback = function(){
    		query();
        };
        
        $scope.empService.remove($scope, params, callback);
    }
    
    /**
     * 编辑数据
     */
    function update(){
    	var params = angular.copy($scope.editEmployee);
    	
    	var callback = function(){
    		hidePopupDiv($("#layer_edit"));
    		query();
        };
        
        $scope.empService.update($scope, params, callback);
    	
    	$scope.editEmployee = {};
    }
    
    /**
     * 新增数据
     */
    function add(){
    	var params = angular.copy($scope.editEmployee);
    	
    	var callback = function(){
    		hidePopupDiv($("#layer_edit"));
    		query();
        };
        
        $scope.empService.add($scope, params, callback);
    	
    	$scope.editEmployee = {};
    }
    
});

