'use strict';
var app = angular
    .module("dashboard", ['ngRoute'])
    .config(function($routeProvider, $locationProvider) {
        $routeProvider.
        when('/activityLogView', {
            templateUrl: 'js/parts/activityLogView.htm',
            controller: 'ActivityLogController',
            resolve: {
                loadOnInit: ['DashboardService', function (DashboardService) {
                    return DashboardService.initActivityLog()
                }]
            }
        }).
        when('/statementsView', {
            templateUrl: 'js/parts/statementsView.htm',
            controller: 'StatementsController',
            resolve: {
                loadOnInit: ['DashboardService', function (DashboardService) {
                    return DashboardService.initStatementsView()
                }]
            }
        }).
        when('/agentsView', {
            templateUrl: 'js/parts/agentsView.htm',
            controller: 'AgentsController',
            resolve: {
                loadOnInit: ['DashboardService', function (DashboardService) {
                    return DashboardService.initAgentsView()
                }]
            }
        }).
        when('/activitiesView', {
            templateUrl: 'js/parts/activitiesView.htm',
            controller: 'ActivitiesController',
            resolve: {
                loadOnInit: ['DashboardService', function (DashboardService) {
                    return DashboardService.initActivitiesView()
                }]
            }
        }).
        when('/reportsView', {
            templateUrl: 'js/parts/reportsView.htm',
            controller: 'ReportsController'
//            resolve: {
//                loadOnInit: ['DashboardService', function (DashboardService) {
//                                return DashboardService.initActivityLog()
//                }]
//            }
        }).
        when('/usersView', {
            templateUrl: 'js/parts/usersView.htm',
            controller: 'UsersController',
            resolve: {
                loadOnInit: ['DashboardService', function (DashboardService) {
                    return DashboardService.initUsersView()
                }]
            }
        }).
        when('/createUser', {
            templateUrl: 'js/parts/createUserView.htm',
            controller: 'CreateUserController'
//            resolve: {
//                loadOnInit: ['DashboardService', function (DashboardService) {
//                    return DashboardService.initActivityLog()
//                }]
//            }
        }).
        otherwise({
            redirectTo: '/activityLogView'
        });

        $locationProvider.html5Mode(true);
//      $locationProvider.hashPrefix('!');
    });


