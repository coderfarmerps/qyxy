/**
 * Created by stefan on 16-4-14.
 */
define(['modules'], function (app) {
    app.config(function(growlProvider) {
        growlProvider.onlyUniqueMessages(true);
        growlProvider.globalTimeToLive(4000);
        growlProvider.globalEnableHtml(false); // ngSanitize
    });
    app.run(function ($rootScope) {
       $rootScope.$on('$stateChangeStart', function () {
           $rootScope.isDisplaySave = false;
           $rootScope.save = null;
       });
        $rootScope.$on('displaySave',function(event,callback){
            $rootScope.isDisplaySave = true;
            $rootScope.save = callback;
        })
    });
   app.config(function ($stateProvider, $urlRouterProvider) {
      $urlRouterProvider.otherwise('/welcome');
      $stateProvider
         .state('welcome',{
             url:'/welcome',
            templateUrl: 'html/templates/welcome.html',
             controller: 'welcomeController'
         }).state('addAccount',{
             url:'/addAccount/:id',
             templateUrl: 'html/templates/addAccount.html',
             controller: 'addAccountController'
         }).state('accountList',{
             url:'/accountList',
             templateUrl: 'html/templates/accountList.html',
             controller: 'accountListController'
         }).state('addRemindDate',{
              url:'/addRemindDate/:id',
              templateUrl: 'html/templates/addRemindDate.html',
              controller: 'addRemindDateController'
          }).state('remindDateList',{
              url:'/remindDateList',
              templateUrl: 'html/templates/remindDateList.html',
              controller: 'remindDateListController'
          })
   }).config(function (paginationConfig) { //分页配置
        paginationConfig.directionLinks = true;
        paginationConfig.boundaryLinks = true; //是否显示首页，尾页选项
        paginationConfig.maxSize = 5; //最多显示几页的选项
        paginationConfig.firstText = '首页';
        paginationConfig.lastText = '尾页';
        paginationConfig.previousText = '上一页';
        paginationConfig.nextText = '下一页';
    });

    app.value('project',{url:''})

    app.config(function($httpProvider) {
        // POST method use x-www-form-urlencoded Content-Type
        $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';

        // Override transformRequest to serialize form data like jquery
        $httpProvider.defaults.transformRequest = [

            function(data) {
                return angular.isObject(data) && String(data) !== '[object File]' ? serialize(data) : data;
            }
        ];

        // Add interceptor
        $httpProvider.interceptors.push(function($q, $injector, growl) {
            return {
                request: function(config) {
                    // REST 风格路由重写
                    var rules = config.url.match(/:(\w+)/g);
                    if (rules !== null) {
                        angular.forEach(rules, function(rule) {
                            var name = rule.substring(1);
                            if (config.params && config.params.hasOwnProperty(name)) {
                                config.url = config.url.replace(rule, config.params[name]);
                                delete config.params[name];
                            } else if (config.data && config.data.hasOwnProperty(name)) {
                                config.url = config.url.replace(rule, config.data[name]);
                                delete config.data[name];
                            }
                        });
                    }
                    return $q.when(config);
                },
                response: function(response) {
                    if (angular.isObject(response.data)) {
                        var res = response.data;
                        // 兼容旧数据格式 {code:0, message: '', data: {...}} --> {code:200, data: {message: '', ...}}
                        res.data = res.data || {};
                        res.data.message = res.data.message || res.message;

                        if (res.code == 302){
                            growl.addErrorMessage("会话过期，请重新验证");
                            $injector.get('$state').go('welcome');
                        }
                        // 默认自动拆包
                        if (response.config.autoparse !== false) {
                            return [0, 200].indexOf(res.code) !== -1 ? $q.when(res.data) : $q.reject(res.data);
                        }

                        return $q.when(response.data);
                    }
                    return $q.when(response);
                },
                requestError: function(rejection) {
                    return $q.reject(rejection);
                },
                responseError: function(rejection) {
                    console.log(rejection)
                    return $q.reject(rejection);
                }
            };
        });
    });

    app.config(function(datepickerConfig, datepickerPopupConfig) {
        datepickerConfig.showWeeks = false;
        datepickerPopupConfig.showButtonBar = false;
    });

    app.factory('bootbox', function($modal) {

        return {

            alert: function(message, fn) {
                $modal.open({
                    templateUrl: 'html/templates/config/bootbox.partial.html',
                    controller: function($scope) {
                        $scope.type = 'alert';
                        $scope.message = message;

                        $scope.confirm = function() {
                            $scope.$close();
                            return fn && fn();
                        };
                    }
                });
            },

            confirm: function(message, fn) {
                $modal.open({
                    templateUrl: 'html/templates/config/bootbox.partial.html',
                    controller: function($scope) {
                        $scope.type = 'confirm';
                        $scope.message = message;

                        $scope.confirm = function() {
                            $scope.$close();
                            return fn && fn();
                        };
                    }
                });
            }

        };
    });
});

var serialize = function (obj) {
    var query = '';
    var name, value, fullSubName, subName, subValue, innerObj, i;

    for (name in obj) {
        if (obj.hasOwnProperty(name)) {
            value = obj[name];

            if (value instanceof Array) {
                for (i = 0; i < value.length; ++i) {
                    subValue = value[i];
                    fullSubName = name;
                    innerObj = {};
                    innerObj[fullSubName] = subValue;
                    query += serialize(innerObj) + '&';
                }
            }
            else if (value instanceof Object) {
                for (subName in value) {
                    if (value.hasOwnProperty(subName)) {
                        subValue = value[subName];
                        fullSubName = name + '[' + subName + ']';
                        innerObj = {};
                        innerObj[fullSubName] = subValue;
                        query += serialize(innerObj) + '&';
                    }
                }
            }
            else if (value !== undefined && value !== null) {
                query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
            }
        }
    }

    return query.length ? query.substr(0, query.length - 1) : query;
};