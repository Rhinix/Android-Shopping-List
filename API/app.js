const express = require("express");
const app = express();
const morgan = require("morgan");
const bodyparser = require("body-parser");
const mongoose = require("mongoose");

const userRoutes = require("./api/routes/user");
const shoppingListRoutes = require("./api/routes/shoppingList");
const articleRoutes = require("./api/routes/article");

const connectionString =
  "mongodb+srv://" +
  process.env.MONGO_ATLAS_USER +
  ":" +
  process.env.MONGO_ATLAS_PSWD +
  "@node-rest-shop-rqiqg.mongodb.net/ShoppingList?retryWrites=true&w=majority";

mongoose
  .connect(connectionString, {
    useNewUrlParser: true,
    useUnifiedTopology: true
  })
  .catch(err => {
    console.log(err);
  });
mongoose.Promise = global.Promise;

app.use(morgan("dev"));

app.use(bodyparser.urlencoded({ extended: false }));
app.use(bodyparser.json());

app.use("/user", userRoutes);
app.use("/shoppingList", shoppingListRoutes);
app.use("/article", articleRoutes);

//handling error
app.use((req, res, next) => {
  const error = new Error("Not Found");
  error.status = 404;
  next(error);
});

app.use((error, req, res) => {
  res.status(error.status || 500).json({
    error: {
      message: error.message
    }
  });
});

module.exports = app;
