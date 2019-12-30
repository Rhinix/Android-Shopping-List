package be.amellaa.shoppinglist.activities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment.MyListFragment
import be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment.SharedFragment

/**
 *  Adapter for ShoppingList's TabLayout
 */
class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                MyListFragment()
            }
            else -> SharedFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "My Lists"
            else -> "Shared Lists"
        }
    }

}