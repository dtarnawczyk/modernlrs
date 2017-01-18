'use strict';
app.controller('UsersController', ['$scope', '$location', 'loadOnInit', '$http', '$q', 'DashboardService', '$uibModal',
    function($scope, $location, loadOnInit, $http, $q, DashboardService, $uibModal) {
        $scope.users = loadOnInit
        console.log($scope.users);

        $scope.goTo = function (path) {
            $location.path(path);
        };

        $scope.deleteUsersDialogOpen = function () {
            var selectedItems = 0;
            $scope.users.forEach(function(user) {
                if(user.selected){
                    selectedItems++;
                }
            });
            if(selectedItems > 0 ){
                var confirmDeleteDialog = $uibModal.open({
                        animation: true,
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: 'js/modals/deleteUserConfirmDialog.html',
                        controller: 'DeleteUserConfirmDialogController',
                        size: 'sm',
                        resolve: {
                            selectedCount: function () {
                                return selectedItems;
                            }
                        }
                });

                confirmDeleteDialog.result.then(function () {
                        console.log('Deletion files');
                        $scope.deleteSelectedUser();
                    }, function () {
                        console.log('Deletion cancelled');
                    }
                );
            }
        };

        $scope.deleteSelectedUser = function() {
            $scope.users.forEach(function(user) {
                if(user.selected){
                    $http({
                        method : "POST",
                        url : "/deleteUser",
                        data : angular.toJson(user),
                            headers : {
                                'Content-Type' : 'application/json'
                            }
                        })
                    .then(
                        function success(response){
//                            console.log(response);
                            DashboardService.initUsersView().then(function(users){
                                $scope.users = users;
                            });
                        },
                        function error(errResponse){
                            console.error('Error while fetching data');
                            $q.reject(errResponse);
                        }
                    );
                }
            });
        };

}]);