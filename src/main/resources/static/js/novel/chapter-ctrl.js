/**
 * Created by Nettle on 2017/1/7.
 */

netReader
    .config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('chapter', {
                url: '/novel/{novelId:[0-9]+}/chapter',
                templateUrl: '/html/novel/chapter',
                controller: 'chapterCtrl'
            })
    }])
    .controller('chapterCtrl', ['$scope', '$http', '$state', '$stateParams', function ($scope, $http, $state, $stateParams) {
        $scope.order = true;
        $http
            .get(['api','novel', $stateParams.novelId].join('/'))
            .then(function (result) {
                $scope.novel = result.data;
                $http
                    .get(['api','novel', $stateParams.novelId, 'chapter'].join('/'))
                    .then(function (result) {
                        $scope.chapters = result.data;
                    }, function (result) {
                        mdui.alert('失败：未知错误');
                    });
            }, function (result) {
                mdui.alert('失败：未知错误');
            });
    }]);