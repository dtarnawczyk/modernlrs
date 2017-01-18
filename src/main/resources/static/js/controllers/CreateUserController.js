'use strict';
app.controller('CreateUserController', ['$scope', '$location', '$http', '$q',
        function($scope, $location, $http, $q) {

    $scope.goTo = function (path) {
        $location.path(path);
    };

    $scope.createNewUser = function() {
        var formData = {
            "name": $scope.newUsername || "",
        	"email": $scope.newEmail || "",
        	"password": $scope.newPassword || "",
        	"role": $scope.newRole,
        	"active": $scope.newActive || false
        };
        $http({
                method : "POST",
                url : "/createNewUser",
                data : angular.toJson(formData),
                headers : {
                    'Content-Type' : 'application/json'
                }
            })
            .then(
                function success(response){
                    console.log(response);
                    $location.path('/usersView');
                },
                function error(errResponse){
                    console.error('Error while fetching data');
                    $q.reject(errResponse);
                }
            );
    }

}]);