'use strict';
app.controller('Navigation', ['$scope', '$location', 'DashboardService',
    function($scope, $location) {

        $scope.title = 'navigation';

        $scope.state = false;

        $scope.toggleState = function() {
            $scope.state = !$scope.state;
        };

        $scope.goTo = function (path) {
            $location.path(path);
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