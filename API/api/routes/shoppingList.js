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
  let id = req.params.listId;
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
  let shoppingList = new ShoppingList({
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
  let id = req.params.listId;
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
    {
      "shoppingListProperties":[],
      "articlesListProperties":[]
    }
*/

router.patch("/updateList/:listId", (req, res) => {
  let id = req.params.listId;
  let updateOps = {};

  if (req.body.shoppingListProperties) {
    for (let ops of req.body.shoppingListProperties) {
      updateOps[ops.propName] = ops.value;
    }
  }

  if (req.body.articlesListProperties) {
    let propName;
    for (let ops of req.body.articlesListProperties) {
      propName = "itemList." + ops.articleIndex + "." + ops.propName;
      updateOps[propName] = ops.value;
    }
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

router.patch("/deleteArticle/:listId", (req, res) => {
  let id = req.params.listId;
  let deleteArtcilesId = req.body;

  ShoppingList.findByIdAndUpdate(id, {
    $pull: { itemList: { _id: { $in: deleteArtcilesId } } }
  })
    .exec()
    .then(result => {
      res.status(200).json({ message: "Article(s) deleted" });
    })
    .catch(err => {
      res.status(500).json({ error: err });
    });
});

//ToDo patch request for adding articles in itemList
router.patch("/addArticle/:listId", (req, res) => {
  let id = req.params.listId;
  let newArticle = req.body;

  newArticle.forEach(element => {
    element._id = new mongoose.Types.ObjectId();
  });

  ShoppingList.findByIdAndUpdate(id, {
    $addToSet: { itemList: { $each: newArticle } }
  })
    .exec()
    .then(result => {
      res.status(200).json({ message: "Article(s) added" });
    })
    .catch(err => {
      res.status(500).json({ error: err });
    });
});
module.exports = router;
