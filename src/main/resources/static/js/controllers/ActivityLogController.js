'use strict';
app.controller('ActivityLogController', [ '$scope', 'loadOnInit', '$http', '$q',
function($scope, loadOnInit, $http, $q) {
    $scope.activityEvents = loadOnInit;

    $scope.pageSize = 10;
    $scope.currentPage = 0;

    $scope.updatePageSize = function() {
        var _pageSize = $scope.pageSize;
        console.log("Updating pageSize: "+ _pageSize);
        $http({
            method : "POST",
            url : "/getEventsOnPageSize",
            data : _pageSize,
            headers : {
                'Content-Type' : 'application/json'
            }
            })
        .then(
            function success(response){
//                console.log(response);
                $scope.activityEvents = response.data;
            },
            function error(errResponse){
                console.error('Error while fetching data');
                $q.reject(errResponse);
            }
        );
    };

    $scope.nextPage = function() {
        $scope.currentPage++;
        var _currentPage = $scope.currentPage;
        var _pageSize = $scope.pageSize;
        var pageRange = {
            "pageSize": _pageSize,
            "currentPage": _currentPage
        };
        console.log(pageRange);
        $http({
            method : "POST",
            url : "/getEventsOnNextPage",
            data : angular.toJson(pageRange),
            headers : {
                'Content-Type' : 'application/json'
            }
            })
        .then(
            function success(response){
//                console.log(response);
                $scope.activityEvents = response.data;
            },
            function error(errResponse){
                console.error('Error while fetching data');
                $q.reject(errResponse);
            }
        );

    };

    $scope.prevPage = function() {
        var _currentPage = $scope.currentPage;
        var _pageSize = $scope.pageSize;
        _currentPage--;
        console.log("currentPage: "+ _currentPage);
        if(_currentPage >= 0){
            $scope.currentPage = _currentPage;
            var pageRange = {
                "pageSize": _pageSize,
                "currentPage": _currentPage
            };
            console.log(pageRange);
            $http({
                method : "POST",
                url : "/getEventsOnPrevPage",
                data : angular.toJson(pageRange),
                headers : {
                    'Content-Type' : 'application/json'
                }
                })
            .then(
                function success(response){
    //                console.log(response);
                    $scope.activityEvents = response.data;
                },
                function error(errResponse){
                    console.error('Error while fetching data');
                    $q.reject(errResponse);
                }
            );
        }
    };


}]);