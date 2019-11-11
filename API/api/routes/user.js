const express = require("express");
const router = express();
const bcrypt = require("bcrypt");
const mongoose = require("mongoose");
const jwt = require("jsonwebtoken");

const User = require("../models/user");

/*
    POST /user/signup  
    required fields:
        email
        password
*/
router.post("/signup", (req, res) => {
  User.find({ email: req.body.email })
    .exec()
    .then(doc => {
      if (doc.length >= 1) {
        return res.status(409).json({ message: "Mail Exist" });
      } else {
        bcrypt.hash(req.body.password, 10, (err, hash) => {
          if (err) return res.status(500).json({ error: err });
          const user = new User({
            _id: new mongoose.Types.ObjectId(),
            email: req.body.email,
            password: hash
          });
          user
            .save()
            .then(result => {
              res.status(200).json({ message: "User created" });
            })
            .catch(err => {
              res.status(500).json({ error: err });
            });
        });
      }
    })
    .catch(err => {
      res.status(500).json({ error: err });
    });
});

router.post("/login", (req, res) => {
  User.findOne({ email: req.body.email })
    .exec()
    .then(user => {
      if (user.length < 1) {
        return res.status(401).json({
          message: "Auth Failed"
        });
      }
      bcrypt.compare(req.body.password, user.password, (err, result) => {
        if (result) {
          const token = jwt.sign(
            {
              email: user.email,
              userId: user._id
            },
            process.env.JWT_KEY
            // {
            //   expiresIn: "5h"
            // }
          );

          return res.status(200).json({
            message: "Auth successful",
            token: token
          });
        }
        res.status(401).json({
          message: "Auth failed"
        });
      });
    })
    .catch(err => {
      console.log(err);
      res.status(500).json({ error: err });
    });
});

module.exports = router;
