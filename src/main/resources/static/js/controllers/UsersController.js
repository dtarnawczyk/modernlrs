'use strict';
app.controller('UsersController', ['$scope', '$location', 'loadOnInit', function($scope, $location, loadOnInit) {
    $scope.users = loadOnInit
    console.log($scope.users);

    $scope.goTo = function (path) {
        $location.path(path);
    };

    $scope.deleteSelectedUser = function() {

        alert("Delete selected user");

    };

}]);