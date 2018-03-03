/**
 * Created by stefan on 16-4-14.
 */
require.config({
    paths:{
        jquery: 'public/jquery/jquery.min',
        //"jquery-ui": "public/jquery/jquery-ui.min",
        angular: 'public/angular/angular.min',
        //date: "public/angular/date",
        "angular-file-upload": 'public/angular/angular-file-upload.min',
        "angular-growl": 'public/angular/angular-growl.min',
        "angular-ui-router": 'public/angular/angular-ui-router.min',
        "ui-bootstrap-tpls": 'public/angular/ui-bootstrap-tpls.min',
        "angular-datetimepicker": "public/angular/datetimepicker/js/datetimepicker",
        moment: "public/angular/datetimepicker/moment.min",
        domReady:"public/requireJs/domReady",
        bootstrap: 'public/bootstrap/bootstrap.min',
        clipboard: 'public/clipboard/dist/clipboard.min',
        //project js
        config: 'javascript/config',
        modules: 'javascript/module', //不能起名为module, 会被require忽略，不引入js文件
        controller: 'javascript/controller',
        service: 'javascript/service',
        directive: 'javascript/directive',
        filter: 'javascript/filter',
        // clipboard.js 没有将ClipboardJS export 到window全局变量，此处需要再次处理
        clipboardRequire: 'javascript/clipboardRequire'
    },
    shim:{
        "moment":{
          exports: "moment"
        },
        "angular":{
            exports:"angular", //function name， 如果有多个，则使用init，init和exports共存时，exports会被忽略
            deps: ["jquery"]
        },
        "angular-file-upload":["angular"],
        "angular-growl":["angular"],
        "angular-ui-router":["angular"],
        "ui-bootstrap-tpls":["angular"],
        "clipboardRequire":["clipboard"],
        "angular-datetimepicker":["moment","angular"],
        bootstrap:["jquery"],
        /*date:{
            exports: "date",
            deps: ["jquery","jquery-ui","angular"]
        }*/
    },
    //deps:['bootstrap'], //require初始化完成后，就会执行此js文件
    urlArgs: "bust=" + (new Date()).getTime()
});

require(['domReady','angular','bootstrap','config','service','filter','directive','controller'], function (domReady, angular) {
    domReady(function () {
        angular.bootstrap(document,['app']);
    });
    /*
     //jquery domReady
     $(document).ready(function () {
     angular.bootstrap(document,['app']);
     })
     */
});
