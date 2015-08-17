(function() {
    var app = angular.module('gratisUIBuilder', ['colorpicker.module']);
    
    app.controller('AppController', function($scope, $http) {
        $scope.settings = {};
        
        $http.get('http://scribbler.io:3000/api/customs/').success(function(data) {
            $scope.settings = data[0];
        });
        
        $scope.updateSettings = function() {
            $http.put('http://scribbler.io:3000/api/customs/' + $scope.settings.id, $scope.settings);
        }
    });
    
})();