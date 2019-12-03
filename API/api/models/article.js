const mongoose = require("mongoose");

const articleSchema = mongoose.Schema({
  _id: mongoose.Schema.Types.ObjectId,
  name: {
    required: true,
    type: String
  },
  qty: Number,
  checked: {type: Boolean, default: false}
});

module.exports = mongoose.model("article", articleSchema);
