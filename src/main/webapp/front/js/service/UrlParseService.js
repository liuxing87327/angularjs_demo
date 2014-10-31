//根据value获取对应的text
demoApp.factory('urlParseService', function () {
    return {
        getTextByValue: function (value, list) {
            if (!list || list.length == 0) {
                return '';
            }

            var result = '';
            list.forEach(function (item) {
                if (item.value === value) {
                    result = item.text;
                    return;
                }
            });
            return result;
        },
        //构建hash
        buildHash: function (params) {
            console.log(params);
            var paramPairs = [];
            for (var key in params) {
                if (isEmpty(params[key]) === false) {
                    paramPairs.push(key + '=' + params[key]);
                }
            }

            return paramPairs.join('&');

            function isEmpty(val) {
                if (val === undefined || val === null || val.toString() === '') {
                    return true;
                }
                return false;
            }
        },
        parseHash: function (strHash) {
            if (!strHash) {
                return {};
            }

            var strParams = strHash.slice(1),
                pairs = strParams.split('&'),
                result = {};

            pairs.forEach(function (pair) {
                var keyValue = pair.split('=');
                if (angular.isArray(keyValue) && keyValue.length == 2) {
                    if (keyValue[0] === 'khLevel' && keyValue[1]) {
                        var arr = keyValue[1].split(',');
                        result[keyValue[0]] = arr.filter(function (item) {
                            return item != '';
                        });
                    } else {
                        if (keyValue[1] === 'false' || keyValue[1] === 'true') {
                            result[keyValue[0]] = eval(keyValue[1]);
                        } else {
                            result[keyValue[0]] = keyValue[1];
                        }
                    }
                }
            });
            return result;
        },
        buildSearch:function(params){
            var obj = {};
            for(var prop in params){
                if(params[prop] !== '' && params[prop] !== undefined && params[prop] !== null){
                    obj[prop] = params[prop];
                }
            }

            return obj;
        },
        parseSearch:function(strSearch){
            var resultObj = {};
            if(strSearch && strSearch.length > 1){
                var search = strSearch.substring(1);
                var items = search.split('&');
                for(var index = 0 ; index < items.length ; index++ ){
                    if(! items[index]){
                        continue;
                    }
                    var kv = items[index].split('=');
                    resultObj[kv[0]] = typeof kv[1] === "undefined" ? "":decodeURI(kv[1]);
                }
            }
            return resultObj;
        }
    }
});