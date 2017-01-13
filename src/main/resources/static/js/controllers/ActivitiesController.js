'use strict';
app.controller('ActivitiesController', ['$scope', 'loadOnInit', function($scope, loadOnInit) {
    $scope.activities = loadOnInit;
    console.log($scope.activities);


}]);