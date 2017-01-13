'use strict';
app.controller('AgentsController', ['$scope', 'loadOnInit', function($scope, loadOnInit) {
    $scope.agents = loadOnInit;
    console.log($scope.agents);
}]);