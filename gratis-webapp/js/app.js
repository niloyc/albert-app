(function() {
    var app = angular.module('tryAngular', ['chart.js']);
    
    app.controller('AppController', function($scope, $http) {
        $scope.items = [];
        $scope.customers = '';
        $scope.orders = '';
        $scope.order_item_names = {};
        $scope.order_item_quantities = {};
        $scope.point_ranges = ['0-100', '100-200', '200-300', '300-400', '400-500', '500+'];
        $scope.series = ['Series A'];
        $scope.point_data = [[10, 50, 100, 60, 30, 40]];
        
        $scope.age_labels = ['18-25', '25-30', '30-40', '40-50', '50+'];
        $scope.age_data = [500, 400, 600, 200, 200];
        
        $scope.months = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];
        $scope.num_customers = [[50, 100, 200, 400, 450, 600, 700]];
        
        $scope.product_labels = ['Coffee', 'Eclair', 'Donut', 'Chai Latte', 'Tea'];
        $scope.product_data = [[100, 50, 69, 200, 30]];
        
        $http.get('http://scribbler.io:3000/api/items').success(function(data) {
            $scope.items = data;
            console.log($scope.items);
        });
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
        this.tab = 0;
        this.subtab = 1;
        this.addItemOn = false;
        
        this.setTab = function(newValue) {
            this.tab = newValue;
        }
        
        this.isSet = function(tabName) {
            return this.tab === tabName;
        }
        
        this.setSubtab = function(newValue) {
            this.subtab = newValue;
        }
        
        this.isSubtabSet = function(tabName) {
            return this.subtab === tabName;
        }
        
        this.setAddItemOn = function(on) {
            this.addItemOn = on;
        }
        
        this.isAddItemOn = function() {
            return this.addItemOn;
        }
    });
    
    app.controller('PieCtrl', function($scope) {
    });
    
    app.controller('PointsDistribCtrl', function($scope) {
    });
    
    app.controller('AddCustomerCtrl', function($scope, $http) {
        $scope.addCustomer = function(user) {
            user.name = '';
            user.points = 0;
            $http.post('http://scribbler.io:3000/api/customers/', user).success(function(data) {
                
            });
        };
    });
    
})();