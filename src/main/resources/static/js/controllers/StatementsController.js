'use strict';
app.controller('StatementsController', ['$scope', 'loadOnInit', function($scope, loadOnInit) {
    $scope.statements = loadOnInit;
    console.log($scope.statements);


}]);