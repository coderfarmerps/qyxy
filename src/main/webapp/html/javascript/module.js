/**
 * Created by stefan on 16-4-14.
 */
define(["angular","angular-file-upload", "angular-ui-router","angular-datetimepicker","ui-bootstrap-tpls", 'angular-growl'], function (angular) {
    return angular.module("app",['ui.router', 'angularFileUpload', 'ui.bootstrap','ui.bootstrap.datetimepicker', 'angular-growl']);
});