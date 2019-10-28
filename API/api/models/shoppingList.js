const mongoose = require("mongoose");

const shoppingListSchema = mongoose.Schema({
  _id: mongoose.Schema.Types.ObjectId,
  name: {
    required: true,
    type: String
  },
  itemList: []
});

module.exports = mongoose.model("shoppingList", shoppingListSchema);
