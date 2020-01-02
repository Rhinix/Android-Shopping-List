const express = require("express");
const router = express();
const mongoose = require("mongoose");
const checkAuth = require("../middleware/check-auth");
const jwt = require("jsonwebtoken");

const ShoppingList = require("../models/shoppingList");
const Article = require("../models/article");

router.get("/MyLists", checkAuth, (req, res) => {
  const token = req.headers.authorization.split(" ")[1];
  const userId = mongoose.Types.ObjectId(jwt.decode(token).userId);

  ShoppingList.find()
    .where("owner")
    .equals(userId)
    .select(" _id name articlesList")
    .exec()
    .then(result => {
      if (result) {
        let shoppingListArray = [];
        let newShoppingList;

        result.forEach(shoppingList => {
          newShoppingList = {
            name: shoppingList.name,
            _id: shoppingList._id,
            nbArticles: shoppingList.articlesList.length
          };
          shoppingListArray.push(newShoppingList);
        });

        res.status(200).json(shoppingListArray);
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

router.get("/SharedLists", checkAuth, (req, res) => {
  const token = req.headers.authorization.split(" ")[1];
  const userId = jwt.decode(token).userId;
  const filter = { users: mongoose.Types.ObjectId(userId) };
  console.log(filter);

  ShoppingList.find(filter)
    .select("_id name articlesList")
    .exec()
    .then(result => {
      if (result) {
        let shoppingListArray = [];
        let newShoppingList;

        result.forEach(shoppingList => {
          newShoppingList = {
            name: shoppingList.name,
            _id: shoppingList._id,
            nbArticles: shoppingList.articlesList.length
          };
          shoppingListArray.push(newShoppingList);
        });

        res.status(200).json(shoppingListArray);
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

router.get("/Share/:listId", checkAuth, (req, res) => {
  let id = req.params.listId;

  ShoppingList.findById(id)
    .select("ShareCode")
    .exec()
    .then(result => {
      if (result) {
        res.status(200).json(result);
      } else {
        res.status(404).json({ Message: "Not found" });
      }
    })
    .catch(err => {
      res.status(500).json({ error: err });
    });
});

router.get("/:listId", checkAuth, (req, res) => {
  let id = req.params.listId;

  ShoppingList.findById(id)
    .select("_id name articlesList")
    .populate("articlesList", "name qty checked _id")
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

router.post("/Share", checkAuth, (req, res) => {
  const token = req.headers.authorization.split(" ")[1];
  const userId = jwt.decode(token).userId;

  const sharedCode = req.body.sharedCode;
  let filter = { ShareCode: sharedCode };

  ShoppingList.updateOne(filter, { $push: { users: userId } })
    .exec()
    .then(result => {
      if (result) {
        res.status(200).json({ Message: "list shared" });
      } else {
        res.status(404).json({ Message: "Aucune liste trouvée" });
      }
    })
    .catch(err => {
      res.status(500).json({ error: err });
    });
});

router.post("/", checkAuth, (req, res) => {
  const token = req.headers.authorization.split(" ")[1];
  const userId = jwt.decode(token).userId;

  let listName = req.body.name;
  let articlesList = req.body.articlesList || [];

  let newArticle;
  let ListArticles = [];

  articlesList.forEach(article => {
    newArticle = new Article({
      _id: new mongoose.Types.ObjectId(),
      name: article.name,
      qty: article.qty
    });
    article._id = newArticle._id;

    ListArticles.push(newArticle);
  });

  Article.collection.insertMany(ListArticles);

  let shoppingList = new ShoppingList({
    _id: new mongoose.Types.ObjectId(),
    owner: userId,
    user: [],
    name: listName,
    articlesList: articlesList
  });

  shoppingList
    .save()
    .then(result => {
      res.status(201).json({ message: "List created" });
    })
    .catch(err => {
      res.status(500).json({ error: err });
    });
});

router.delete("/:listId", checkAuth, (req, res) => {
  let id = req.params.listId;
  ShoppingList.findByIdAndDelete(id)
    .populate("articlesList", "name _id qty checked")
    .exec()
    .then(shoppingList => {
      if (shoppingList) {
        shoppingList.articlesList.forEach(article => {
          article.remove();
        });
        res.status(204).json({ message: "List deleted" });
      } else {
        res.status(404).json({ message: "List not found" });
      }
    })
    .catch(err => {
      res.status(500).json({ error: err });
    });
});

router.patch("/StopShared/:listId", checkAuth, (req, res) => {
  let id = req.params.listId;
  let deletedUserId = req.body.userId;

  ShoppingList.findByIdAndUpdate(id, {
    $pull: { users: deletedUserId }
  })
    .exec()
    .then(result => {
      res.status(200).json({ message: "Sharing stop", res: result });
    })
    .catch(err => {
      res.status(500).json({ error: err });
    });
});

router.patch("/:listId", checkAuth, (req, res) => {
  let id = req.params.listId;

  let name = req.body.name;

  ShoppingList.findById(id)
    .populate("articlesList", "name qty checked _id")
    .exec()
    .then(shoppingList => {
      if (shoppingList) {
        /*if (addedArticles) {
          let newArticles = [];

          addedArticles.forEach(article => {
            let newArticle = new Article({
              _id: new mongoose.Types.ObjectId(),
              name: article.name,
              qty: article.qty
            });
            newArticles.push(newArticle);
            shoppingList.articlesList.push(newArticle);
          });

          Article.insertMany(newArticles);
        }
        if (modifiedArticles) {
          console.log(modifiedArticles);

          let index = 0;
          modifiedArticles.forEach(modifiedArticle => {
            index = shoppingList.articlesList
              .map(article => article._id)
              .indexOf(modifiedArticle._id);

            console.log(index);

            shoppingList.articlesList[index].name = modifiedArticle.name;
            shoppingList.articlesList[index].qty = modifiedArticle.qty;
            shoppingList.articlesList[index].save();
          });
        }
        if (deletedArticles) {
          let index = 0;
          deletedArticles.forEach(deletedArticle => {
            index = shoppingList.articlesList
              .map(article => article._id)
              .indexOf(deletedArticle);
            console.log("\nindexOf:" + index);
            if (index >= 0) {
              shoppingList.articlesList.splice(index, 1);
            }
            console.log(shoppingList.articlesList);
          });
          Article.deleteMany({ _id: { $in: deletedArticles } }).exec();
        }*/
        if (name) {
          shoppingList.name = name;
          shoppingList.save();
        }
        res.status(200).json({ message: "list updated" });
      } else {
        res.status(404).json({ message: "list not found" });
      }
    })
    .catch(err => {
      res.status(500).json({ error: err });
    });
});

module.exports = router;
