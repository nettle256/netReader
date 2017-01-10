netReader
    .config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('article', {
                url: '/novel/{novelId:[0-9]+}/chapter/{subId:[0-9]+}',
                templateUrl: '/html/novel/article',
                controller: 'articleCtrl'
            })
    }])
    .controller('articleCtrl', ['$scope', '$http', '$state', '$stateParams', '$timeout', function ($scope, $http, $state, $stateParams, $timeout) {
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

        $http
            .get(['api', 'novel', $stateParams.novelId, 'chapter', $stateParams.subId, 'translation'].join('/'))
            .then(function (result) {
                $scope.transList = result.data.map(function (d) {
                    d.selected = false;
                    return d;
                });
            }, function (result) {
                mdui.alert(result.data);
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
        };

        $scope.translations = [];
        $scope.transList = null;

        $scope.showTranslation = function (translation) {
            translation.selected = true;
            $http
                .get(['api','translation', translation.id].join('/'))
                .then(function (result) {
                    translation.content = result.data.content;
                    translation.selected = true;
                }, function (result) {
                    mdui.alert(result.data);
                });
        };

        $scope.top = function () {
            $('html body').animate({                 //添加animate动画效果
                scrollTop: 0
            }, 500);
        }

    }]);