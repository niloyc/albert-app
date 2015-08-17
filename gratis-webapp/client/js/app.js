(function() {
    var app = angular.module('gratisDashboard', ['chart.js']);
    
    app.controller('AppController', function($scope, $http, $filter) {
        $scope.addItemOn = false;
        
        $scope.edit = false;
        $scope.edit_item = {};
        $scope.edit_item_id = "";
        $scope.edit_item_copy = {};
        
        $scope.items = [];
        $scope.current_items = [];
        $scope.root_item = {name:'Home', id:'root', is_cat:true};
        $scope.breadcrumbs = [$scope.root_item];
        $scope.curr_parent = $scope.root_item;
        
        $scope.moveTreeTo = function(parent) {
            if (!parent.is_cat) return;
            $scope.curr_parent = parent;
            var it = parent;
            $scope.breadcrumbs = [];
            while (it.parent) {
                $scope.breadcrumbs.push(it);
                it = it.parent;
                for (i of $scope.items) {
                    if (i.id === it) {
                        it = i;
                        break;
                    }
                }
            }
            $scope.breadcrumbs.push($scope.root_item);
            
            $scope.current_items = [];
            for (i of $scope.items) {
                if (i.parent === parent.id) {
                    $scope.current_items.push(i);
                }
            }
        }
        
        $scope.refreshTree = function() {
            $scope.moveTreeTo($scope.curr_parent);
        }
        
        $scope.editItem = function(item) {
            $scope.edit = true;
            $scope.edit_item = item;
            $scope.edit_item_id = item.id;
            $scope.edit_item_copy = JSON.parse(JSON.stringify(item));
            $scope.addItemOn = true;
        }
        
        $scope.restoreEditItem = function() {
            var index = $scope.items.indexOf($scope.edit_item);
            console.log($scope.edit_item);
            console.log(index);
            console.log($scope.edit_item_copy);
            $scope.items[index] = $scope.edit_item_copy;
            $scope.refreshTree();
        }
        
        $scope.removeItem = function(item) {
            var index = $scope.items.indexOf(item);
            console.log(item.id);
            console.log(index);
            $scope.items.splice(index, 1);
            $scope.refreshTree();
            $http.delete('https://getgratis.co/api/items/' + item.id);
        };
        
        $scope.clearEditItem = function() {
            $scope.edit_item = {};
        }
        
        $scope.setAddItemOn = function(on) {
            if (!on) {
                if ($scope.edit) {
                    $scope.restoreEditItem();
                }
                $scope.edit_item = {};
                $scope.edit = false;
            }
            $scope.addItemOn = on;
        }
        
        $scope.isAddItemOn = function() {
            return $scope.addItemOn;
        }
        
        $http.get('https://getgratis.co/api/items').success(function(data) {
            $scope.items = data;
            $scope.moveTreeTo($scope.root_item);
            console.log($scope.items);
        });
    });
    
    app.controller('SidebarController', function() {
        this.tab = 0;
        this.subtab = 1;
        
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
    });
    
    
    app.controller('InsightsCtrl', function($scope, $http) {
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
        
        $http.get('https://getgratis.co/api/customers/').success(function(data) {
            $scope.customers = data;
        });
        
        $http.get('https://getgratis.co/api/orders/').success(function(data) {
            $scope.orders = data;
            $scope.order_item_names = data.map(function(a) {return a.items.map(function(b){return b.name})});
            $scope.order_item_quantities = data.map(function(a) {return a.items.map(function(b){return b.quantity})});
            console.log($scope.order_item_names);
            console.log($scope.order_item_quantities);
        });
    });
    
    app.controller('AddCustomerCtrl', function($scope, $http) {
        $scope.addCustomer = function(user) {
            $http.post('https://getgratis.co/api/customers/', user).success(function(data) {
                user.name = '';
                user.points = 0;
            });
        };
    });
    
    app.controller('AddItemCtrl', function($scope, $http) {
        $scope.isCategory = function() {
            return $scope.edit_item.is_cat;
        }
        
        $scope.setCategory = function(cat) {
            $scope.edit_item.is_cat = cat;
        }
        
        $scope.addItem = function() {
            console.log($scope.edit_item);
            $scope.edit_item.is_cat = $scope.edit_item.is_cat || false;
            if ($scope.edit_item.is_cat) {
                $scope.edit_item.price = 0;
                $scope.edit_item.quantity = 0;
            }
            $scope.edit_item.parent = $scope.breadcrumbs[0].id;
            if ($scope.edit) {
                $http.put('https://getgratis.co/api/items/' + $scope.edit_item.id, $scope.edit_item).success(function(data) {
                    //$scope.setAddItemOn(false);
                });
            }
            else {
                $http.post('https://getgratis.co/api/items/', $scope.edit_item).success(function(data) {
                    // To make deletion work, we need to place the generated id into the local object
                    $scope.edit_item.id = data.id;
                    $scope.items.push($scope.edit_item);
                    $scope.refreshTree();
                    
                    // Must perform clear in parent scope to avoid assigning new locally scoped edit_item
                    $scope.clearEditItem();
                });
            }
        }
    });
    
})();
