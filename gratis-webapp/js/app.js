(function() {
    var app = angular.module('tryAngular', ['chart.js']);
    
    app.controller('AppController', function($scope, $http) {
        $scope.customers = '';
        $scope.orders = '';
        $scope.order_item_names = {};
        $scope.order_item_quantities = {};
        $scope.point_ranges = ['0-100', '100-200', '200-300', '300-400', '400-500', '500+'];
        $scope.series = ['Series A'];
        $scope.point_data = [[10, 50, 100, 60, 30, 40]];
        
        $http.get('http://scribbler.io:3000/api/customers/').success(function(data) {
            $scope.customers = data;
        });
        $http.get('http://scribbler.io:3000/api/orders/').success(function(data) {
            $scope.orders = data;
            $scope.order_item_names = data.map(function(a) {return a.items.map(function(b){return b.name})});
            $scope.order_item_quantities = data.map(function(a) {return a.items.map(function(b){return b.quantity})});
            console.log($scope.order_item_names);
            console.log($scope.order_item_quantities);
        });
    });
    
    app.controller('SidebarController', function() {
        this.tab = 1;
        
        this.setTab = function(newValue) {
            this.tab = newValue;
        }
        
        this.isSet = function(tabName) {
            return this.tab === tabName;
        }
    });
    
    app.controller('PieCtrl', function($scope) {
    });
    
    app.controller('PointsDistribCtrl', function($scope) {
    });
    
})();