package be.amellaa.shoppinglist.activities.shoppingListActivity.itemFragment

import android.os.Bundle

class SharedItemFragment: BaseItemFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(false)

    }
}