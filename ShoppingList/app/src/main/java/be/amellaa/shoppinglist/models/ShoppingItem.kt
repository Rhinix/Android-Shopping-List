package be.amellaa.shoppinglist.models

class ShoppingItem {
    lateinit var id: String
    lateinit var name: String
    var qty: Int? = null
    var checked: Boolean = false
}