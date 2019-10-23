const express = require("express");
const app = express();
const morgan = require("morgan");
const bodyparser = require("body-parser");
const mongoose = require("mongoose");

app.use(morgan("dev"));

app.use(bodyparser.urlencoded({ extended: false }));
app.use(bodyparser.json());

//mongoose.connect();
//mongoose.Promise=global.Promise;

app.use("/", (req, res) => {
  res.status(200).json({ message: "ça fonctionne" });
});

module.exports = app;
