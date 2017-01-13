'use strict';
app.controller('ActivityLogController', [ '$scope', 'loadOnInit', function($scope, loadOnInit) {
    $scope.activityEvents = loadOnInit;

    console.log($scope.activityEvents);
}]);