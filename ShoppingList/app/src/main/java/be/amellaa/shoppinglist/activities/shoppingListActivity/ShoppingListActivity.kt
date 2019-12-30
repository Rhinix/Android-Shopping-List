package be.amellaa.shoppinglist.activities.shoppingListActivity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import be.amellaa.shoppinglist.Authenticator.AccountAuthenticator
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.activities.TabAdapter
import be.amellaa.shoppinglist.activities.loginActivity.LoginActivity
import com.google.android.material.tabs.TabLayout


/**
 *  Activity with tabs, that host the two kinds of ListFragment
 */
class ShoppingListActivity : FragmentActivity() {

    lateinit var mViewPager: ViewPager
    lateinit var mTabLayout: TabLayout
    lateinit var mSettingsToolbar: Toolbar
    //var arrayList_details: ArrayList<ShoppingList> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoppinglist)
        mViewPager = findViewById<ViewPager>(R.id.viewContent)
        mTabLayout = findViewById<TabLayout>(R.id.tabsLayout)
        val fragmentAdapter = TabAdapter(supportFragmentManager)
        mViewPager.adapter = fragmentAdapter

        mTabLayout.setupWithViewPager(mViewPager)

        initializeToolBar()
    }

    private fun initializeToolBar() {
        mSettingsToolbar = findViewById(R.id.toolbar_settings)
        mSettingsToolbar.title = getString(R.string.app_name)
        setActionBar(mSettingsToolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.log_out -> {
                AccountAuthenticator(this).deleteAccount()
                changeActivity(this, LoginActivity::class.java)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeActivity(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        startActivity(intent)
        finish()
    }

}
