const mongoose = require("mongoose");

const shoppingListSchema = mongoose.Schema({
  _id: mongoose.Schema.Types.ObjectId,
  name: {
    required: true,
    type: String
  },
  articlesList: [{ type: mongoose.Schema.Types.ObjectId, ref: "article" }]
});

module.exports = mongoose.model("ShoppingList", shoppingListSchema);
