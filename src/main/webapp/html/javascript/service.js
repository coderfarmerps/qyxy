/**
 * Created by stefan on 16-4-14.
 */
define(['modules'], function (app) {
    app.factory('accountService', function ($http, project) {
    	return {
            selectById: function (id) {
                return $http({
                    url: project.url + '/admin/account/selectById',
                    params: {id: id}
                });
            },
    		save : function(params){
    			return $http({
    				url: project.url + '/admin/account/saveAccount',
    				method: 'post',
    				data: params
    			});
    		},
            update : function(params){
                return $http({
                    url: project.url + '/admin/account/updateAccount',
                    method: 'post',
                    data: params
                });
            },
            getAccountList : function(params){
                return $http({
                    url: project.url + '/admin/account/getAccountList',
                    params: params
                });
            }
    	}
    }).factory('accountTypeService', function ($http,project) {
    	return {
            save : function(params){
                return $http({
                    url: project.url + '/admin/accountType/saveAccountType',
                    method: 'post',
                    data: params
                });
            },
            update : function(params){
                return $http({
                    url: project.url + '/admin/accountType/updateAccountType',
                    method: 'post',
                    data: params
                });
            },
            getAccountTypeList : function(){
                return $http({
                    url: project.url + '/admin/accountType/getAccountTypeList'
                });
            }
    	}
    }).factory('loginService', function ($http, project) {
        return {
            question : function(){
                return $http({
                    url: project.url + '/login/getQuestion',
                });
            },
            answer : function (data) {
                return $http({
                    url: project.url + '/login/answer',
                    method: 'post',
                    data: data
                });
            }
        }
    }).factory('remindDateService', function ($http, project) {
        return {
            selectById: function (id) {
                return $http({
                    url: project.url + '/admin/remindDate/getRemindDateById',
                    params: {id: id}
                });
            },
            save : function(params){
                return $http({
                    url: project.url + '/admin/remindDate/saveRemindDate',
                    method: 'post',
                    data: params
                });
            },
            update : function(params){
                return $http({
                    url: project.url + '/admin/remindDate/updateRemindDate',
                    method: 'post',
                    data: params
                });
            },
            getRemindDateList : function(params){
                return $http({
                    url: project.url + '/admin/remindDate/getRemindDateList',
                    params: params
                });
            },
            finishRemindDate : function(id){
                return $http({
                    url: project.url + '/admin/remindDate/finishRemindDate',
                    method: 'post',
                    data: {id:id}
                });
            },
            delRemindDate : function(id){
                return $http({
                    url: project.url + '/admin/remindDate/delRemindDate',
                    method: 'post',
                    data: {id:id}
                });
            }
        }
    })
})