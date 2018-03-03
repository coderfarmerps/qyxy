/**
 * Created by stefan on 16-4-15.
 */
define(['domReady','angular','modules','config'], function (domReady, angular) {
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