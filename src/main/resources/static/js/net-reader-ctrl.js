/**
 * Created by Nettle on 2017/1/6.
 */

angular.module('netReaderApp', [])
    .controller('netReaderCtrl', ['$scope', '$http', function ($scope, $http) {
        $scope.logout = function () {
            $http
                .get(['api','logout'].join('/'));
        };

    }]);