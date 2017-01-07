netReader
    .config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('article', {
                url: '/novel/{novelId}/chapter/{subId}',
                templateUrl: '/html/novel/article',
                controller: 'articleCtrl'
            })
    }])
    .controller('articleCtrl', ['$scope', '$http', '$state', '$stateParams', function ($scope, $http, $state, $stateParams) {
        $http
            .get(['api','novel', $stateParams.novelId, 'chapter', $stateParams.subId].join('/'))
            .then(function (result) {
                $scope.chapter = result.data;
                if ($scope.chapter.cnSubTitle == null)
                    $scope.chapter.cnSubTitle = '';
                $scope.subTitle = $scope.chapter.cnSubTitle;
            }, function (result) {
                mdui.alert('失败：未知错误');
            });

        $scope.updateSubTitle = function () {
            $http
                .put(['api','novel', $stateParams.novelId, 'chapter', $stateParams.subId].join('/'), {
                    cnSubTitle: $scope.subTitle
                })
                .then(function (result) {
                    $scope.chapter.cnSubTitle = $scope.subTitle;
                }, function (result) {
                    mdui.alert(result.data);
                });
        }

    }]);