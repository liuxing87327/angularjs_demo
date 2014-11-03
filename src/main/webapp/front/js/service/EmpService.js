demoApp.factory('Employee', function ($resource) {
    var actions = {
        initDate: {method: 'PUT', params: {_method:'initDate'}},
        add: {method: 'POST', params: {}},
        remove: {method: 'DELETE', params: {}},
        update: {method: 'PUT', params: {_method:'update'}},
        query: {method: 'GET', params: {}, isArray: false},
        findOne: {method: 'GET', params: {}}
    };

    return $resource('/data/api/employee/:id', {id: '@userCode'}, actions);
});