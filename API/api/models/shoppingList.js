const mongoose = require("mongoose");

const shoppingListSchema = mongoose.Schema({
  _id: mongoose.Schema.Types.ObjectId,
  owner: { type: mongoose.Schema.Types.ObjectId, ref: "user" },
  users: [{ type: mongoose.Schema.Types.ObjectId, ref: "user" }],
  name: {
    required: true,
    type: String
  },
  ShareCode: String,
  articlesList: [{ type: mongoose.Schema.Types.ObjectId, ref: "article" }]
});

module.exports = mongoose.model("ShoppingList", shoppingListSchema);
