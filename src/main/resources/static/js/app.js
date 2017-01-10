'use strict';
var app = angular
    .module("dashboard", ['ngRoute'])
    .config(function($routeProvider, $locationProvider) {
        $routeProvider.
        when('/activityLogView', {
            templateUrl: 'js/parts/activityLogView.htm',
            controller: 'ActivityLogController'
        }).
        when('/statementsView', {
            templateUrl: 'js/parts/statementsView.htm',
            controller: 'StatementsController'
        }).
        when('/verbsView', {
            templateUrl: 'js/parts/verbsView.htm',
            controller: 'VerbsController'
        }).
        when('/agentsView', {
            templateUrl: 'js/parts/agentsView.htm',
            controller: 'AgentsController'
        }).
        when('/activitiesView', {
            templateUrl: 'js/parts/activitiesView.htm',
            controller: 'ActivitiesController'
        }).
        when('/reportsView', {
            templateUrl: 'js/parts/reportsView.htm',
            controller: 'ReportsController'
        }).
        when('/usersView', {
            templateUrl: 'js/parts/usersView.htm',
            controller: 'UsersController'
        }).
        otherwise({
            redirectTo: '/activityLogView'
        });

        $locationProvider.html5Mode(true);
//      $locationProvider.hashPrefix('!');
    });


