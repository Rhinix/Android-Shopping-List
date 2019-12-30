package be.amellaa.shoppinglist.activities.shoppingListActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.activities.shoppingListActivity.itemFragment.MyItemsFragment
import be.amellaa.shoppinglist.activities.shoppingListActivity.itemFragment.SharedItemFragment

/**
 *  Activity that host a ItemFragment
 */
class ShoppingItemActivity: FragmentActivity() {

    var frag : Fragment? = supportFragmentManager.findFragmentById(R.id.shoppingitem_fragment_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoppingitem)
        if(frag == null){
            frag = if(intent.getStringExtra("listKind") == "MyListFragment"){
                MyItemsFragment()
            } else{
                SharedItemFragment()
            }
            supportFragmentManager.beginTransaction().add(R.id.shoppingitem_fragment_container, frag!!).commit()
        }
    }


}