package be.amellaa.shoppinglist.DAO

import be.amellaa.shoppinglist.models.ShoppingItem
import be.amellaa.shoppinglist.models.ShoppingList
import be.amellaa.shoppinglist.models.User
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.CountDownLatch

class ShoppingListDAO {


    companion object {
        val instance = ShoppingListDAO()
        val httpClient = OkHttpClient()
        val DOMAIN_URL = "http://192.168.0.22:3000"
        val USER_LOGIN_URL = "/user/login/"
        val USER_SIGNUP_URL = "/user/signup/"
        val SHOPPINGLIST_URL = "/shoppingList/"
        val MY_LIST_URL = "/shoppingList/MyLists/"
        val SHARED_LIST_URL = "/shoppingList/SharedList/"
        var TOKEN =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im1haWwyQGduZS5jb20iLCJ1c2VySWQiOiI1ZGM3ZmY5OTI1MjhkYjA1OWMzZGE5MmUiLCJpYXQiOjE1NzM0NjkyMzJ9.JhALm7upsZY5-zqj-Baee4Ez8VBiseC_FI4TnO7aYso"
    }

    fun login(user: User) {
        val request = Request.Builder()
            .url(DOMAIN_URL + USER_LOGIN_URL)
            .build()

    }

    fun signUp(newUser: User) {
        val request = Request.Builder()
            .get()
            .header("Authorization", "bearer $TOKEN")
            .url(DOMAIN_URL + USER_SIGNUP_URL)
            .build()
    }

    fun getMyList(): ArrayList<ShoppingList> {
        val request = Request.Builder()
            .get()
            .header("Authorization", "bearer $TOKEN")
            .url(DOMAIN_URL + MY_LIST_URL)
            .build()

        var newShoppingListArray = ArrayList<ShoppingList>()

        //test
        /*val list = ShoppingList()
        list.name = "putain de merde"
        list.id="1234"
        newShoppingListArray.add(list)*/
        val countDownLatch : CountDownLatch = CountDownLatch(1)
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {

                var res = response.body()!!.string()

                var jsonArray = JSONArray(res)

                var newShoppingList: ShoppingList
                var jsonObject: JSONObject

                for (i in 0 until jsonArray.length()) {
                    jsonObject = jsonArray.getJSONObject(i)
                    newShoppingList = ShoppingList()
                    newShoppingList.id = jsonObject.getString("_id")
                    newShoppingList.name = jsonObject.getString("name")
                    newShoppingList.nbArticles = jsonObject.getInt("nbArticles")
                    newShoppingListArray.add(newShoppingList)
                }
                countDownLatch.countDown()
            }

            override fun onFailure(call: Call, e: IOException) {
                countDownLatch.countDown()
                throw e
            }

        })
        countDownLatch.await();
        return newShoppingListArray

    }

    fun getSharedList() {

    }

    fun saveList() {

    }

    fun getItemFromList(id: String) : ArrayList<ShoppingItem>
    {
        val request = Request.Builder()
            .get()
            .header("Authorization", "bearer $TOKEN")
            .url(DOMAIN_URL + SHOPPINGLIST_URL + id)
            .build()
        var newItemList : ArrayList<ShoppingItem> = ArrayList()
        val countDownLatch : CountDownLatch = CountDownLatch(1)
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {

                var res = response.body()!!.string()

                var jsonArray = JSONObject(res).getJSONArray("articlesList")

                var newShoppingItem: ShoppingItem
                var jsonObject: JSONObject

                for (i in 0 until jsonArray.length()) {
                    jsonObject = jsonArray.getJSONObject(i)
                    newShoppingItem = ShoppingItem()
                    newShoppingItem.id = jsonObject.getString("_id")
                    newShoppingItem.name = jsonObject.getString("name")
                    newShoppingItem.qty = jsonObject.getInt("qty")
                    newItemList.add(newShoppingItem)
                }
                countDownLatch.countDown()
            }

            override fun onFailure(call: Call, e: IOException) {
                countDownLatch.countDown()
                throw e
            }

        })
        countDownLatch.await();
        return newItemList;
    }

    fun patchList() {

    }

    fun deleteList(id: String) {

    }

    fun setHeader(fieldName: String, value: String) {

    }
}