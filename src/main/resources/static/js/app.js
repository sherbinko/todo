/*
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 Copyright 2016, Andrey Shcherbinko. All rights reserved.
 */

var todoApp = angular.module('todoApp', []);

todoApp.controller("main", function($scope, $http, $q) {
    $scope.statusToShow = '';

    $scope.refreshAfterPromise = function(updatePromise) {
        return (
            updatePromise.then(function () {
                return $http.get("/services/tasks")
            }).
            then(function (tasks) {
                $scope.tasks = tasks.data;
            })
        )
    };

    $scope.$watch('$viewContentLoaded', function() {
        $scope.refreshAfterPromise($q.when())
    });

    $scope.addTask = function (desc) {
        $scope.refreshAfterPromise($http.put("/services/tasks",desc));
    };

    $scope.deleteTask = function(id) {
        $scope.refreshAfterPromise($http.delete("/services/tasks/"+id))
    };

    $scope.changeStatus=function(task) {
        $scope.refreshAfterPromise($http.post("/services/tasks/"+task.id+"/status", task.status));
    }
});