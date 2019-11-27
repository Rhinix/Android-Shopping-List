package be.amellaa.shoppinglist.activities.shoppingListActivity


import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.activities.TabAdapter
import com.google.android.material.tabs.TabLayout

class ShoppingListActivity : FragmentActivity() {

    lateinit var mViewPager: ViewPager
    lateinit var mTabLayout : TabLayout
    //var arrayList_details: ArrayList<ShoppingList> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoppinglist)
        mViewPager = findViewById<ViewPager>(R.id.viewContent)
        mTabLayout = findViewById<TabLayout>(R.id.tabsLayout)
        val fragmentAdapter = TabAdapter(supportFragmentManager)
        mViewPager.adapter = fragmentAdapter

        mTabLayout.setupWithViewPager(mViewPager)
    }
}
