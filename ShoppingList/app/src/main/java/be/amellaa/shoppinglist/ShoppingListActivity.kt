package be.amellaa.shoppinglist

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import be.amellaa.shoppinglist.models.ShoppingList
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ShoppingListActivity : Activity() {

    val client = OkHttpClient()
    lateinit var mRecyclerView: RecyclerView
    lateinit var swipeView : SwipeRefreshLayout
    var arrayList_details: ArrayList<ShoppingList> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoppinglist)
        mRecyclerView = findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        swipeView = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh) as SwipeRefreshLayout
        swipeView.setOnRefreshListener{ run("http://192.168.1.38:3000/shoppinglist", true) }
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
                arrayList_details = ArrayList();
                for (i in 0.. size-1) {
                    var json_object:JSONObject = json_array.getJSONObject(i)
                    var model:ShoppingList= ShoppingList();
                    model.id = json_object.getString("_id")
                    model.name = json_object.getString("name")
                    model.nbArticles = json_object.getInt("nbArticles")
                    arrayList_details.add(model)
                }
                runOnUiThread {
                    //stuff that updates ui
                    val adapterShopping : ShoppingListAdapter = ShoppingListAdapter(arrayList_details)
                    mRecyclerView.adapter = adapterShopping
                    if (refresh)
                    {
                        swipeView.isRefreshing = false
                    }
                }
            }
        })
    }

}
