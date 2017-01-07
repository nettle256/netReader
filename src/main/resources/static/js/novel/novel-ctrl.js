/**
 * Created by Nettle on 2017/1/7.
 */

netReader
    .config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('novel', {
                url: '',
                templateUrl: '/html/novel/index',
                controller: 'novelCtrl'
            });
    }])
    .controller('novelCtrl', ['$scope', '$http', function ($scope, $http) {
        $scope.showNovels = function (str) {
            $http
                .get(['api','novel'].join('/') + str)
                .then(function (result) {
                    $scope.novels = result.data;
                }, function (result) {
                    mdui.alert('失败：未知错误');
                });
        };
        $scope.showNovels('');

        $scope.gotoChapter = function(id) {
            $state.go('chapter', {novelId: id});
        }
    }]);