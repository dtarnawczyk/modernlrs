app.controller('Navigation', ['$scope', function($scope) {

    $scope.title = 'navigation';

    $scope.state = false;

    $scope.toggleState = function() {
        $scope.state = !$scope.state;
    };

}]);

app.directive('sidebarDirective', function() {
    return {
        link : function(scope, element, attr) {
            scope.$watch(attr.sidebarDirective, function(val) {
                  if(val)
                  {
                    element.addClass('show');
                    return;
                  }
                  element.removeClass('show');
            });
        }
    };
});