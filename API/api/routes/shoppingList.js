const express = require("express");
const router = express();
const mongoose = require("mongoose");

const ShoppingList = require("../models/shoppingList");

/*
    GET /shoppingList/

    get all lists
*/
router.get("/", (req, res) => {
  ShoppingList.find()
    .select("itemList.name itemList._id itemList.qte _id name")
    .exec()
    .then(result => {
      if (result) {
        res.status(200).json(result);
      } else {
        res.status(404).json({
          message: "there is no list"
        });
      }
    })
    .catch(err => {
      res.status(500).json({ error: err });
    });
});

/*
    GET /shoppingList/"list id"

    get list by id
*/
router.get("/:listId", (req, res) => {
  const id = req.params.listId;
  ShoppingList.findById(id)
    .select("_id name itemList")
    .exec()
    .then(result => {
      if (result) {
        res.status(200).json(result);
      } else {
        res.status(404).json({
          message: "List not found for the given id"
        });
      }
    })
    .catch(err => {
      res.status(500).json({ error: err });
    });
});

/*
    POST /shoppingList:
    name
    itemlist[]
*/
router.post("/", (req, res) => {
  const shoppingList = new ShoppingList({
    _id: new mongoose.Types.ObjectId(),
    name: req.body.name,
    itemList: req.body.itemList
  });

  shoppingList.itemList.forEach(element => {
    element._id = new mongoose.Types.ObjectId();
  });

  shoppingList
    .save()
    .then(result => {
      console.log(result);
      res.status(200).json({ message: "List created" });
    })
    .catch(err => {
      res.status(500).json({ error: err });
    });
});

/*
    DELETE /shoppingList/listId

    delete list by id
*/
router.delete("/:listId", (req, res) => {
  const id = req.params.listId;
  ShoppingList.deleteOne({ _id: id })
    .exec()
    .then(result => {
      res.status(200).json({ message: "List deleted" });
    })
    .catch(err => {
      res.status(500).json({ error: err });
    });
});

/*
  PATCH /shoppingList/updateList:listId
  require an array with the modified field + their new values
  ex:
    [
      {
        "propName":"name",
        "value":"test"
      },
      {
        "propName":"listItem.<index du produit>.<propName>",
        "value":"njn"
      }
    ]
*/

router.patch("/updateList:listId", (req, res) => {
  const id = req.params.listId;
  const updateOps = {};
  for (const ops of req.body.set) {
    updateOps[ops.propName] = ops.value;
  }
  ShoppingList.findByIdAndUpdate(id, { $set: updateOps })
    .exec()
    .then(result => {
      res.status(200).json({ message: "List updated" });
    })
    .catch(err => {
      res.status(500).json({ error: err });
    });
});

module.exports = router;