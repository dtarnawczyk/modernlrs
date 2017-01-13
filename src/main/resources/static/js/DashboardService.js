'use strict';
app.factory('DashboardService', ['$http', '$q', function($http, $q){
    return {
        initActivityLog: function() {
            return $http
                    .get('/activityLogInit')
                    .then(
                        function success(response){
                            console.log(response);
                            return response.data;
                        },
                        function error(errResponse){
                            console.error('Error while fetching data');
                            return $q.reject(errResponse);
                        }
                    );
        },
        initStatementsView: function() {
            return $http
                    .get('/statementsInit')
                    .then(
                        function success(response){
                            console.log(response);
                            return response.data;
                        },
                        function error(errResponse){
                            console.error('Error while fetching data');
                            return $q.reject(errResponse);
                        }
                    );
        },
        initAgentsView: function() {
            return $http
                    .get('/agentsInit')
                    .then(
                        function success(response){
                            console.log(response);
                            return response.data;
                        },
                        function error(errResponse){
                            console.error('Error while fetching data');
                            return $q.reject(errResponse);
                        }
                    );
        },
        initActivitiesView: function() {
            return $http
                    .get('/activitiesInit')
                    .then(
                        function success(response){
                            console.log(response);
                            return response.data;
                        },
                        function error(errResponse){
                            console.error('Error while fetching data');
                            return $q.reject(errResponse);
                        }
                    );
        }
    };
}]);