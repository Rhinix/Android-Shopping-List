const mongoose = require("mongoose");

const shoppingListSchema = mongoose.Schema({
  _id: mongoose.Schema.Types.ObjectId,
  name: {
    required: true,
    type: String
  },
  itemList: [
    {
      _id: mongoose.Schema.Types.ObjectId,
      name: {
        required: true,
        type: String
      },
      qte: {
        required: true,
        type: String
      }
    }
  ]
});

module.exports = mongoose.model("shoppingList", shoppingListSchema);
