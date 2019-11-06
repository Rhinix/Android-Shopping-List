package be.amellaa.shoppinglist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import be.amellaa.shoppinglist.models.ShoppingList
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ShoppingListActivity : Activity() {

    val client = OkHttpClient()
    lateinit var listView_details: ListView
    lateinit var swipeView : SwipeRefreshLayout
    var arrayList_details: ArrayList<ShoppingList> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoppinglist)
        listView_details = findViewById<ListView>(R.id.listView) as ListView
        swipeView = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh) as SwipeRefreshLayout
        swipeView.setOnRefreshListener{run("http://192.168.1.38:3000/shoppinglist", true)}
        listView_details.setOnItemClickListener { parent, view, position, id ->
            val intent : Intent = Intent(this, ItemListActivity::class.java)
            intent.putExtra("listId", (listView_details.getItemAtPosition(position) as ShoppingList).id)
            startActivity(intent)
        }
        run("http://192.168.1.38:3000/shoppinglist")
    }

    fun run(url: String, refresh: Boolean = false) {
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (refresh)
                {
                    swipeView.isRefreshing = false
                }
            }
            override fun onResponse(call: Call, response: Response) {
                var str_response = response.body()!!.string()
                //creating json object
                val json_array: JSONArray = JSONArray(str_response)
                var size:Int = json_array.length()
                arrayList_details= ArrayList();
                for (i in 0.. size-1) {
                    var json_object:JSONObject=json_array.getJSONObject(i)
                    var model:ShoppingList= ShoppingList();
                    model.id=json_object.getString("_id")
                    model.name=json_object.getString("name")
                    arrayList_details.add(model)
                }
                runOnUiThread {
                    //stuff that updates ui
                    val obj_adapterShopping : ShoppingListAdapter = ShoppingListAdapter(applicationContext,arrayList_details)
                    listView_details.adapter=obj_adapterShopping
                    if (refresh)
                    {
                        swipeView.isRefreshing = false
                    }
                }
            }
        })
    }

}
