/**
 * Created by stefan on 16-4-14.
 */
define(['modules', 'clipboardRequire'], function (app) {
    app.controller('addAccountController', function ($scope, $state, $stateParams, growl, $modal, accountService, accountTypeService) {
        $scope.entity = {};

        $scope.id = $stateParams.id;
        if ($scope.id){
            accountService.selectById($scope.id).then(function (res) {
                $scope.entity = res.data;
            })
		}

        $scope.selectAccountType = function (accountType) {
            $scope.entity.accountTypeId = accountType.id;
        };

        $scope.getAccountTypeList = function () {
            accountTypeService.getAccountTypeList().then(function (res) {
                $scope.accountTypeList = res.list;
            });
        }
        $scope.getAccountTypeList()
        
        //录入
        $scope.save = function(){
        	if(!$scope.entity.accountTypeId){
                growl.addErrorMessage("请选择账号类型");
				return;
			}
			var method = accountService.save;
            if ($scope.id){
        		method = accountService.update;
			}
            method($scope.entity).then(
            	function () {
                    growl.addSuccessMessage("保存成功");
                    $state.go('accountList')
                },
				function (rej) {
                    growl.addErrorMessage(rej.message);
                }
        	)['finally'](function(){
        		$scope.entity = {};
        	});
        };

        $scope.addAccountType = function (accountType) {
            $modal.open({
                templateUrl:"html/templates/partial/accountType.html",
                controller:['$scope', function(scope){
                    scope.entity = {};
                    if(accountType){
                        scope.entity = accountType;
					}

                    scope.save = function(){
                        scope.processing = true;
                        var method = accountTypeService.save;
                        if (scope.entity.id){
                            method = accountTypeService.update;
						}
                        method(scope.entity).then(
                            function(){
								growl.addSuccessMessage("保存成功");
                                $scope.getAccountTypeList();
                                scope.$close();
                            },
							function (rej) {
                                growl.addSuccessMessage(rej.message);
                            }
                        )['finally'](function(){
                            scope.processing = false;
                        });
                    }
                }]
            });
        }

    }).controller('accountListController', function ($scope, $modal, accountService, $timeout) {
    	$scope.page = 1;
    	$scope.size = 5;
    	$scope.getAccountList = function () {
            accountService.getAccountList({
                current: $scope.page,
                pageSize: $scope.size
            }).then(function (res) {
                $scope.accountList = res.list;
                $scope.total = res.total;
            })
        }

        $scope.getAccountList();

        $timeout(function () {
            new ClipboardJS(".clipboard", {
                text: function(trigger) {
                    return $(trigger.getAttribute('data-clipboard-target'))[0].innerText;
                }
            });
        })
    }).controller('welcomeController', function ($scope, $modal, growl, loginService) {
        loginService.question().then(function (res) {
            if (res.data){
                $modal.open({
                    templateUrl:"html/templates/partial/question.html",
                    controller:['$scope', function(scope){
                        scope.entity = {};
                        scope.entity.question = res.data;
                        scope.save = function(){
                            scope.processing = true;
                            loginService.answer(scope.entity).then(
                                function(){
                                    growl.addSuccessMessage("回答正确");
                                    scope.$close();
                                },
                                function (rej) {
                                    scope.entity.question = rej.message
                                    growl.addErrorMessage("回答错误，请重试");
                                }
                            )['finally'](function(){
                                scope.processing = false;
                            });
                        }
                    }]
                });
            }
        })
    }).controller('addRemindDateController', function ($scope, $state, $stateParams, growl, $modal, remindDateService) {
        $scope.entity = {};

        $scope.id = $stateParams.id;
        if ($scope.id){
            remindDateService.selectById($scope.id).then(function (res) {
                $scope.entity = res.data;
                $scope.entity.importantDate = new Date($scope.entity.importantDate)
            })
        }

        //录入
        $scope.save = function(){
            var method = remindDateService.save;
            if ($scope.id){
                method = remindDateService.update;
            }
            var entity = angular.copy($scope.entity);
            entity.importantDate = entity.importantDate.getTime()
            method(entity).then(
                function () {
                    growl.addSuccessMessage("保存成功");
                    $state.go('remindDateList')
                },
                function (rej) {
                    growl.addErrorMessage(rej.message);
                }
            )['finally'](function(){
                $scope.entity = {};
            });
        };
    }).controller('remindDateListController', function ($scope, $modal, growl, remindDateService, bootbox) {
        $scope.page = 1;
        $scope.size = 5;
        $scope.getRemindDateList = function () {
            remindDateService.getRemindDateList({
                current: $scope.page,
                pageSize: $scope.size,
                beginTime: $scope.beginTime ? $scope.beginTime.getTime() : null,
                endTime: $scope.endTime ? $scope.endTime.getTime() : null,
                isFinished: $scope.isFinished ? $scope.isFinished : null
            }).then(function (res) {
                $scope.remindDateList = res.list;
                $scope.total = res.total;
            })
        }

        $scope.getRemindDateList();

        $scope.finishRemindDate = function (id) {
            bootbox.confirm("完成以后，将不再发送提醒通知，继续操作？", function () {
                remindDateService.finishRemindDate(id).then(function (res) {
                    growl.addSuccessMessage("操作成功")
                    $scope.getRemindDateList();
                })
            })
        }

        $scope.delRemindDate = function (id) {
            bootbox.confirm("删除以后，将看不到数据，继续操作？", function () {
                remindDateService.delRemindDate(id).then(function (res) {
                    growl.addSuccessMessage("操作成功")
                    $scope.getRemindDateList();
                })
            })
        }
    });
})