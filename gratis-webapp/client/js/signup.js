(function() {
    var app = angular.module('gratisSignup', []);
    
    app.controller('SignupCtrl', function($scope, $http) {
        $scope.data = {};
        $scope.newId = "";
        $scope.submitForm = function() {
            console.log($scope.data);
            $http.post('https://getgratis.co/api/users', $scope.data).success(function(data) {
                $scope.newId = data.id;
            });
        };
    });
    
})();
