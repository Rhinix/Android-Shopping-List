package be.amellaa.shoppinglist.activities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import be.amellaa.shoppinglist.activities.shoppingListActivity.MyListFragment
import be.amellaa.shoppinglist.activities.shoppingListActivity.SharedFragment

class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                MyListFragment()
            }
            else -> SharedFragment()
            //SecondFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Mes Listes"
            else -> "Listes Partagées"
        }
    }

}