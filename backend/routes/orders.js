var mongo = require('mongodb');

var Server = mongo.Server,
	Db = mongo.Db,
	BSON = mongo.BSONPure;

var server = new Server('localhost', 27018, {auto_reconnect: true});
db = new Db('albert_db', server);

db.open(function(err, db) {
	if(!err) {
		console.log("Connected to 'albert_db' database");
		db.collection('orders', {strict:true}, function(err, collection) {
			if (err) {
				console.log("The 'orders' collection doesn't exist!");
			}
		});
	}
});

exports.findAll = function(req, res) {
	res.send([{name:'John', age:30}, {name:'Jane', age:32}]);
}

exports.findById = function(req, res) {
	res.send({id:req.params.id, name:'John', age:30});
}