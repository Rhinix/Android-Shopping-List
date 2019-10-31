package be.amellaa.shoppinglist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import be.amellaa.shoppinglist.models.ShoppingList
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ListActivity : Activity() {

    val client = OkHttpClient()
    lateinit var listView_details: ListView
    var arrayList_details: ArrayList<ShoppingList> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        listView_details = findViewById<ListView>(R.id.listView) as ListView
        listView_details.setOnItemClickListener { parent, view, position, id ->
            val intent : Intent = Intent(this, ItemListActivity::class.java)
            intent.putExtra("listId", (listView_details.getItemAtPosition(position) as ShoppingList).id)
            startActivity(intent)
        }
        run("http://192.168.1.38:3000/shoppinglist")
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
                    val obj_adapter : ListActivityAdapter = ListActivityAdapter(applicationContext,arrayList_details)
                    listView_details.adapter=obj_adapter
                }
            }
        })
    }

}
