package be.amellaa.shoppinglist.models

/**
 *  Model for a ShoppingItem
 */
class ShoppingItem {
    lateinit var id: String
    lateinit var name: String
    var qty: Int? = null
    var checked: Boolean = false
}