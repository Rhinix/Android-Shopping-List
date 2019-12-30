package be.amellaa.shoppinglist.activities.shoppingListActivity.itemFragment

import android.os.Bundle

/**
 *  Fragment for items in a list, the user has to share
 */
class SharedItemFragment: BaseItemFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(false)

    }
}