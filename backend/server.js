var express = require('express'),
	users = require('./routes/users');/*,
	orders = require('./routes/orders');*/

var app = express();

app.get('/users', users.findAll);
app.post('/users', users.addUser);
app.get('/users/:id', users.findById);
app.put('/users/:id', users.updateUser);
app.delete('/users/:id', users.deleteUser);

/*
app.get('/orders', orders.findAll);
app.post('/orders', orders.addOrder);
app.get('/orders/:id', orders.findById);
app.put('/orders/:id', orders.updateOrder);
app.delete('/orders/:id', orders.deleteOrder);
*/

app.listen(3000);
console.log('Listening on port 3000...');
