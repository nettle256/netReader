/**
 * Created by Nettle on 2017/1/13.
 */

netReader
    .config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('translate', {
                url: '/novel/{novelId:[0-9]+}/chapter/{subId:[0-9]+}/translate',
                templateUrl: '/html/novel/translate',
                controller: 'translateCtrl'
            })
    }])
    .controller('translateCtrl', ['$scope', '$http', '$state', '$stateParams', function ($scope, $http, $state, $stateParams) {
        $http
            .get(['api','novel', $stateParams.novelId, 'chapter', $stateParams.subId].join('/'))
            .then(function (result) {
                $scope.chapter = result.data;
                if ($scope.chapter.cnSubTitle == null)
                    $scope.chapter.cnSubTitle = '';
                $scope.subTitle = $scope.chapter.cnSubTitle;
                $scope.original = $scope.chapter.content;
                $scope.translation = [];
                $scope.original.forEach(function () {
                    $scope.translation.push('');
                });
                console.log($scope.translation);
            }, function (result) {
                mdui.alert('失败：未知错误');
            });

        $scope.select = function(line, idx, event) {
            if (line.length < 1) return;
            $scope.selectedLine = idx;
            $scope.setToolCss($(event.currentTarget));
            $('.translate_tools textarea:first').focus();
        };

        $scope.setToolCss = function (event) {
            var max_top = $('.translate_container').height() - 320;
            $scope.toolCss = {
                width: $('.translate_container').width() - $('.original').width() + 'px',
                top: (event.offset().top - 130 < max_top ? event.offset().top - 130 : max_top) + 'px',
                left: -4 + 'px'
            };
            $scope.textCss = {
                height: event.height(),
                resize: 'none'
            };
        };
    }]);