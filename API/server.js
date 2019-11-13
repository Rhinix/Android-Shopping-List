const https = require("https");
const app = require("./app");
const fs = require("fs");

const key = fs.readFileSync(__dirname + "\\api\\credentials\\selfsigned.key");
const cert = fs.readFileSync(__dirname + "\\api\\credentials\\selfsigned.crt");

var options = {
  key: key,
  cert: cert
};

const port = process.env.port || 3000;

const server = https.createServer(options, app);

server.listen(port);
