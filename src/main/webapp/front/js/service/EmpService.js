demoApp.factory('Employee', function ($resource) {
    //新增、查询一个、删除 都默认，其他自定义actions
    var actions = {
        initDate: {method: 'PUT', params: {}},
        query: {method: 'GET', params: {}, isArray: false}
    };

    return $resource('/api/v1/employee/:id', {id: '@userCode'}, actions);
});