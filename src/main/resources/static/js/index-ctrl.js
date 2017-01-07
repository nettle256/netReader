/**
 * Created by Nettle on 2017/1/7.
 */

netReader
    .controller('indexCtrl', ['$scope', '$http', '$timeout', '$state', function ($scope, $http, $timeout, $state) {
        var drawer = new mdui.Drawer("#left-drawer");

        $scope.drawerClosed = false;

        $http
            .get(['api','user'].join('/'))
            .then(function (result) {
                $scope.user = result.data;
            }, function (result) {

            });

        $scope.logout = function () {
            mdui.dialog({
                content: '确认登出',
                buttons: [
                    {
                        text: '取消'
                    },
                    {
                        text: '确认',
                        onClick: function(inst){
                            $http
                                .get(['api', 'logout'].join('/'))
                                .then(function (result) {
                                    window.location.reload();
                                }, function (result) {
                                });
                        }
                    }
                ]
            })
        };

        $scope.openDrawer = function () {
            drawer.open();
            $scope.drawerClosed = false;
        };

        $scope.closeDrawer = function () {
            drawer.close();
            $timeout(function () {
                $scope.drawerClosed = true;
            }, 100);
        };

        $state.go('novel');
    }]);