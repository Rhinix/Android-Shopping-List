package be.amellaa.shoppinglist.models

/**
 *  Model for a ShoppingList
 */
class ShoppingList {
    lateinit var id: String
    lateinit var name: String
    var nbArticles: Int? = null
    lateinit var sharedCode: String
}