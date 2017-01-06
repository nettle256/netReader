/**
 * Created by Nettle on 2017/1/6.
 */

angular.module('netReaderSignApp', [])
    .controller('netReaderSignCtrl', ['$scope', '$http', function ($scope, $http) {
        $scope.newUser = {
            username: null,
            password: null,
            repeat: null
        };

        $scope.signIn = function () {
            $http
                .post(['api','login'].join('/'), $scope.user)
                .then(function (result) {
                    window.location.reload();
                }, function (result) {
                    $scope.message = result.data.message;
                });
        };

        $scope.signUp = function () {
            $http
                .post(['api','login'].join('/'), $scope.user)
                .then(function (result) {
                    window.location.reload();
                }, function (result) {
                    $scope.message = result.data.message;
                });
        };
        
        $scope.checkPasswordSame = function () {
            $scope.isPasswordSame = ($scope.newUser.password === $scope.newUser.repeat);
            console.log($scope.isPasswordSame);
        }
    }]);