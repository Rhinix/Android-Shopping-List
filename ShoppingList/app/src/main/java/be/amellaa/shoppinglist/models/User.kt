package be.amellaa.shoppinglist.models

class User {
    var email: String;
    var password: String;

    constructor(email: String, password: String) {
        this.email = email;
        this.password = password;
    }

}