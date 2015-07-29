(function() {
    var app = angular.module('tryAngular', []);
    
    app.controller('AppController', ['$http', function($http) {
        var scope = this;
        scope.customers = '';
        scope.orders = '';
        $http.get('http://scribbler.io:3000/api/customers/').success(function(data) {
            scope.customers = data;
        });
        $http.get('http://scribbler.io:3000/api/orders/').success(function(data) {
            scope.orders = data;
        });
    }]);
    
    app.controller('SidebarController', function() {
        this.tab = 1;
        
        this.setTab = function(newValue) {
            this.tab = newValue;
        }
        
        this.isSet = function(tabName) {
            return this.tab === tabName;
        }
    });
    
})();