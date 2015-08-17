var loopback = require('loopback');
var boot = require('loopback-boot');
var http = require('http');
var https = require('https');
var sslConfig = require('./ssl-config');

var app = module.exports = loopback();

app.use(function requireHTTPS(req, res, next) {
  if (!req.secure) {
    return res.redirect('https://' + req.headers.host + req.url);
  }
  next();
});

http.createServer(app).listen(8080);

app.start = function() {
  console.log('Starting...');
  // start the web server
  var server = null;
  var options = {
    key: sslConfig.privateKey,
    cert: sslConfig.certificate,
    ca: sslConfig.ca
  };

  server = https.createServer(options, app);

  return server.listen(app.get('port'), function() {
    var baseUrl = 'https://' + app.get('host') + ':' + app.get('port');
    app.emit('started', baseUrl);
    console.log('Web server listening at: %s', baseUrl);
  });
};

boot(app, __dirname, function(err) {
  if (err) throw err;

  if (require.main === module) {
    app.start();
  }
});
