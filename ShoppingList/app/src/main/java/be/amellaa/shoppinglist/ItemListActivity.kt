package be.amellaa.shoppinglist

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.amellaa.shoppinglist.models.ShoppingItem
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ItemListActivity : Activity()
{

    val client = OkHttpClient()
    lateinit var mRecyclerView: RecyclerView
    var arrayList_details: ArrayList<ShoppingItem> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listitem)
        mRecyclerView = findViewById<RecyclerView>(R.id.listView) as RecyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        if(intent != null) {
            run("http://192.168.1.38:3000/shoppinglist/"+intent.getStringExtra("listId"))
        }
    }

    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //progress.visibility = View.GONE
            }
            override fun onResponse(call: Call, response: Response) {
                var str_response = response.body()!!.string()
                //creating json object
                val json_array: JSONArray = JSONObject(str_response).getJSONArray("articlesList")
                var size:Int = json_array.length()
                arrayList_details = ArrayList();
                for (i in 0 until size) {
                    var json_object : JSONObject = json_array.getJSONObject(i)
                    var model : ShoppingItem = ShoppingItem();
                    model.id = json_object.getString("_id")
                    model.name = json_object.getString("name")
                    arrayList_details.add(model)
                }
                runOnUiThread {
                    //stuff that updates ui
                    val adapterShopping : ItemListAdapter = ItemListAdapter(arrayList_details)
                    mRecyclerView.adapter = adapterShopping
                }
            }
        })
    }

}