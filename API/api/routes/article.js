const express = require("express");
const router = express();
const mongoose = require("mongoose");
const checkAuth = require("../middleware/check-auth");
const jwt = require("jsonwebtoken");

const ShoppingList = require("../models/shoppingList");
const Article = require("../models/article");


router.get("/:articleId", checkAuth, (req, res) => {
    let id = req.params.articleId;
    Article.findById(id)
        .select("_id name qty checked")
        .exec()
        .then(result => {
            if (result) {
                res.status(200).json(result);
            }
            else {
                res.status(404).json({
                    message: "Article not found for the given id"
                })
            }
        }).catch(err => {
            res.status(500).json({ error: err });
        });
})

router.post("/", checkAuth, (req, res) => {
    let listId = req.body.listId;
    let articleName = req.body.name;
    let articleQty = req.body.qty || 1;
    if (articleName) {

        var article = new Article({
            _id: new mongoose.Types.ObjectId(),
            name: articleName,
            qty: articleQty
        })
    }
    else {
        res.status(409).json({
            message: "Article name is required"
        })
        return;
    }
    if (listId) {
        ShoppingList.findById(listId)
            .populate("articlesList", "name qty checked _id")
            .exec()
            .then(shoppingList => {

                shoppingList.articlesList.push(article)
                shoppingList.save()
                Article.insertMany(article)
                res.status(201).json({
                    message: "Article added"
                })
            }).catch(err => {
                console.log(err);

                res.status(500).json({ error: err });
            });
    }
    else {
        res.status(409).json({
            message: "List id is required"
        })
    }

})

router.delete("/:articleId", checkAuth, (req, res) => {
    let id = req.params.articleId;
    Article.findById(id)
        .exec()
        .then(article => {
            if (article) {
                ShoppingList.find()
                    .populate("articlesList", "name qty checked _id")
                    .exec()
                    .then(result => {
                        if (result) {
                            result.forEach(shoppingList => {
                                let updatedList = []                                
                                shoppingList.articlesList.forEach(art => {
                                    if(art._id != id){
                                        updatedList.push(art)
                                    }
                                })
                                shoppingList.articlesList = updatedList
                                shoppingList.save()
                            })
                            article.remove()
                            res.status(201).json({
                                message: "Article deleted"
                            })

                        } else {
                            res.status(404).json({
                                message: "there is no article"
                            });
                        }
                    }).catch(err => {
                        console.log(err);
                        res.status(500).json({ error: err });
                    });
            }
            else {
                res.status(404).json({
                    message: "Article not found for the given id"
                })
            }
        }).catch(err => {
            res.status(500).json({ error: err });
        });
})

router.patch("/:articleId", (req, res) => {
    let id = req.params.articleId;
    let name = req.body.name;
    let qty = req.body.qty;
    let checked = req.body.checked;
    Article.findById(id)
        .exec()
        .then(article => {
            if (article) {
                if (name) {
                    article.name = name
                }
                if (qty) {
                    article.qty = qty
                }
                if ('checked' in req.body) {
                    article.checked = checked
                }
                
                article.save()
                res.status(201).json({
                    message: "Article updated"
                })
            } else {
                res.status(404).json({
                    message: "Article not found for the given id"
                })
            }
        })
        .catch(err => {
            res.status(500).json({ error: err });
        });
})

module.exports = router;