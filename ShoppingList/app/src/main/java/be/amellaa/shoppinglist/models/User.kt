package be.amellaa.shoppinglist.models

/**
 *  Model for an User
 */
class User {
    var email: String
    var password: String
    var Token = ""

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }

}