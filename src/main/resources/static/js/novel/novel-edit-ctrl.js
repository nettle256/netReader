/**
 * Created by Nettle on 2017/1/7.
 */

netReader
    .config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('novel_new', {
                url: '/novel/new',
                templateUrl: '/html/novel/edit',
                controller: 'novelEditCtrl'
            }).state('novel_edit', {
                url: '/novel/{novelId:[0-9]+}/edit',
                templateUrl: '/html/novel/edit',
                controller: 'novelEditCtrl'
            });
    }])
    .controller('novelEditCtrl', ['$scope', '$http', '$state', '$stateParams', function ($scope, $http, $state, $stateParams) {

        $scope.novel = {
            id: null,
            name: null,
            cnName: null,
            author: null,
            source: null,
            url: null
        };
        $scope.saving = false;

        if ($stateParams.novelId) {
            $scope.novel.id = $stateParams.novelId;
            $http
                .get(['api','novel', $scope.novel.id].join('/'))
                .then(function (result) {
                    $scope.novel = result.data;
                }, function (result) {
                    mdui.alert('失败：未知错误');
                });
            $scope.isNew = false;
        }   else {
            $scope.isNew = true;
        }

        $scope.save = function () {
            $scope.saving = true;
            if (!$scope.novel.url.endsWith('/'))
                $scope.novel.url = $scope.novel.url + '/';
            if ($scope.isNew) {
                $http
                    .post(['api','novel'].join('/'), $scope.novel)
                    .then(function (result) {
                        $state.go('novel_edit', {novelId: result.data.id});
                    }, function (result) {
                        if (result.status == 403)
                            mdui.alert('失败：没有权限');
                        else if (result.status == 406)
                            mdui.alert('失败：系统错误');
                        else mdui.alert('失败：未知错误');
                        $scope.saving = false;
                    });
            }   else {
                $http
                    .put(['api','novel', $scope.novel.id].join('/'), $scope.novel)
                    .then(function (result) {
                        $state.go('.', {}, { reload: true });
                    }, function (result) {
                        if (result.status == 403)
                            mdui.alert('失败：没有权限');
                        else if (result.status == 406)
                            mdui.alert('失败：系统错误');
                        else mdui.alert('失败：未知错误');
                        $scope.saving = false;
                    });
            }
        };

        $scope.cancel = function () {
            if ($scope.novelForm.$dirty) {
                mdui.dialog({
                    content: $scope.new ? '确认放弃新建小说' : '确认放弃修改',
                    buttons: [
                        {
                            text: '取消'
                        },
                        {
                            text: '确认',
                            onClick: function(){
                                $state.go('novel');
                            }
                        }
                    ]
                })
            }   else
            $state.go('novel');
       }

       $scope.deleting = false;
       $scope.delete = function () {
           if (!$scope.novel.deleted) {
               // delete
               mdui.dialog({
                   content: '确认删除该小说',
                   buttons: [
                       {
                           text: '取消'
                       },
                       {
                           text: '确认',
                           onClick: function(){
                               $scope.deleting = true;
                               $http
                                   .delete(['api','novel', $scope.novel.id].join('/'))
                                   .then(function (result) {
                                       $scope.novel.deleted = true;
                                       $scope.deleting = false;
                                   }, function (result) {
                                       if (result.status == 403)
                                           mdui.alert('失败：没有权限');
                                       else if (result.status == 406)
                                           mdui.alert('失败：系统错误');
                                       else mdui.alert('失败：未知错误');
                                       $scope.deleting = false;
                                   });
                           }
                       }
                   ]
               })
           }    else {
               // recover
               mdui.dialog({
                   content: '确认恢复该小说',
                   buttons: [
                       {
                           text: '取消'
                       },
                       {
                           text: '确认',
                           onClick: function(){
                               $scope.deleting = true;
                               $http
                                   .put(['api','novel', $scope.novel.id, 'recover'].join('/'), {})
                                   .then(function (result) {
                                       $scope.novel.deleted = false;
                                       $scope.deleting = false;
                                   }, function (result) {
                                       if (result.status == 403)
                                           mdui.alert('失败：没有权限');
                                       else if (result.status == 406)
                                           mdui.alert('失败：系统错误');
                                       else mdui.alert('失败：未知错误');
                                       $scope.deleting = false;
                                   });
                           }
                       }
                   ]
               })
           }
       }

       $scope.novel.scanSubmit = false;
       $scope.scan = function () {
           $scope.novel.scanSubmit = true;
           $http
               .put(['api','novel', $scope.novel.id, 'scan'].join('/'), {})
               .then(function (result) {
                   $scope.novel.scanning = true;
                   $scope.novel.scanSubmit = false;
               }, function (result) {
                   if (result.status == 403)
                       mdui.alert('失败：没有权限');
                   else if (result.status == 406)
                       mdui.alert('失败：系统错误');
                   else mdui.alert('失败：未知错误');
                   $scope.novel.scanSubmit = false;
               });
       }
    }]);
